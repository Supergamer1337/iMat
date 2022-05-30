package com.example.imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class OrderHistoryCard extends AnchorPane {

    Order order;
    IMatController parentController;

    @FXML private Label purchaseDateLabel;
    @FXML private Label purchasePriceLabel;

    public OrderHistoryCard(Order order, IMatController parentController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("order-history-card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.order = order;
        this.parentController = parentController;
        Calendar calendarDate = new GregorianCalendar();
        calendarDate.setTime(order.getDate());

        this.purchaseDateLabel.setText("Köp " + calendarDate.get(Calendar.YEAR) + "-" + calendarDate.get(Calendar.MONTH) + "-" + calendarDate.get(Calendar.DAY_OF_MONTH));

        double orderCostSummary = 0;
        for (ShoppingItem shoppingItem : order.getItems()) {
            double productPrice = shoppingItem.getProduct().getPrice();
            orderCostSummary += shoppingItem.getAmount() * productPrice;
        }

        this.purchasePriceLabel.setText("Summa: " + orderCostSummary + "kr");
    }

    @FXML public void pressedCard(){
        parentController.populateHistorypPane(order);
    }
}
