package com.ui.view;

import com.ui.SSmain;
import javafx.fxml.FXML;

public class PathWindowController {
    private SSmain sSmain;

    @FXML
    public void handleBackButton(){
        sSmain.showSystemOverview();
    }

    public void setsSmain(SSmain sSmain){
        this.sSmain = sSmain;
    }
}
