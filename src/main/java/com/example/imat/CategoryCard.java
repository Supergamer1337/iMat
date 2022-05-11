package com.example.imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CategoryCard extends AnchorPane {

    @FXML private AnchorPane cardAnchorPane;
    @FXML private Label categoryLabel;

    public CategoryCard(String categoryName) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("category-card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        categoryLabel.setText(categoryName);
    }
}
