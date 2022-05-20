package com.example.imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class OrderHistoryCard extends AnchorPane {

    Order order;

    @FXML private Label purchaseDateLabel;
    @FXML private Label purchasePriceLabel;

    public OrderHistoryCard(Order order) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("order-history-card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.order = order;
        this.purchaseDateLabel.setText("Köp " + order.getDate());

        double orderCostSummary = 0;
        for (ShoppingItem shoppingItem : order.getItems()) {
            double productPrice = shoppingItem.getProduct().getPrice();
            orderCostSummary += shoppingItem.getAmount() * productPrice;
        }

        this.purchasePriceLabel.setText("Summa: " + orderCostSummary);
    }
}
