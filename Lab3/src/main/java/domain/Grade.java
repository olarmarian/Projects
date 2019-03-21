package domain;

import javafx.util.Pair;
import repository.HasId;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


public class Grade implements HasId<Pair<Student,Homework>> {

    private Student student;
    private Homework homework;
    private LocalDate date;
    private Float grade;
    private String feedback;

    private Float maxGrade = 10.0f;

    public Grade(){}

    public Grade(Student s, Homework h, Float grade, String date, String feedback){
        this.student = s;
        this.homework = h;
        this.grade = grade;
        this.feedback = feedback;
        this.date = LocalDate.parse(date,dateFormatter);
    }
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public DateTimeFormatter getDateFormatter(){
        return dateFormatter;
    }

    public Grade buildObject(Student s, Homework h, String line){
        if(line.equals("")){
            return null;
        }else{
            String[] args = line.split("#");
            final Grade g = new Grade(s,h, Float.parseFloat(args[2]),args[3] ,args[4]);
            return g;
        }
    }

    @Override
    public Pair<Student,Homework> getId() {
        return new Pair<Student,Homework>(this.student,this.homework) ;
    }

    @Override
    public void setId(Pair<Student,Homework> id) {
        this.student = id.getKey();
        this.homework = id.getValue();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = LocalDate.parse(date);
    }

    public Float getGrade() {
        return grade;
    }

    public void setGrade(Float grade) {
        this.grade = grade;
    }

    public String getFeedback() { return feedback; }

    public void setFeedback(String feedback) { this.feedback = feedback; }

    public Float getMaxGrade(){
        return maxGrade;
    }

    public float decMaxGrade(Integer deadline,Integer week){
        int nr_weeks = week-deadline;
        for(int i = 0 ; i < nr_weeks ; i++){
            maxGrade = maxGrade - 2.5f;
        }
        return 10f - maxGrade;
    }

    @Override
    public String toString() {
        return this.student.getId() + "#" + this.homework.getId().toString() + "#" + this.grade + "#" + this.date + "#" + this.feedback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grade grade1 = (Grade) o;
        return Objects.equals(getId(), grade1.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, homework, grade, feedback);
    }
}
