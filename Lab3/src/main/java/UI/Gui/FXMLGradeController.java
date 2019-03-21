package UI.Gui;

import domain.Grade;
import domain.Student;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import service.Service;

public class FXMLGradeController {

    Float penality;
    private Grade grade;
    private Integer mWeeks;
    private Service service;

    @FXML
    private Label lbl_idStudent,lbl_idHomework,lbl_valueGrade,lbl_weekGrade,lbl_feedbackGrade,lbl_mWeeks,lbl_penality;

    @FXML
    private Button btn_acceptAddGrade,btn_cancelAddGrade;


    public FXMLGradeController(){
        System.out.println("FXMLGradeController");
    }

    public void setFXMLGradeController(Grade g, Integer mw,Service s) throws Exception {
        this.grade = g;
        this.mWeeks = mw;
        this.service = s;
        penality = grade.decMaxGrade(service.findHomework(grade.getId().getValue().getId()).getDeadline()+mWeeks,Integer.parseInt(Long.toString(service.collageWeek())));
        lbl_idStudent.setText(grade.getId().getKey().getName());
        lbl_idHomework.setText(grade.getId().getValue().getId().toString());
        lbl_valueGrade.setText(grade.getGrade().toString());
        lbl_weekGrade.setText(grade.getDate().toString());
        lbl_feedbackGrade.setText(grade.getFeedback());
        lbl_mWeeks.setText(mWeeks.toString());
        lbl_penality.setText(Float.toString(penality));
    }

    @FXML
    public void initialize(){
        System.out.println("Initialized FXMLGradeController");
    }

    @FXML
    public void acceptAdditionPreview() {
        try {
            service.addGrade(grade.getId().getKey().getId(),grade.getId().getValue().getId(),grade.getGrade()-penality,grade.getDate().toString(),grade.getFeedback(),mWeeks);
        } catch (Exception e) {
            Alert alert = new Alert( Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.show();
        }
        Stage stage = (Stage) btn_acceptAddGrade.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void cancelPreview(){
        Stage stage = (Stage) btn_cancelAddGrade.getScene().getWindow();
        stage.close();
    }
}
