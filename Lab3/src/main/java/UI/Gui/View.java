package UI.Gui;

import domain.Student;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;

public class View {

    private TableView<Student> tableView;
    private TextField idText;
    private TextField nameText;
    private TextField groupText;
    private TextField emailText;
    private TextField teacherText;
    private Controller controller;

    public View(Controller controller){
        tableView = new TableView<>();
        this.controller = controller;
    }


    public BorderPane getView(){
        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(createTable());
        borderPane.setRight(createStudent());
        return borderPane;
    }

    private void initTableView(){
        tableView.setItems(controller.getList("Student"));
        TableColumn<Student, String> idColumn = new TableColumn<>("Id");
        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Student, String> groupColumn = new TableColumn<>("Group");
        TableColumn<Student, String> emailColumn = new TableColumn<>("Email");
        TableColumn<Student, String> teacherColumn = new TableColumn<>("Teacher");

        tableView.getColumns().addAll(idColumn, nameColumn, groupColumn,emailColumn,teacherColumn);
        idColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("group"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("email"));
        teacherColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("teacher"));
        tableView.getSelectionModel().selectedItemProperty().addListener((observer, oldData, newData)-> showDetails(newData));

    }

    private void showDetails(Student student){
        if(student!=null){
            idText.setText(student.getId());
            nameText.setText(student.getName());
            groupText.setText(student.getGroup().toString());
            emailText.setText(student.getEmail());
            teacherText.setText(student.getTeacher());
        }
    }

    private StackPane createTable(){
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(tableView);
        initTableView();
        Insets ins = new Insets(10);
        stackPane.setPadding(ins);
        return stackPane;
    }

    private GridPane createStudent(){
        GridPane gridPane = new GridPane();
        gridPane.add(new Label("Id"), 0, 0);
        gridPane.add(idText = new TextField(), 1, 0);
        gridPane.add(new Label("Name"), 0, 1);
        gridPane.add(nameText = new TextField(), 1, 1);
        gridPane.add(new Label("Group"), 0, 2);
        gridPane.add(groupText = new TextField(), 1, 2);
        gridPane.add(new Label("Email"), 0, 3);
        gridPane.add(emailText = new TextField(), 1, 3);
        gridPane.add(new Label("Teacher"), 0, 4);
        gridPane.add(teacherText = new TextField(), 1, 4);

        HBox buttonsBox = new HBox();
        Button add = new Button("Add");
        add.autosize();
        add.setTextAlignment(TextAlignment.CENTER);
        add.setOnAction(event -> {
            this.addHandler();
        });
        buttonsBox.getChildren().add(add);
        Button upd = new Button("Update");
        upd.autosize();
        upd.setTextAlignment(TextAlignment.CENTER);
        upd.setOnAction(event ->{
            this.updHandler();
        });
        buttonsBox.getChildren().add(upd);
        Button rem = new Button("Remove");
        rem.autosize();
        rem.setTextAlignment(TextAlignment.CENTER);
        rem.setOnAction(event ->{
            this.remHandler();
        });
        buttonsBox.getChildren().add(rem);

        gridPane.add(buttonsBox, 0, 5,2, 1);
        Insets ins = new Insets(10);
        gridPane.setPadding(ins);
        return gridPane;
    }

    private void addHandler(){
        try {
            controller.addStudent(idText.getText(), nameText.getText(),emailText.getText(), Integer.parseInt(groupText.getText()),teacherText.getText());
            idText.clear();
            groupText.clear();
            emailText.clear();
            teacherText.clear();
            nameText.clear();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Student error");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }
    private void updHandler(){
        try {
            controller.updStudent(idText.getText(), nameText.getText(),emailText.getText(), Integer.parseInt(groupText.getText()),teacherText.getText());
            idText.clear();
            groupText.clear();
            emailText.clear();
            teacherText.clear();
            nameText.clear();
            tableView.refresh();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Student error");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }
    private void remHandler(){
        try {
            controller.remStudent(idText.getText());
            idText.clear();
            groupText.clear();
            emailText.clear();
            teacherText.clear();
            nameText.clear();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Student error");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

}
