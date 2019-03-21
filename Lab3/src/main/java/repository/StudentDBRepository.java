package repository;
import domain.Student;
import validation.IValidation;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StudentDBRepository implements CrudRepository<String,Student>{

    private Map<String,Student> entities;
    private IValidation<Student> validator;

    public StudentDBRepository(IValidation<Student> val){
        this.entities = new HashMap<>();
        this.validator = val;
    }

    @Override
    public List<Student> getPieceOfData(Integer start_idx, Integer length) {

        List<Student> data = new ArrayList<>();
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab3db", "marian", "xxxx");
                Statement stmt = conn.createStatement();) {
            String strSelect = String.format("select * from student limit %d offset %d",length,start_idx);
            ResultSet rset = null;
            try {
                rset = stmt.executeQuery(strSelect);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            while (rset.next()){
                String id = rset.getString("id");
                String name = rset.getString("name");
                String email = rset.getString("email");
                String teacher = rset.getString("teacher");
                Integer group = rset.getInt("grupa");
                Student student = new Student(id, name, group, email, teacher);
                data.add(student);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return data;
    }

    protected void readData(){
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab3db", "marian", "xxxx");
                Statement stmt = conn.createStatement();) {
            String strSelect = String.format("select * from student");
            ResultSet rset = null;
            try {
                rset = stmt.executeQuery(strSelect);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            while (rset.next()){
                String id = rset.getString("id");
                String name = rset.getString("name");
                String email = rset.getString("email");
                String teacher = rset.getString("teacher");
                Integer group = rset.getInt("grupa");
                Student student = new Student(id, name, group, email, teacher);
                entities.put(id,student);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Student findOne(String id) {
        Student student = null;
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab3db", "marian", "xxxx");
                Statement stmt = conn.createStatement();) {
            String strSelect = String.format("select name, grupa, email, teacher from student where id = '%s'", id);
            ResultSet rset = null;
            try {
                rset = stmt.executeQuery(strSelect);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            while (rset.next()) {
            String name = rset.getString("name");
                String email = rset.getString("email");
                String teacher = rset.getString("teacher");
                Integer group = rset.getInt("grupa");
                student = new Student(id, name, group, email, teacher);
            }
        }
        catch (Exception ex){}
        return student;
    }

    @Override
    public Iterable<Student> findAll() {
        readData();
        return entities.values();
    }
    @Override
    public void save(Student entity) throws Exception {
        validator.validate(entity);
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab3db", "marian", "xxxx");
                Statement stmt = conn.createStatement();) {
            int size = 0;
            String strSize =String.format("select count(*) from student where id='%s'",entity.getId());
            ResultSet rset = stmt.executeQuery(strSize);
            while (rset.next()) {
                size = rset.getInt(1);
            }
            if(size == 0) {
                String strInsert = String.format("insert into student values ('%s','%s',%d,'%s','%s')",
                        entity.getId(),
                        entity.getName(),
                        entity.getGroup(),
                        entity.getEmail(),
                        entity.getTeacher());
                stmt.executeUpdate(strInsert);
            }else{
                throw new Exception("Duplicate identity");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(String id)throws Exception{
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab3db", "marian", "xxxx");
                Statement stmt = conn.createStatement();) {
            int size = 0;
            String strSize =String.format("select count(*) from student where id='%s'",id);
            ResultSet rset = stmt.executeQuery(strSize);
            while (rset.next()) {
                size = rset.getInt(1);
            }
            if(size == 1){
                String strDelete = String.format("delete from student where id = '%s'", id);
                stmt.executeUpdate(strDelete);
            }else{
                throw new Exception("Student not found");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Student update(Student entity) throws Exception {
        this.validator.validate(entity);
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab3db", "marian", "xxxx");
                Statement stmt = conn.createStatement();) {
            int size = 0;
            String strSize =String.format("select count(*) from student where id='%s'",entity.getId());
            ResultSet rset = stmt.executeQuery(strSize);
            while (rset.next()) {
                size = rset.getInt(1);
            }
            if(size == 1) {
                String strUpdate = String.format("update student set id = '%s', name = '%s', grupa = %d, email ='%s', teacher ='%s' where id = '%s'",
                        entity.getId(),
                        entity.getName(),
                        entity.getGroup(),
                        entity.getEmail(),
                        entity.getTeacher(),
                        entity.getId());
                stmt.executeUpdate(strUpdate);
            }else{
                throw new Exception("Student not found");
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
            String strSize = "select count(*) from student";
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
