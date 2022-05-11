package com.example.imat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;
import java.util.ResourceBundle;

public class IMat extends Application {
    private IMatDataHandler handler;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(IMat.class.getResource("imat-view.fxml"));
        handler = IMatDataHandler.getInstance();

        Scene scene = new Scene(fxmlLoader.load(), 1440, 1024);
        stage.setScene(scene);
        stage.setTitle("iMat");
        stage.show();
    }

    public IMatDataHandler getDataHandler(){
        return handler;
    }

    public static void main(String[] args) {
        launch();
    }
}