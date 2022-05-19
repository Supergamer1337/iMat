package com.example.imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;

public class SearchCard extends AnchorPane {
    @FXML private Label searchLabel;
    @FXML private ImageView searchImage;
    private IMatController parentController;
    private Product product;

    public SearchCard(Product product, IMatController parentController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("search-card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.product = product;
        this.parentController = parentController;
        Image image = new Image(getClass().getResourceAsStream("images/" + product.getImageName()));
        searchImage.setImage(image);
        searchLabel.setText(product.getName());
    }

    @FXML public void gu(){
        parentController.productItemPressed(product);
    }
}
