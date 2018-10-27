package com.ui.view;

import com.ui.SSmain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class SystemOverviewController {


    private SSmain ssmain;

    public void handleLinesSearch(ActionEvent actionEvent) {
        ssmain.showLinesWindow();
    }

    public void handlePathSearch(ActionEvent actionEvent) {
        ssmain.showPathWindow();
    }


    public void setssmain(SSmain ssmain){
        this.ssmain = ssmain;
    }

}
