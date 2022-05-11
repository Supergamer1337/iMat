package com.example.imat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class IMat extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(IMat.class.getResource("imat-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1440, 1024);
        stage.setScene(scene);
        stage.setTitle("iMat");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}