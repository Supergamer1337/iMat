package com.example.imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.List;

public class HistoryCard extends AnchorPane {
    private ShoppingCart shoppingCart;
    private IMatDataHandler dataHandler;

    @FXML private Label historyDateLabel;
    @FXML private Label historySumLabel;
    @FXML private AnchorPane historyImagePane;
    @FXML private AnchorPane historyButton;

    public void HistoryCard(ShoppingCart shoppingCarts){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("history-card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dataHandler = IMatDataHandler.getInstance();
        this.shoppingCart = shoppingCart;
        historySumLabel.setText(shoppingCart.getTotal() + " kr");
        setHistoryImage();
        //TODO: set historySumLabel depending on how we implement and history.
        //TODO: Show a preview of what the hisory cart includes if you press it.
    }

    private void setHistoryImage(){
        String image = getClass().getResource("images/" + shoppingCart.getItems().get(0).getProduct().getImageName()).toExternalForm();
        historyImagePane.setStyle("-fx-background-image: url('" + image + "'); " +
                "-fx-background-position: default;" +
                "-fx-background-repeat: no-repeat;");
    }

    @FXML
    public void addHistoryToCart(){
        for (ShoppingItem shoppingItem : shoppingCart.getItems()) {
            dataHandler.getShoppingCart().addItem(shoppingItem);
        }
    }

}
