package com.example.imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class ShoppingCartCard extends AnchorPane {
    @FXML Label totalPriceLabel;
    @FXML Label productNameLabel;
    @FXML Label totalAmountLabel;
    @FXML ImageView productImageView;

    Boolean minusButtonIsHoverd = false;
    Boolean plusButtonIsHoverd = false;

    ShoppingCartHandler cartHandler;
    ShoppingItem shoppingItem;
    IMatController parentController;
    IMatDataHandler dataHandler;



    public ShoppingCartCard(ShoppingItem shoppingItem, IMatController parentController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("shopping-cart-card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.parentController = parentController;
        this.dataHandler = IMatDataHandler.getInstance();
        cartHandler = new ShoppingCartHandler();
        this.shoppingItem = shoppingItem;
        productNameLabel.setText(shoppingItem.getProduct().getName());
        Image image = new Image(getClass().getResourceAsStream("images/" + shoppingItem.getProduct().getImageName()));
        productImageView.setImage(image);
        updateAmountLabels();

    }

    private void updateAmountLabels(){
        totalAmountLabel.setText(String.valueOf((int) shoppingItem.getAmount()) + " st");
        totalPriceLabel.setText(String.valueOf(shoppingItem.getTotal()) + " kr");
        parentController.updateShoppingCartInformation();
    }

    @FXML void showDetails() {
        parentController.cartItemPressed(shoppingItem);
    }


    @FXML
    public void addItemToCartInteraction(){
        cartHandler.addProductToCart(shoppingItem.getProduct());
        updateAmountLabels();
    }

    @FXML
    public void removeItemFromCartInteraction(){
        cartHandler.removeProductFromCart(shoppingItem.getProduct(), true);
        updateAmountLabels();
    }

    @FXML
    public void togglePlusButtonHover(){
        plusButtonIsHoverd = !plusButtonIsHoverd;
    }

    @FXML
    public void toggleMinusButtonHover(){
        minusButtonIsHoverd = !minusButtonIsHoverd;
    }


}
