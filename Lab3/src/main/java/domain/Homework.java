package domain;

import repository.HasId;

import java.util.Objects;

public class Homework implements HasId<Integer> {

    private Integer nrHomework;
    private String requirement;
    private Integer deadline;
    private Integer courseWeek;
    private Integer homeworkWeek;

    public Homework(){}

    public Homework(Integer nrHomework, String requirement, Integer deadline, Integer courseWeek, Integer homeworkWeek) {
        this.nrHomework = nrHomework;
        this.requirement = requirement;
        this.deadline = deadline;
        this.courseWeek = courseWeek;
        this.homeworkWeek = homeworkWeek;
    }

    public Homework buildObject(String line){
        if(line.equals("")){
            return null;
        }else{
            String[] args = line.split("#");
            final Homework h = new Homework(Integer.parseInt(args[0]), args[1],Integer.parseInt(args[2]),Integer.parseInt(args[3]),Integer.parseInt(args[4]));
            return h;
        }
    }

    @Override
    public Integer getId() {
        return nrHomework;
    }

    @Override
    public void setId(Integer nrHomework) {
        this.nrHomework = nrHomework;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public Integer getDeadline() {
        return deadline;
    }

    public void setDeadline(Integer deadline) {
        this.deadline = deadline;
    }

    public Integer getCourseWeek() {
        return courseWeek;
    }

    public void setCourseWeek(Integer courseWeek) {
        this.courseWeek = courseWeek;
    }

    public Integer getHomeworkWeek() {
        return homeworkWeek;
    }

    public void setHomeworkWeek(Integer homeworkWeek) {
        this.homeworkWeek = homeworkWeek;
    }

    @Override
    public String toString() {
        return  nrHomework + "#" + requirement + "#" + deadline +"#" + courseWeek+ "#" + homeworkWeek;
    }

    public boolean requestUpdateDeadline(Integer nrWeeks){
        if(nrWeeks>=this.deadline){
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Homework tema = (Homework) o;
        return Objects.equals(nrHomework, tema.nrHomework);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nrHomework, requirement, deadline, courseWeek, homeworkWeek);
    }
}
