package com.example.imat;

import com.example.imat.utils.StyleUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class ProductCard extends AnchorPane {
    Product product;
    IMatDataHandler dataHandler;
    ShoppingCartHandler cartHandler;

    Boolean minusButtonIsHoverd = false;
    Boolean plusButtonIsHoverd = false;
    Boolean favoriteButtonIsHoverd = false;


    @FXML Label productLabel;
    @FXML Label productPriceLabel;
    @FXML Label productAmountLabel;
    @FXML AnchorPane productImageAnchorPane;
    @FXML ImageView favoriteIcon;

    public ProductCard(Product product) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("product-card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.product = product;
        this.dataHandler = IMatDataHandler.getInstance();
        this.productLabel.setText(product.getName());
        this.productPriceLabel.setText(String.valueOf(product.getPrice()) + "/st");
        this.cartHandler = new ShoppingCartHandler();
        StyleUtils.roundBackgroundImage(productImageAnchorPane, 240, 215, 20);
        StyleUtils.coverBackgroundImage(productImageAnchorPane, getClass().getResource("images/" + product.getImageName()).toExternalForm());

        updateAmountLabel();
        updateFavoriteIcon();
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

    private void updateFavoriteIcon() {
        if (isFavorite()) {
            favoriteIcon.setImage(new Image(getClass().getResourceAsStream("icons/star-favorite.png")));
        } else {
            favoriteIcon.setImage(new Image(getClass().getResourceAsStream("icons/star.png")));
        }
    }

    private void setBgImage(){
        String image = getClass().getResource("images/" + product.getImageName()).toExternalForm();
        productAnchorPane.setStyle("-fx-background-image: url('" + image + "'); " +
                "-fx-background-repeat: no-repeat;");
    }
  
    private void updateAmountLabel(){
        productAmountLabel.setText(String.valueOf(cartHandler.getAmountInCart(product)) + "/st");
    }

    @FXML
    public void favoriteButtonInteraction(){
        changeFavoriteState();
        updateFavoriteIcon();
    }

    @FXML
    public void addItemToCartInteraction(){
        cartHandler.addProductToCart(product);
        updateAmountLabel();
    }

    @FXML
    public void removeItemFromCartInteraction(){
        cartHandler.removeProductFromCart(product);
        updateAmountLabel();
    }

    @FXML
    public void togglePlusButtonHover(){
        plusButtonIsHoverd = !plusButtonIsHoverd;
    }

    @FXML
    public void toggleMinusButtonHover(){
        minusButtonIsHoverd = !minusButtonIsHoverd;
    }

    @FXML
    public void toggleFavoriteButtonHover(){
        favoriteButtonIsHoverd = !minusButtonIsHoverd;
    }




}
