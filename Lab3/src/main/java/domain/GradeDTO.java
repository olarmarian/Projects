package domain;

import java.time.LocalDate;

public class GradeDTO {
    private String studentId;


    private String studentName;
    private Integer homewordId;
    private Float grade;
    private LocalDate date;

    public GradeDTO(String studentId, String studentName, Integer hId, float gr, LocalDate date) {
        this.studentName = studentName;
        this.homewordId = hId;
        this.grade = gr;
        this.studentId = studentId;
        this.date = date;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentId() {return studentId;}

    public void setStudentId(String studentId) {this.studentId = studentId;}

    public Integer getHomewordId() {return homewordId;}

    public void setHomewordId(Integer homewordId) {
        this.homewordId = homewordId;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(Float gr) {
        this.grade = gr;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


}
