package UI.Gui;

import domain.Grade;
import domain.Homework;
import domain.PairCustomized;
import domain.Student;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import service.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class FXMLController implements Observer<Event> {
    private Service service;
    private ObservableList<Student> modelStud;
    private ObservableList<Homework> modelHW;
    private ObservableList<Grade> modelGrade;
    private Integer itemsPerPage = 7, fromS = 0, toS = 0,fromH = 0, toH = 0,fromG = 0, toG = 0;

    private DatePicker dp = new DatePicker();

    @FXML private BorderPane bp;
    @FXML private TableView<Student> tableViewStudent;
    @FXML private TableView<Homework> tableViewHomework;
    @FXML private TableView<Grade> tableViewGrades;

    @FXML private TableColumn<Student,String> idSColumn;
    @FXML private TableColumn<Student,String> nameColumn;
    @FXML private TableColumn<Student,String> groupColumn;
    @FXML private TableColumn<Student,String> emailColumn;
    @FXML private TableColumn<Student,String> teacherColumn;

    @FXML private TableColumn<Homework,String> idHColumn;
    @FXML private TableColumn<Homework,String> reqColumn;
    @FXML private TableColumn<Homework,String> deadlineColumn;
    @FXML private TableColumn<Homework,String> courseWeekColumn;
    @FXML private TableColumn<Homework,String> homeworkWeekColumn;

    @FXML private TableColumn<Grade,String> idSGColumn;
    @FXML private TableColumn<Grade,String> idHGColumn;
    @FXML private TableColumn<Grade,String> gradeColumn;
    @FXML private TableColumn<Grade,String> weekColumn;
    @FXML private TableColumn<Grade,String> feedbackColumn;
    @FXML private TableColumn<Grade,String> nameSGColumn;

    @FXML private Pagination paginationGrades,paginationHomeworks,paginationStudents;

    @FXML private Pane pnl_home,pnl_student,pnl_homework,pnl_grade;

    @FXML private Button btn_home,
            btn_student,
            btn_homework,
            btn_grade,
            btn_addStudent,
            btn_remStudent,
            btn_updStudent,
            btn_addHomework,
            btn_remHomework,
            btn_updHomework,
            btn_addGrade,
            btn_filters,
            btn_reports;

    @FXML private ComboBox<PairCustomized<Integer,String>> comboBoxHomework;
    @FXML private ComboBox<Integer> comboBoxStudentGroup;

    @FXML private TextField student_idTxt,
            student_emailTxt,
            student_nameTxt,
            student_teacherTxt,
            homework_idTxt,
            homework_reqTxt,
            homework_deadlineTxt,
            homework_cWeekTxt,
            homework_hWeekTxt,
            grade_idSGTxt,
            grade_gradeTxt,
            grade_weekTxt,
            grade_mWeeksTxt;

    @FXML private TextArea grade_feedbackTxt;

    public FXMLController(){
        System.out.println("Controller");
    }

    public List<Grade> getDataGrades(){
        int size = this.service.allGradesSize();
        if(this.toG > size){
            this.toG = size;
        }
        return service.getPieceDataGrades(fromG,itemsPerPage);
    }
    public List<Student> getDataStudents(){
        int size = this.service.allStudentsSize();
        if(this.toS > size){
            this.toS = size;
        }
        return service.getPieceDataStudents(fromS,itemsPerPage);
    }
    public List<Homework> getDataHomeworks(){
        int size = this.service.allHomeworksSize();
        if(this.toH > size){
            this.toH = size;
        }
        return service.getPieceDataHomeworks(fromH,itemsPerPage);
    }

    private Node createPageGrade(int pageIndex){
        this.fromG = pageIndex * itemsPerPage;
        this.toG = this.fromG + itemsPerPage;
        tableViewGrades.setItems(FXCollections.observableList(getDataGrades()));
        return tableViewGrades;
    }

    private Node createPageHomework(int pageIndex){
        this.fromH = pageIndex * itemsPerPage;
        this.toH = this.fromH + itemsPerPage;
        tableViewHomework.setItems(FXCollections.observableList(getDataHomeworks()));
        return tableViewHomework;
    }

    private Node createPageStudent(int pageIndex){
        this.fromS = pageIndex * itemsPerPage;
        this.toS = this.fromS + itemsPerPage;
        tableViewStudent.setItems(FXCollections.observableList(getDataStudents()));
        return tableViewStudent;
    }


    @FXML private void handleButtonAction(ActionEvent event) {
        if(event.getSource() == btn_home){
            pnl_home.toFront();
        }else if(event.getSource() == btn_student){
            pnl_student.toFront();
        }else if(event.getSource() == btn_homework){
            pnl_homework.toFront();
        }else if(event.getSource() == btn_grade){
            pnl_grade.toFront();
        } else if(event.getSource() == btn_filters){
            Stage filterStage = new Stage();
            Parent root = null;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/filters.fxml"));
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            FXMLFilterController ctrl = loader.getController();
            ctrl.setFXMLFilterController(this.service);

            Scene scene = new Scene(root);
            filterStage.setTitle("Lab application");
            filterStage.setScene(scene);
            filterStage.show();

        }else if(event.getSource() == btn_reports){
            Stage raportStage = new Stage();
            Parent root = null;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/raports.fxml"));
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            FXMLRaportController ctrl = loader.getController();
            ctrl.setFXMLRaportController(this.service,raportStage);

            Scene scene = new Scene(root);
            raportStage.setTitle("Lab application");
            raportStage.setScene(scene);
            raportStage.show();
        }
    }

    @FXML
    private void initialize() throws Exception {
        System.out.println("Initialized");
        idSColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("name"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("group"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("email"));
        teacherColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("teacher"));
        tableViewStudent.getSelectionModel().selectedItemProperty().addListener((observer, oldData, newData)-> showDetailsStudent(newData));

        idHColumn.setCellValueFactory(new PropertyValueFactory<Homework,String>("id"));
        reqColumn.setCellValueFactory(new PropertyValueFactory<Homework,String>("requirement"));
        deadlineColumn.setCellValueFactory(new PropertyValueFactory<Homework,String>("deadline"));
        courseWeekColumn.setCellValueFactory(new PropertyValueFactory<Homework,String>("courseWeek"));
        homeworkWeekColumn.setCellValueFactory(new PropertyValueFactory<Homework,String>("homeworkWeek"));
        tableViewHomework.getSelectionModel().selectedItemProperty().addListener((observer,oldData,newData)-> showDetailsHomework(newData));

        courseWeekColumn.setVisible(false);
        homeworkWeekColumn.setVisible(false);

        btn_addStudent.getStylesheets().add(
                getClass().getResource("/ButtonMenu.css").toExternalForm());
        btn_addStudent.getStyleClass().add("ButtonMenu");

        btn_remStudent.getStylesheets().add(
                getClass().getResource("/ButtonMenu.css").toExternalForm());
        btn_remStudent.getStyleClass().add("ButtonMenu");

        btn_updStudent.getStylesheets().add(
                getClass().getResource("/ButtonMenu.css").toExternalForm());
        btn_updStudent.getStyleClass().add("ButtonMenu");

        btn_addHomework.getStylesheets().add(
                getClass().getResource("/ButtonMenu.css").toExternalForm());
        btn_addHomework.getStyleClass().add("ButtonMenu");

        btn_updHomework.getStylesheets().add(
                getClass().getResource("/ButtonMenu.css").toExternalForm());
        btn_updHomework.getStyleClass().add("ButtonMenu");

        btn_remHomework.getStylesheets().add(
                getClass().getResource("/ButtonMenu.css").toExternalForm());
        btn_remHomework.getStyleClass().add("ButtonMenu");

        btn_addGrade.getStylesheets().add(
                getClass().getResource("/ButtonMenu.css").toExternalForm());
        btn_addGrade.getStyleClass().add("ButtonMenu");


        tableViewStudent.getStylesheets().add(getClass().getResource("/TableView.css").toExternalForm());
        tableViewStudent.getStyleClass().add("TableView");

        tableViewHomework.getStylesheets().add(getClass().getResource("/TableView.css").toExternalForm());
        tableViewHomework.getStyleClass().add("TableView");

        tableViewGrades.getStylesheets().add(getClass().getResource("/TableView.css").toExternalForm());
        tableViewGrades.getStyleClass().add("TableView");


        idSGColumn.setCellValueFactory(new PropertyValueFactory<Grade,String>("key"));
        try {
            nameSGColumn.setCellValueFactory(n-> {
                try {
                    return new SimpleStringProperty(n.getValue().getId().getKey().getName());
                } catch (Exception e) { }
                return new SimpleStringProperty("deleted");
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            idHGColumn.setCellValueFactory(n-> {
                try {
                    return new SimpleStringProperty(n.getValue().getId().getValue().getRequirement());
                } catch (Exception ex) { }
                return new SimpleStringProperty("deleted");
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        gradeColumn.setCellValueFactory(new PropertyValueFactory<Grade,String>("grade"));
        weekColumn.setCellValueFactory(new PropertyValueFactory<Grade,String>("date"));
        feedbackColumn.setCellValueFactory(new PropertyValueFactory<Grade,String>("feedback"));
        tableViewGrades.getSelectionModel().selectedItemProperty().addListener((observer,oldData,newData)-> getIdStudentFromTableview(newData));

        pnl_home.toFront();
    }

    public void setFXMLController(Service serv) throws Exception {
        this.service = serv;
        this.service.addObserver(this);
        loadData();
        TextFields.bindAutoCompletion(grade_idSGTxt,service.allStudents().stream().map(x->new PairCustomized(x.getId(),x.getName())).distinct().collect(Collectors.toList()));
    }

    private void showDetailsStudent(Student student){
        if(student!=null){
            student_idTxt.setText(student.getId());
            student_nameTxt.setText(student.getName());
            comboBoxStudentGroup.setValue(student.getGroup());
            student_emailTxt.setText(student.getEmail());
            student_teacherTxt.setText(student.getTeacher());
        }
    }
    private void getIdStudentFromTableview(Grade grade){
        if(grade !=null){
            grade_idSGTxt.setText(grade.getId().getKey().getId());
        }
    }

    private void showDetailsHomework(Homework homework){
        if(homework!=null){
            homework_idTxt.setText(homework.getId().toString());
            homework_reqTxt.setText(homework.getRequirement());
            homework_deadlineTxt.setText(homework.getDeadline().toString());
            homework_cWeekTxt.setText(homework.getCourseWeek().toString());
            homework_hWeekTxt.setText(homework.getHomeworkWeek().toString());
        }
    }

    private void showError(String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(header);
        alert.setContentText(content);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/Alert.css").toExternalForm());
        dialogPane.getStyleClass().add("Alert");
        alert.show();
    }

    @FXML private void addStudentUI(ActionEvent event) {
        if(event.getSource()==btn_addStudent){
            try {
                service.addStudent(student_idTxt.getText(),student_nameTxt.getText(),comboBoxStudentGroup.getValue(),student_emailTxt.getText(),student_teacherTxt.getText());
            } catch (Exception e) {
                showError("Student addition error", e.getMessage());
            }
            student_emailTxt.clear();
            comboBoxStudentGroup.getSelectionModel().selectFirst();
            student_idTxt.clear();
            student_nameTxt.clear();
            student_teacherTxt.clear();
        }
    }
    @FXML private void updStudentUI(ActionEvent event){
        if(event.getSource()==btn_updStudent){
            try {
                service.updStudent(student_idTxt.getText(),student_nameTxt.getText(),comboBoxStudentGroup.getValue(),student_emailTxt.getText(),student_teacherTxt.getText());
            } catch (Exception e) {
                showError("Student update error",e.getMessage());
            }
            student_emailTxt.clear();
            comboBoxStudentGroup.getSelectionModel().selectFirst();
            student_idTxt.clear();
            student_nameTxt.clear();
            student_teacherTxt.clear();
        }
    }
    @FXML private void remStudentUI(ActionEvent event){
        if(event.getSource()==btn_remStudent){
            try {
                service.remStudent(student_idTxt.getText());
            } catch (ConcurrentModificationException e) { }
            catch (Exception e) {
                showError("Student remove error",e.getMessage());
            }
            student_emailTxt.clear();
            comboBoxStudentGroup.getSelectionModel().selectFirst();
            student_idTxt.clear();
            student_nameTxt.clear();
            student_teacherTxt.clear();
        }
    }
    @FXML private void addHomeworkUI(ActionEvent event){
        if(event.getSource()==btn_addHomework){
            try {
                service.addHomework(Integer.parseInt(homework_idTxt.getText()),homework_reqTxt.getText(),Integer.parseInt(homework_deadlineTxt.getText()),Integer.parseInt(homework_cWeekTxt.getText()),Integer.parseInt(homework_hWeekTxt.getText()));
            } catch (Exception e) {
                showError("Homework addition error",e.getMessage());
            }
            homework_hWeekTxt.clear();
            homework_cWeekTxt.clear();
            homework_deadlineTxt.clear();
            homework_reqTxt.clear();
            homework_idTxt.clear();
        }
    }
    @FXML private void remHomeworkUI(ActionEvent event){
        if(event.getSource()==btn_remHomework){
            try {
                service.remHomework(Integer.parseInt(homework_idTxt.getText()));
            } catch (Exception e) {
                showError("Homework remove error",e.getMessage());
            }
            homework_hWeekTxt.clear();
            homework_cWeekTxt.clear();
            homework_deadlineTxt.clear();
            homework_reqTxt.clear();
            homework_idTxt.clear();
        }
    }
    @FXML private void updHomeworkUI(ActionEvent event){
        if(event.getSource()==btn_updHomework){
            try {
                service.updHomework(Integer.parseInt(homework_idTxt.getText()),homework_reqTxt.getText(),Integer.parseInt(homework_deadlineTxt.getText()),Integer.parseInt(homework_cWeekTxt.getText()),Integer.parseInt(homework_hWeekTxt.getText()));
            } catch (Exception e) {
                showError("Homework update error",e.getMessage());
            }
            homework_hWeekTxt.clear();
            homework_cWeekTxt.clear();
            homework_deadlineTxt.clear();
            homework_reqTxt.clear();
            homework_idTxt.clear();
        }
    }

    @FXML private void addGradeUI(ActionEvent event) throws Exception {
        if(event.getSource()==btn_addGrade){
            try {
                Student s = service.findStudent(grade_idSGTxt.getText());
                if (s == null) {
                    s = service.findByName(grade_idSGTxt.getText());
                }
                if (s != null) {
                    Homework h = service.findHomework(comboBoxHomework.getValue().getKey());
                    Grade g = new Grade(s, h, Float.parseFloat(grade_gradeTxt.getText()), grade_weekTxt.getText(), grade_feedbackTxt.getText());
                    Integer mWeeks = Integer.parseInt(grade_mWeeksTxt.getText());

                    Stage gradeStage = new Stage();
                    Parent root = null;
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/grade.fxml"));
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    FXMLGradeController ctrl = loader.getController();
                    try {
                        ctrl.setFXMLGradeController(g, mWeeks, this.service);
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Grade addition error");
                        alert.setContentText(e.getMessage());
                        alert.show();
                    }
                    Scene scene = new Scene(root);
                    gradeStage.setTitle("Lab application");
                    gradeStage.setScene(scene);
                    gradeStage.show();
                    grade_mWeeksTxt.clear();
                    grade_idSGTxt.clear();
                    grade_gradeTxt.clear();
                    grade_weekTxt.clear();
                    grade_feedbackTxt.clear();
                } else {
                    showError("Grade addition error", "Student not found");
                }
            }catch (NumberFormatException e){
                showError("Grade addition error","Complete the fields or give a valid value for 'Nota'");
            }  catch (Exception e) {
                showError("Grade addition error","Student or homework not found");
            }
        }
    }

    public void filterStudentName(){
        paginationGrades.setPageCount(6);
        paginationGrades.setPageFactory(this::filterStudentNameList);

    }

    private Node filterStudentNameList(Integer pageIndex) {
        Predicate<Grade> p1 = n -> n.getId().getKey().getName().toLowerCase().contains(grade_idSGTxt.getText().toLowerCase());
        this.fromG = pageIndex * itemsPerPage;
        this.toG = this.fromG + itemsPerPage;
        try {
            List<Grade> list = modelGrade.stream().filter(p1).sorted(Comparator.comparing(g -> g.getId().getValue().getRequirement())).collect(Collectors.toList());
            paginationGrades.setPageCount(list.size()/itemsPerPage + 1);
            if(toG > list.size()){
                this.toG = list.size();
            }
            ObservableList<Grade> pageElems = FXCollections.observableList(list.subList(fromG,toG));
            tableViewGrades.setItems(pageElems);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tableViewGrades;
    }

    @FXML
    private void loadData() throws Exception {
        modelStud = FXCollections.observableList(StreamSupport.stream(service.allStudents().spliterator(), false).sorted(Comparator.comparing(Student::getId)).collect(Collectors.toList()));
        modelHW = FXCollections.observableList(StreamSupport.stream(service.allHomeworks().spliterator(), false).sorted(Comparator.comparing(Homework::getId)).collect(Collectors.toList()));
        modelGrade = FXCollections.observableList(StreamSupport.stream(service.allGrades().spliterator(), false).sorted(Comparator.comparing(Grade::getGrade)).collect(Collectors.toList()));

        List< PairCustomized<Integer,String> > listHW = service.listOfHomeworks();
        ObservableList<PairCustomized<Integer,String>> modelHomework = FXCollections.observableList(listHW);
        ObservableList<Integer> modelGroups = FXCollections.observableList(service.getGroupList());
        comboBoxHomework.setItems(modelHomework);
        comboBoxHomework.getSelectionModel().selectFirst();
        comboBoxStudentGroup.setItems(modelGroups);
        comboBoxStudentGroup.getSelectionModel().selectFirst();
        loadPages();
    }

    private void loadPages(){
        int paginationGradesSize = (service.allGradesSize() / itemsPerPage) + 1;
        int paginationStudentsSize = (service.allStudentsSize() / itemsPerPage) + 1;
        int paginationHomeworksSize = (service.allHomeworksSize() / itemsPerPage) + 1;
        paginationGrades.setPageCount(paginationGradesSize);
        paginationGrades.setPageFactory(this::createPageGrade);
        paginationStudents.setPageCount(paginationStudentsSize);
        paginationStudents.setPageFactory(this::createPageStudent);
        paginationHomeworks.setPageCount(paginationHomeworksSize);
        paginationHomeworks.setPageFactory(this::createPageHomework);
    }

    @Override
    public void update(Event e) throws Exception {
        StudentEvent sEvent = null;
        HomeworkEvent hEvent = null;
        GradeEvent gEvent = null;
        try {
            sEvent = (StudentEvent) e;
        }catch (Exception ex){ }
        try {
            hEvent = (HomeworkEvent) e;
        }catch (Exception ex){ }
        try {
            gEvent = (GradeEvent) e;
        }catch (Exception ex){ }

        if(sEvent != null){
            if(sEvent.getType()==ChangeEventType.ADD){
                modelStud.add(sEvent.getData());
                loadData();
            }
            if(sEvent.getType()==ChangeEventType.UPDATE){
                modelStud.remove(sEvent.getOldData());
                modelStud.add(sEvent.getData());
                loadData();
            }
            if(sEvent.getType()==ChangeEventType.DELETE){
                modelStud.remove(sEvent.getOldData());
                loadData();
            }
        }else if(hEvent != null){
            if(hEvent.getType()==ChangeEventType.ADD){
                modelHW.add(hEvent.getData());
                loadData();
            }
            if(hEvent.getType()==ChangeEventType.UPDATE){
                modelHW.remove(hEvent.getOldData());
                modelHW.add(hEvent.getData());
                loadData();
            }
            if(hEvent.getType()==ChangeEventType.DELETE){
                modelHW.remove(hEvent.getOldData());
                loadData();
            }
        }else if(gEvent != null){
            if(gEvent.getType()==ChangeEventType.ADD){
                modelGrade.add(gEvent.getData());
                loadData();
            }
            if(gEvent.getType()==ChangeEventType.UPDATE){
                modelGrade.remove(gEvent.getOldData());
                modelGrade.add(gEvent.getData());
            }
            if(gEvent.getType()==ChangeEventType.DELETE){
                modelGrade.remove(gEvent.getOldData());
            }
        }
    }
}
