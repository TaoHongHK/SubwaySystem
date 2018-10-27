package com.ui.view;

import com.subwaysystem.SubwayException;
import com.subwaysystem.SubwaySystem;
import com.ui.SSmain;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class LinesWindowController{

    private SSmain sSmain;

    private SubwaySystem subwaySystem;

    @FXML
    private TextField stationNameField;
    @FXML
    private TextArea resultArea;
    @FXML
    private ChoiceBox<String> choiceBox2;
    @FXML
    private ChoiceBox<String> choiceBox1;


    @FXML
    public void initialize(){
        choiceBox1.getItems().addAll("一号线","二号线","三号线","四号线","六号线","七号线","八号线","阳逻线","十一号线");
        choiceBox1.setValue("一号线");
        try {
            if (choiceBox1.getValue()!=null&&subwaySystem!=null) {
                choiceBox2.getItems().addAll(subwaySystem.getFinalStations(choiceBox1.getValue()));
            }
        }catch (SubwayException e){
            System.out.println("button2");
        }
    }

    @FXML
    public void handleBackButton(){
        sSmain.showSystemOverview();
    }

    @FXML
    public void handleSearchButton_1(){
        String stationName = stationNameField.getText();
        try {
            if (stationName!=null){
                resultArea.setText(subwaySystem.searchForLine(stationName));
            }
        }catch (SubwayException e){
            System.out.println("button1");
        }
    }
    @FXML
    public void handleSearchButton_2(){
        try {
            resultArea.setText(subwaySystem.searchForStations(choiceBox1.getValue(),
                    choiceBox2.getValue()));
        }catch (SubwayException ee){
            System.out.println("sdfadsf");
        }
    }

    public void setSubwaySystem(SubwaySystem subwaySystem){
        this.subwaySystem = subwaySystem;
    }

    public void setsSmain(SSmain sSmain){
        this.sSmain = sSmain;
    }
}
