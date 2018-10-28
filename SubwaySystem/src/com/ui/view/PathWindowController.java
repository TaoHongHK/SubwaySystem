package com.ui.view;

import com.subwaysystem.SubwayException;
import com.subwaysystem.SubwaySystem;
import com.ui.SSmain;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import java.util.List;

public class PathWindowController {
    private SSmain sSmain;
    private SubwaySystem subwaySystem;
    private String TYPE;

    @FXML
    private ChoiceBox<String> choiceBox_1;
    @FXML
    private ChoiceBox<String> choiceBox_2;
    @FXML
    private ChoiceBox<String> choiceBox_3;
    @FXML
    private ChoiceBox<String> choiceBox_4;
    @FXML
    private TextArea textArea;
    @FXML
    private RadioButton radioButton_1;
    @FXML
    private RadioButton radioButton_2;
    @FXML
    private RadioButton radioButton_3;

    public void myinitialize() {
        choiceBox_1.setItems(FXCollections.observableArrayList("一号线", "二号线", "三号线", "四号线", "六号线", "七号线", "八号线", "阳逻线", "十一号线"));
        choiceBox_3.setItems(FXCollections.observableArrayList("一号线", "二号线", "三号线", "四号线", "六号线", "七号线", "八号线", "阳逻线", "十一号线"));
        TYPE = "普通";
        if (subwaySystem != null) {
            choiceBox_1.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    try {
                        String[] names = subwaySystem.getOneLineAllStations(choiceBox_1.getItems().get(newValue.intValue()));
                        choiceBox_2.getItems().clear();
                        for (String name : names) {
                            choiceBox_2.getItems().add(name);
                        }
                        choiceBox_2.setValue(names[0]);
                    } catch (SubwayException e) {

                    }
                }
            });
            choiceBox_3.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    try {
                        String[] names = subwaySystem.getOneLineAllStations(choiceBox_3.getItems().get(newValue.intValue()));
                        choiceBox_4.getItems().clear();
                        for (String name : names) {
                            choiceBox_4.getItems().add(name);
                        }
                        choiceBox_4.setValue(names[0]);
                    } catch (SubwayException e) {
                        System.out.println("initial failed");
                    }
                }
            });
        }
        ToggleGroup toggleGroup = new ToggleGroup();
        radioButton_1.setToggleGroup(toggleGroup);
        radioButton_2.setToggleGroup(toggleGroup);
        radioButton_3.setToggleGroup(toggleGroup);
        radioButton_1.setOnAction(event -> {
            if (radioButton_1.isSelected()){
                TYPE = "普通";
            }
        });
        radioButton_2.setOnAction(event -> {
            if (radioButton_2.isSelected()){
                TYPE = "武汉通";
            }
        });
        radioButton_3.setOnAction(event -> {
            if (radioButton_3.isSelected()){
                TYPE = "日票";
            }
        });
    }

    @FXML
    public void handleBackButton(){
        sSmain.showSystemOverview();
    }

    @FXML
    public void handleBuyButton(){
        try {
            List list = subwaySystem.getShortestPath(choiceBox_2.getValue(),choiceBox_4.getValue());
            textArea.setText(subwaySystem.printPath(list));
            textArea.appendText("\n"+"您需要花费："+subwaySystem.specialCost(list,TYPE)+"元");
        }catch (SubwayException e){
            e.printStackTrace();
        }
    }

    public void setsSmainAndSS(SSmain sSmain){
        this.sSmain = sSmain;
        this.subwaySystem = sSmain.getSubwaySystem();
    }

}
