package com.example.imat;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

public class ProductCard {
    Product product;
    IMatDataHandler dataHandler;
    ShoppingCartHandler cartHandler;
    @FXML Label productLabel;
    @FXML Label productPriceLabel;
    @FXML Label productAmountLabel;
    @FXML AnchorPane productAnchorPane;

    public ProductCard(Product product) {
        this.product = product;
        this.dataHandler = IMatDataHandler.getInstance();
        this.productLabel.setText(product.getName());
        this.productPriceLabel.setText(String.valueOf(product.getPrice()));
        this.cartHandler = new ShoppingCartHandler();
        setBgImage();
        updateAmountLabel();
    }

    private Boolean isFavorite(){
        for (Product p : dataHandler.favorites()) {
           if(product == p) {
               return true;
           }
        }
        return false;
    }


    private void changeFavoriteState(){
        if (!isFavorite()){
            dataHandler.addFavorite(product);
        }
        else
        {
            dataHandler.removeFavorite(product);
        }
    }

    private void setBgImage(){
        String image = getClass().getResource("images/" + product.getImageName()).toExternalForm();
        productAnchorPane.setStyle("-fx-background-image: url('" + image + "'); " +
                "-fx-background-position: center;" +
                "-fx-background-repeat: no-repeat;");
    }


    private void updateAmountLabel(){
        productAmountLabel.setText(String.valueOf(cartHandler.getAmountInCart(product)));
    }

    @FXML private void favoriteButtonInteraction(){
        changeFavoriteState();
        //TODO: change the star icon.
    }

    @FXML private void addItemToCartInteraction(){
        cartHandler.addProductToCart(product);
        updateAmountLabel();
    }

    @FXML private void removeItemFromCartInteraction(){
        cartHandler.removeProductFromCart(product);
        updateAmountLabel();
    }


}
