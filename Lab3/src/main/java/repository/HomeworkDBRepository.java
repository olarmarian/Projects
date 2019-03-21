package repository;

import domain.Homework;
import validation.IValidation;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeworkDBRepository implements CrudRepository<Integer,Homework> {
    private Map<Integer,Homework> entities;
    IValidation<Homework> validator;
    public HomeworkDBRepository(IValidation<Homework> validator){
        this.entities = new HashMap<>();
        this.validator = validator;
    }

    protected void readData(){
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab3db", "marian", "xxxx");
                Statement stmt = conn.createStatement();) {
            String strSelect = String.format("select * from homework");
            ResultSet rset = null;
            try {
                rset = stmt.executeQuery(strSelect);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            while (rset.next()){
                int id = rset.getInt("id");
                String req = rset.getString("requirement");
                Integer deadline = rset.getInt("deadline");
                Integer courseWeek = rset.getInt("courseWeek");
                Integer homeworkWeek = rset.getInt("homeworkWeek");
                Homework homework = new Homework(id,req,deadline,courseWeek,homeworkWeek);
                entities.put(id,homework);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Homework findOne(Integer id) throws Exception {
        Homework homework= null;
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab3db", "marian", "xxxx");
                Statement stmt = conn.createStatement();) {
            String strSelect = String.format("select requirement, deadline, courseWeek, homeworkWeek from homework where id = %d", id);
            ResultSet rset = null;
            try {
                rset = stmt.executeQuery(strSelect);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            while (rset.next()) {
                String req = rset.getString("requirement");
                Integer deadline = rset.getInt("deadline");
                Integer courseWeek = rset.getInt("courseWeek");
                Integer homeworkWeek = rset.getInt("homeworkWeek");
                homework = new Homework(id,req,deadline,courseWeek,homeworkWeek);
            }
        }
        return homework;
    }

    @Override
    public Iterable<Homework> findAll() {
        readData();
        return entities.values();
    }
    @Override
    public void save(Homework entity) throws Exception {
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab3db", "marian", "xxxx");
                Statement stmt = conn.createStatement();) {
            String strSize = String.format("select count(*) from homework where id=%d",entity.getId());
            ResultSet rset = stmt.executeQuery(strSize);
            validator.validate(entity);
            int size = 0;
            while (rset.next()) {
                size = rset.getInt(1);
            }
            if(size == 0) {
                String strInsert = String.format("insert into homework values (%d,'%s',%d,%d,%d)",
                        entity.getId(),
                        entity.getRequirement(),
                        entity.getDeadline(),
                        entity.getCourseWeek(),
                        entity.getHomeworkWeek());
                stmt.executeUpdate(strInsert);
            }else {
                throw new Exception("Duplicate entity");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) throws  Exception {
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab3db", "marian", "xxxx");
                Statement stmt = conn.createStatement();) {
            int size = 0;
            String strSize =String.format("select count(*) from student where id='%s'",id);
            ResultSet rset = stmt.executeQuery(strSize);
            while (rset.next()) {
                size = rset.getInt(1);
            }
            if(size == 1) {
                String strDelete = String.format("delete from homework where id = %d", id);
                stmt.executeUpdate(strDelete);
            }else{
                throw new Exception("Homework not found");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Homework update(Homework entity) throws Exception{
        this.validator.validate(entity);
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab3db", "marian", "xxxx");
                Statement stmt = conn.createStatement();) {
            int size = 0;
            String strSize =String.format("select count(*) from homework where id=%d",entity.getId());
            ResultSet rset = stmt.executeQuery(strSize);
            while (rset.next()) {
                size = rset.getInt(1);
            }
            if(size == 1) {
                String strUpdate = String.format("update homework set id = %d, requirement = '%s', deadline = %d, courseWeek = %d, homeworkWeek = %d where id = %d",
                        entity.getId(),
                        entity.getRequirement(),
                        entity.getDeadline(),
                        entity.getCourseWeek(),
                        entity.getHomeworkWeek(),
                        entity.getId());
                stmt.executeUpdate(strUpdate);
            }else{
                throw new Exception("Homework not found");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return entity;
    }

    @Override
    public long size(){
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab3db", "marian", "xxxx");
                Statement stmt = conn.createStatement();) {
            String strSize = "select count(*) from homework";
            ResultSet rset = stmt.executeQuery(strSize);
            while (rset.next()) {
                return rset.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Homework> getPieceOfData(Integer start_idx, Integer length){
        List<Homework> data = new ArrayList<>();
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab3db", "marian", "xxxx");
                Statement stmt = conn.createStatement();) {
            String strSize =String.format("select * from homework limit %d offset %d",length,start_idx);
            ResultSet rset = stmt.executeQuery(strSize);
            while (rset.next()) {
                int id = rset.getInt("id");
                String req = rset.getString("requirement");
                Integer deadline = rset.getInt("deadline");
                Integer courseWeek = rset.getInt("courseWeek");
                Integer homeworkWeek = rset.getInt("homeworkWeek");
                Homework homework = new Homework(id,req,deadline,courseWeek,homeworkWeek);
                data.add(homework);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return data;
    }
}
