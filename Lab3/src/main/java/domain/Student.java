package domain;


import repository.HasId;

import java.util.Objects;


public class Student implements HasId<String> {
    private String id;
    private String name;
    private Integer group;
    private String email;
    private String teacher;

    public Student(){}

    public Student(String idStudent, String name, Integer group, String email, String teacher) {
        this.id = idStudent;
        this.name = name;
        this.group = group;
        this.email = email;
        this.teacher = teacher;
    }

    public Student buildObject(String line){
        if(line.equals("")){
            return null;
        }else{
            String[] args = line.split("#");
            final Student student = new Student(args[0], args[1],Integer.parseInt(args[2]),args[3],args[4]);
            return student;
        }
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String idStudent) {
        this.id = idStudent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String indrumator) {
        this.teacher = indrumator;
    }

    @Override
    public String toString() {
        return this.id + "#" + this.name + "#" + this.group + "#" + this.email + "#" +this.teacher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, group, email, teacher);
    }
}
