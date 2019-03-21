package UI.Gui;

import domain.Grade;
import domain.Homework;
import domain.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import service.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Controller implements Observer<Event> {
    public static Service service;

    private ObservableList<Student> modelStud;

    public Controller(Service service) throws Exception {
        this.service = service;
        this.service.addObserver(this);
        modelStud = FXCollections.observableList(StreamSupport.stream(service.allStudents().spliterator(), false).collect(Collectors.toList()));

    }

    public ObservableList getList(String type) {

        if(type.equals("Student")){
            return modelStud;
        }
        return null;
    }

    public void addStudent(String id, String name, String email, Integer group, String teacher) throws Exception {
        service.addStudent(id, name, group, email, teacher);
    }
    public void updStudent(String id, String name, String email, Integer group, String teacher) throws Exception {
        service.updStudent(id, name, group, email, teacher);
    }
    public void remStudent(String id) throws Exception {
        service.remStudent(id);
    }

    @Override
    public void update(Event e) {
        StudentEvent sEvent = null;
        HomeworkEvent hEvent = null;
        GradeEvent gEvent = null;
        try {
            sEvent = (StudentEvent) e;
        }catch (Exception ex){
            System.out.println(ex.getMessage()+" "+ex.getCause()+" "+ex.getLocalizedMessage());
        }
        try {
            hEvent = (HomeworkEvent) e;
        }catch (Exception ex){
            System.out.println(ex.getMessage()+" "+ex.getCause()+" "+ex.getLocalizedMessage());
        }
        try {
            gEvent = (GradeEvent) e;
        }catch (Exception ex){
            System.out.println(ex.getMessage()+" "+ex.getCause()+" "+ex.getLocalizedMessage());
        }

        if(sEvent != null){
            if(sEvent.getType()==ChangeEventType.ADD){
                modelStud.add(sEvent.getData());
                System.out.println();
            }
            if(sEvent.getType()==ChangeEventType.UPDATE){
                modelStud.remove(sEvent.getOldData());
                modelStud.add(sEvent.getData());
            }
            if(sEvent.getType()==ChangeEventType.DELETE){
                modelStud.remove(sEvent.getOldData());
            }
        }
    }
}