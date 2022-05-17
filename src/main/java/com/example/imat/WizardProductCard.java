package com.example.imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class WizardProductCard extends AnchorPane {
    IMatDataHandler dataHandler;

    @FXML private Label productNameLabel;
    @FXML private Label productPriceLabel;
    @FXML private Label productAmountLabel;
    @FXML private Label productTotalPriceLabel;


    public WizardProductCard(ShoppingItem shoppingItem) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("wizard-product-card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.dataHandler = IMatDataHandler.getInstance();

        productNameLabel.setText(shoppingItem.getProduct().getName());
        productPriceLabel.setText(shoppingItem.getProduct().getPrice() + "/st");
        productAmountLabel.setText((int) shoppingItem.getAmount() + "st");
        productTotalPriceLabel.setText("Summa: " + shoppingItem.getTotal() + "kr");
    }
}
