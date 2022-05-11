package com.example.imat;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class IMatController {

    IMatDataHandler dataHandler = IMatDataHandler.getInstance();

    @FXML
    public void initialize() {
        System.out.println("Current home path: " + System.getProperty("user.home"));
    }
}