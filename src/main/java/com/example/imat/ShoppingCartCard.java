package com.example.imat;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

public class ShoppingCartCard {
    @FXML Label totalPriceLabel;
    @FXML Label productNameLabel;
    @FXML Label totalAmountLabel;
    @FXML ImageView productImageView;

    Boolean minusButtonIsHoverd = false;
    Boolean plusButtonIsHoverd = false;

    ShoppingCartHandler cartHandler;
    ShoppingItem shoppingItem;



    public ShoppingCartCard(ShoppingItem shoppingItem) {
        cartHandler = new ShoppingCartHandler();
        this.shoppingItem = shoppingItem;
        productNameLabel.setText(shoppingItem.getProduct().getName());
        Image image = new Image(getClass().getResourceAsStream("images/" + shoppingItem.getProduct().getImageName()));
        productImageView.setImage(image);
    }

    private void updateAmountLabels(){
        totalAmountLabel.setText(String.valueOf(shoppingItem.getAmount()));
        totalPriceLabel.setText(String.valueOf(shoppingItem.getTotal()));
    }

}
