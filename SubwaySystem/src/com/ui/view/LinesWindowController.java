package com.ui.view;

import com.subwaysystem.SubwayException;
import com.subwaysystem.SubwaySystem;
import com.ui.SSmain;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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


    public void myinitialize(){
        choiceBox1.setItems(FXCollections.observableArrayList("一号线","二号线","三号线","四号线","六号线","七号线","八号线","阳逻线","十一号线"));
        if (subwaySystem!=null) {
            choiceBox1.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    try {
                        String[] names = subwaySystem.getFinalStations(choiceBox1.getItems().get(newValue.intValue()));
                        choiceBox2.getItems().clear();
                        for (String name : names){
                            choiceBox2.getItems().add(name);
                        }
                        choiceBox2.setValue(names[0]);
                    }catch (SubwayException e){

                    }
                }
            });
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


    public void setsSmainAndSS(SSmain sSmain){
        this.sSmain = sSmain;
        this.subwaySystem = sSmain.getSubwaySystem();
    }
}
