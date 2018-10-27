package com.ui;

import com.subwaysystem.*;
import java.io.IOException;

import com.ui.view.LinesWindowController;
import com.ui.view.PathWindowController;
import com.ui.view.SystemOverviewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SSmain extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private SubwaySystem subwaySystem;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("SubwaySystemApp");
        initRootLayout();
        showSystemOverview();
        this.subwaySystem = new SubwaySystem();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SSmain.class.getResource("view\\RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSystemOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SSmain.class.getResource("view/SystemOverview.fxml"));
            AnchorPane SystemOverview = (AnchorPane) loader.load();
            SystemOverviewController systemOverviewController = loader.getController();
            systemOverviewController.setssmain(this);
            rootLayout.setCenter(SystemOverview);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showLinesWindow(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SSmain.class.getResource("view/linesWindow.fxml"));
            AnchorPane linesWindow = (AnchorPane) loader.load();
            LinesWindowController linesWindowController = loader.getController();
            linesWindowController.setSubwaySystem(this.subwaySystem);
            linesWindowController.setsSmain(this);
            rootLayout.setCenter(linesWindow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showPathWindow(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SSmain.class.getResource("view/pathWindow.fxml"));
            AnchorPane pathWindow = (AnchorPane) loader.load();
            PathWindowController pathWindowController = loader.getController();
            pathWindowController.setsSmain(this);
            rootLayout.setCenter(pathWindow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
