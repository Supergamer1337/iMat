package com.example.imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.io.IOException;

public class CategoryCard extends AnchorPane {

    @FXML private AnchorPane cardAnchorPane;
    @FXML private Label categoryLabel;

    private ProductCategory category;

    public CategoryCard(ProductCategory category) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("category-card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.category = category;

        categoryLabel.setText(setCategoryName(category));
    }

    public String setCategoryName(ProductCategory category ) {
        switch (category.toString()) {
            case "BERRY":
                return "Bär";
            case "Bread":
                return "Bröd";
            case "CABBAGE":
                return "Isbergssalat";
            case "CITRUS_FRUIT":
                return "Citrusfrukt";
            case "COLD_DRINKS":
                return "Kalla drycker";
            case "DAIRIES":
                return "Mejeriprodukter";
            case "EXOTIC_FRUIT":
                return "Exotisk frukt";
            case "FISH":
                return "Fisk";
            case "FLOUR_SUGAR_SALT":
                return "Mjöl, socker och salt";
            case "FRUIT":
                return "Stenfrukter";
            case "HERB":
                return "Örter";
            case "MEAT":
                return "Kött";
            case "HOT_DRINKS":
                return "Varma drycker";
            case "MELONS":
                return "Meloner";
            case "NUTS_AND_SEEDS":
                return "Nötter och frön";
            case "PASTA":
                return "Pasta";
            case "POD":
                return "Baljväxter";
            case "POTATO_RICE":
                return "Potatis och ris";
            case "ROOT_VEGETABLE":
                return "Rotfrukter";
            case "SWEET":
                return "Sött";
            case "VEGETABLE_FRUIT":
                return "Grönsaksfrukter";
            default:
                return "Okänd kategori";
        }
    }
}
