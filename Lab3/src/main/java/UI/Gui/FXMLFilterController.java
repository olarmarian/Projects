package UI.Gui;

import domain.Grade;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.Service;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class FXMLFilterController {

    private Service service;
    private ObservableList<Grade> modelGrade;

    @FXML private TableView<Grade> tableViewFilter;

    @FXML private TableColumn<Grade,String> nameStudentColumn;
    @FXML private TableColumn<Grade,String> reqHomeworkColumn;
    @FXML private TableColumn<Grade,String> valueGradeColumn;

    @FXML private TextField nameFilterTxt,homeworkFilterTxt;

    @FXML private ComboBox<Integer> comboBoxGroup, comboBoxHomework;

    @FXML private DatePicker datePickerFrom, datePickerTo;
    @FXML private Button btn_filterDates;


    public FXMLFilterController(){
        System.out.println("FXMLFilterController");
    }

    public void setFXMLFilterController(Service s){
        this.service = s;
        try {
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleFilter1() throws Exception {
        Predicate<Grade> p1 = n -> n.getId().getKey().getName().toLowerCase().contains(nameFilterTxt.getText());
        Predicate<Grade> p2 = n -> n.getId().getValue().getRequirement().contains(homeworkFilterTxt.getText());
        List<Grade> filtered = modelGrade.stream().filter(p1.and(p2)).collect(Collectors.toList());
        tableViewFilter.setItems(FXCollections.observableList(filtered));
    }

    public void handleFilter2() throws Exception {
        Predicate<Grade> p1 = n ->n.getId().getKey().getGroup().toString().contains(comboBoxGroup.getSelectionModel().getSelectedItem().toString());
        Predicate<Grade> p2 = n -> n.getId().getValue().getId().toString().contains(comboBoxHomework.getSelectionModel().getSelectedItem().toString());
        List<Grade> filtered = modelGrade.stream().filter(p1.and(p2)).collect(Collectors.toList());
        tableViewFilter.setItems(FXCollections.observableList(filtered));
    }

    public void handleFilter3() throws Exception {
        Predicate<Grade> p1 = n ->n.getDate().isAfter(datePickerFrom.getValue());
        Predicate<Grade> p2 = n -> n.getDate().isBefore(datePickerTo.getValue());
        List<Grade> filtered = modelGrade.stream().filter(p1.and(p2)).collect(Collectors.toList());
        tableViewFilter.setItems(FXCollections.observableList(filtered));
    }


    @FXML
    public void initialize(){
        System.out.println("FXMLFilterController initialized");
        try {
            nameStudentColumn.setCellValueFactory(n-> {
                try {
                    return new SimpleStringProperty(n.getValue().getId().getKey().getName());
                } catch (Exception e) {
                    return new SimpleStringProperty("deleted");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            reqHomeworkColumn.setCellValueFactory(n-> {
                try {
                    return new SimpleStringProperty(n.getValue().getId().getValue().getRequirement());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return new SimpleStringProperty("deleted");
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        tableViewFilter.getStylesheets().add(getClass().getResource("/TableView.css").toExternalForm());
        tableViewFilter.getStyleClass().add("TableView");

        valueGradeColumn.setCellValueFactory(new PropertyValueFactory<Grade,String>("grade"));
        btn_filterDates.getStylesheets().add(
                getClass().getResource("/ButtonMenu.css").toExternalForm());
        btn_filterDates.getStyleClass().add("ButtonMenu");
    }

    private void loadData() {
        try {
            modelGrade = FXCollections.observableList(StreamSupport.stream(service.allGrades().spliterator(), false).collect(Collectors.toList()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        tableViewFilter.setItems(modelGrade);

        List<Integer> listIdHomeworks = service.listIdHomeworks();
        ObservableList<Integer> modelHomework = FXCollections.observableList(listIdHomeworks);
        comboBoxHomework.setItems(modelHomework);
        comboBoxHomework.getSelectionModel().selectFirst();

        List<Integer> listGroups = service.listGroups();
        ObservableList<Integer> modelGroups = FXCollections.observableList(listGroups.stream().sorted(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        }).distinct().collect(Collectors.toList()));
        comboBoxGroup.setItems(modelGroups);
        comboBoxGroup.getSelectionModel().selectFirst();
    }
}
