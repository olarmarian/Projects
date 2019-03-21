package UI.Gui;

import domain.GradeDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.jfree.ui.ExtensionFileFilter;
import service.Service;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class FXMLRaportController {

    Stage stage;
    private Service service;
    @FXML
    private Button btn_generateRaport1,btn_generateRaport2,btn_generateRaport3,btn_generateRaport4,btn_raport1,btn_raport2,btn_raport3,btn_raport4,btn_showChart1,btn_showChart2,btn_showChart3,btn_showChart4;

    @FXML
    private Pane pnl_raport1,pnl_raport2,pnl_raport3,pnl_raport4;

    public FXMLRaportController(){
        System.out.println("FXMLRaportController");
    }

    @FXML
    private PieChart raport1_piechart,raport2_piechart,raport3_piechart,raport4_piechart;

    public void setFXMLRaportController(Service s,Stage stage) {
        this.service = s;
        this.stage = stage;
    }

    @FXML private void handleButtonAction(ActionEvent event) throws Exception {
        if(event.getSource() == btn_raport1){
            pnl_raport1.toFront();
        }else if(event.getSource() == btn_raport2){
            pnl_raport2.toFront();
        }else if(event.getSource() == btn_raport3){
            pnl_raport3.toFront();
        }else if(event.getSource() == btn_raport4){
            pnl_raport4.toFront();
        } else if(event.getSource() == btn_generateRaport1){
            this.raport1UI();
        }else if(event.getSource() == btn_generateRaport2){
            this.raport2UI();
        }else if(event.getSource() == btn_generateRaport3){
            this.raport3UI();
        }else if(event.getSource() == btn_generateRaport4){
            this.raport4UI();
        }
    }

    @FXML
    public void initialize(){
        System.out.println("Initialized FXMLRaportController");

        btn_generateRaport1.getStylesheets().add(
                getClass().getResource("/ButtonMenu.css").toExternalForm());
        btn_generateRaport1.getStyleClass().add("ButtonMenu");
        btn_generateRaport2.getStylesheets().add(
                getClass().getResource("/ButtonMenu.css").toExternalForm());
        btn_generateRaport2.getStyleClass().add("ButtonMenu");
        btn_generateRaport3.getStylesheets().add(
                getClass().getResource("/ButtonMenu.css").toExternalForm());
        btn_generateRaport3.getStyleClass().add("ButtonMenu");
        btn_generateRaport4.getStylesheets().add(
                getClass().getResource("/ButtonMenu.css").toExternalForm());
        btn_generateRaport4.getStyleClass().add("ButtonMenu");
        btn_showChart1.getStylesheets().add(
                getClass().getResource("/ButtonMenu.css").toExternalForm());
        btn_showChart1.getStyleClass().add("ButtonMenu");
        btn_showChart2.getStylesheets().add(
                getClass().getResource("/ButtonMenu.css").toExternalForm());
        btn_showChart2.getStyleClass().add("ButtonMenu");
        btn_showChart3.getStylesheets().add(
                getClass().getResource("/ButtonMenu.css").toExternalForm());
        btn_showChart3.getStyleClass().add("ButtonMenu");
        btn_showChart4.getStylesheets().add(
                getClass().getResource("/ButtonMenu.css").toExternalForm());
        btn_showChart4.getStyleClass().add("ButtonMenu");


        raport1_piechart.getStylesheets().add(
                getClass().getResource("/PieChart.css").toExternalForm());
        raport1_piechart.getStyleClass().add("PieChart");
        raport2_piechart.getStylesheets().add(
                getClass().getResource("/PieChart.css").toExternalForm());
        raport2_piechart.getStyleClass().add("PieChart");
        raport3_piechart.getStylesheets().add(
                getClass().getResource("/PieChart.css").toExternalForm());
        raport3_piechart.getStyleClass().add("PieChart");
        raport4_piechart.getStylesheets().add(
                getClass().getResource("/PieChart.css").toExternalForm());
        raport4_piechart.getStyleClass().add("PieChart");

    }

    @FXML
    private void raport1Piechart() throws Exception {
        List<Pair<String, Double>> list = this.service.AverageForStudents();
        ObservableList<PieChart.Data> pieData = FXCollections.observableList(list.stream().map(x->{
            return new PieChart.Data(x.getKey(),x.getValue());
        }).collect(Collectors.toList()));
        raport1_piechart.setData(pieData);
    }

    @FXML
    private void raport2Piechart() throws Exception {
        List<Pair<String, Float>> list = this.service.raportList2(service.gradeDTOList());
        ObservableList<PieChart.Data> pieData = FXCollections.observableList(list.stream().map(x->{
            return new PieChart.Data(x.getKey(),x.getValue());
        }).collect(Collectors.toList()));
        raport2_piechart.setData(pieData);
    }

    @FXML
    private void raport3Piechart() throws Exception {
        List<Pair<String, Double>> list = this.service.AverageForStudents();
        List<Integer> l = new ArrayList<>();
        l.add(0);
        l.add(0);
        list.stream().map(x->{
            if(x.getValue()>4){
                l.add(0,1);
            }else{
                l.add(1,1);
            }
            return null;
        }).collect(Collectors.toList());

        List<Pair<String,Integer>> raport = new ArrayList<>();
        raport.add(new Pair<>("Accepted",l.get(0)));
        raport.add(new Pair<>("Rejected",l.get(1)));
        ObservableList<PieChart.Data> pieData = FXCollections.observableList(raport.stream().map(x->{
            return new PieChart.Data(x.getKey(),x.getValue());
        }).collect(Collectors.toList()));
        raport3_piechart.setData(pieData);
    }

    @FXML
    private void raport4Piechart() throws Exception {
        List<String> raport = service.raport4List();
        ObservableList<PieChart.Data> pieData = FXCollections.observableList(raport.stream().map(x->{
            return new PieChart.Data(x,1);
        }).collect(Collectors.toList()));
        raport4_piechart.setData(pieData);
    }


    private void raport1UI() throws Exception {
        JFileChooser fileChooser = new JFileChooser(new File("C:\\Users\\Marian\\IdeaProjects\\Lab3\\Rapoarte"));
        fileChooser.setDialogTitle("Save a file");
        fileChooser.setFileFilter(
                new ExtensionFileFilter("Text files","*.txt")
        );
        fileChooser.setFileFilter(
                new ExtensionFileFilter("Pdf files","*.pdf")
        );

        int result = fileChooser.showSaveDialog(null);
        if(result == JFileChooser.APPROVE_OPTION){
            File fi = fileChooser.getSelectedFile();
            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                try {
                    service.Raport1(fi.getPath());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,e.getMessage());
                }
            });
        }
    }
    private void raport2UI() throws Exception {
        JFileChooser fileChooser = new JFileChooser(new File("C:\\Users\\Marian\\IdeaProjects\\Lab3\\Rapoarte"));
        fileChooser.setDialogTitle("Save a file");
        fileChooser.setFileFilter(
                new ExtensionFileFilter("Text files","*.txt")
        );
        fileChooser.setFileFilter(
                new ExtensionFileFilter("Pdf files","*.pdf")
        );

        int result = fileChooser.showSaveDialog(null);
        if(result == JFileChooser.APPROVE_OPTION){
            File fi = fileChooser.getSelectedFile();
            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                try {
                    service.Raport2(fi.getPath());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,e.getMessage());
                }
            });
        }
    }
    private void raport3UI() throws Exception {
        JFileChooser fileChooser = new JFileChooser(new File("C:\\Users\\Marian\\IdeaProjects\\Lab3\\Rapoarte"));
        fileChooser.setDialogTitle("Save a file");
        fileChooser.setFileFilter(
                new ExtensionFileFilter("Text files","*.txt")
        );
        fileChooser.setFileFilter(
                new ExtensionFileFilter("Pdf files","*.pdf")
        );

        int result = fileChooser.showSaveDialog(null);
        if(result == JFileChooser.APPROVE_OPTION){
            File fi = fileChooser.getSelectedFile();
            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                try {
                    service.Raport3(fi.getPath());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,e.getMessage());
                }
            });
        }
    }
    private void raport4UI() throws Exception {
        JFileChooser fileChooser = new JFileChooser(new File("C:\\Users\\Marian\\IdeaProjects\\Lab3\\Rapoarte"));
        fileChooser.setDialogTitle("Save a file");
        fileChooser.setFileFilter(
                new ExtensionFileFilter("Text files","*.txt")
        );
        fileChooser.setFileFilter(
                new ExtensionFileFilter("Pdf files","*.pdf")
        );

        int result = fileChooser.showSaveDialog(null);
        if(result == JFileChooser.APPROVE_OPTION){
            File fi = fileChooser.getSelectedFile();
            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                try {
                    service.Raport4(fi.getPath());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,e.getMessage());
                }
            });
        }
    }
}
