package repository;

import domain.Grade;
import domain.Homework;
import domain.Student;
import javafx.util.Pair;
import validation.IValidation;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GradeDBRepository implements CrudRepository<Pair<Student,Homework>,Grade> {
    private IValidation<Grade> validator;
    private CrudRepository<String,Student> sRepo;
    private CrudRepository<Integer,Homework> hRepo;
    private Map<Pair<Student,Homework>,Grade> entities;
    public GradeDBRepository(IValidation<Grade> validator,CrudRepository<String,Student> sRepo, CrudRepository<Integer,Homework> hRepo){
        this.validator = validator;
        this.hRepo = hRepo;
        this.sRepo = sRepo;
        this.entities = new HashMap<>();
    }

    @Override
    public List<Grade> getPieceOfData(Integer start_idx, Integer length) {
        List<Grade> data = new ArrayList<>();
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab3db", "marian", "xxxx");
                Statement stmt = conn.createStatement();) {
            String strSelect = String.format("select * from grade limit %d offset %d",length,start_idx);
            ResultSet rset = null;
            try {
                rset = stmt.executeQuery(strSelect);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            while (rset.next()){
                String idStudent = rset.getString("idStudent");
                Integer idHomework = rset.getInt("idHomework");
                Float value = rset.getFloat("value");
                String date = rset.getDate("dateGrade").toLocalDate().toString();
                String feedback = rset.getString("feedback");
                Grade grade = new Grade(sRepo.findOne(idStudent),hRepo.findOne(idHomework),value,date,feedback);
                data.add(grade);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    protected void readData(){
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab3db", "marian", "xxxx");
                Statement stmt = conn.createStatement();) {
            String strSelect = String.format("select * from grade");
            ResultSet rset = null;
            try {
                rset = stmt.executeQuery(strSelect);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            while (rset.next()){
                String idStudent = rset.getString("idStudent");
                Integer idHomework = rset.getInt("idHomework");
                Float value = rset.getFloat("value");
                String date = rset.getDate("dateGrade").toLocalDate().toString();
                String feedback = rset.getString("feedback");
                Grade grade = new Grade(sRepo.findOne(idStudent),hRepo.findOne(idHomework),value,date,feedback);
                entities.put(new Pair<>(sRepo.findOne(idStudent),hRepo.findOne(idHomework)),grade);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public Grade findOne(Pair<Student,Homework> id) throws Exception {
        Grade grade = null;
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab3db", "marian", "xxxx");
                Statement stmt = conn.createStatement();) {
            String strSelect = String.format("select value, dateGrade, feedback from grade where idStudent = '%s' and idHomework = %d", id.getKey().getId(),id.getValue().getId());
            ResultSet rset = null;
            try {
                rset = stmt.executeQuery(strSelect);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            while (rset.next()) {
                Float value = rset.getFloat("value");
                String dateGrade = rset.getDate("dateGrade").toString();
                String feedback = rset.getString("feedback");

                grade = new Grade(id.getKey(),id.getValue(), value, dateGrade,feedback);
            }
        }
        return grade;
    }

    @Override
    public Iterable<Grade> findAll() {
        readData();
        return entities.values();
    }

    @Override
    public void save(Grade entity) throws Exception {
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab3db", "marian", "xxxx");
                Statement stmt = conn.createStatement();) {
            String strSize = String.format("select count(*) from grade where idStudent = '%s' and idHomework=%d",entity.getId().getKey().getId(),entity.getId().getValue().getId());
            ResultSet rset = stmt.executeQuery(strSize);
            validator.validate(entity);
            int size = 0;
            while (rset.next()) {
                size = rset.getInt(1);
            }
            if(size == 0) {
                String strInsert = String.format("insert into grade values ('%s',%d,%f,CAST('%s' AS DATE),'%s')",
                        entity.getId().getKey().getId(),
                        entity.getId().getValue().getId(),
                        entity.getGrade(),
                        entity.getDate().toString(),
                        entity.getFeedback());
                stmt.executeUpdate(strInsert);
            }else{
                throw new Exception("Duplicate entity");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Pair<Student,Homework> id) throws IOException, SQLException {
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab3db", "marian", "xxxx");
                Statement stmt = conn.createStatement();) {
            String strDelete = String.format("delete from grade where idStudent = '%s' and idHomework = %d", id.getKey().getId(), id.getValue().getId());
            stmt.executeUpdate(strDelete);
            entities.remove(id);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Grade update(Grade entity) {
        this.validator.validate(entity);
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab3db", "marian", "xxxx");
                Statement stmt = conn.createStatement();) {
            String strUpdate = String.format("update grade set value = %f, date = CAST('%s' AS DATE), feedback = '%s' where idStudent = '%s' and idHomework = %d",
                    entity.getGrade(),
                    entity.getDate().toString(),
                    entity.getFeedback(),
                    entity.getId().getKey().getId(),
                    entity.getId().getValue().getId());
            stmt.executeUpdate(strUpdate);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return entity;
    }

    @Override
    public long size() throws Exception {
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab3db", "marian", "xxxx");
                Statement stmt = conn.createStatement();) {
            String strSize = "select count(*) from grade";
            ResultSet rset = stmt.executeQuery(strSize);
            while (rset.next()) {
                return rset.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
}
