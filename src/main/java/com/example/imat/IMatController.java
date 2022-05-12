package com.example.imat;

import com.example.imat.utils.StyleUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class IMatController {

    IMatDataHandler dataHandler = IMatDataHandler.getInstance();

    @FXML private ImageView shoppingCartImage;
    private boolean shoppingCartIsHovered = false;

    @FXML private ImageView profileImage;
    private boolean profileIsHovered = false;

    @FXML private FlowPane categoryFlowPane;
    @FXML private ScrollPane productsPage;
    @FXML private ScrollPane categoriesPage;
    @FXML private FlowPane productsFlowPane;
    @FXML private SplitPane shoppingCartSplitPane;
    @FXML private FlowPane shoppingCartFlowPane;

    @FXML
    public void initialize() {
        System.out.println("Current home path: " + System.getProperty("user.home"));

        ProductCategory[] categories = ProductCategory.values();
        for (ProductCategory category : categories) {
            for (Product product : dataHandler.getProducts()) {
                if (product.getCategory() == category){
                    categoryFlowPane.getChildren().add(new CategoryCard(category, product.getImageName(), this));
                    break;
                }
            }
        }
    }

    @FXML public void goToCategories() {
        categoriesPage.toFront();
    }

    @FXML public void goToShoppingCart(){
        shoppingCartFlowPane.getChildren().clear();
        shoppingCartSplitPane.toFront();
        List<ShoppingItem> shoppingItems = dataHandler.getShoppingCart().getItems();

        for (ShoppingItem shoppingItem: shoppingItems) {
            shoppingCartFlowPane.getChildren().add(new ShoppingCartCard(shoppingItem));
        }
    }

    @FXML public void showCategory(ProductCategory category) {
        productsFlowPane.getChildren().clear();
        productsPage.toFront();

        List<Product> products = dataHandler.getProducts();
        for (Product product : products) {
            if (product.getCategory() == category) {
               productsFlowPane.getChildren().add(new ProductCard(product));
            }
        }
    }

    @FXML
    public void toggleShoppingCartHover() {
        shoppingCartIsHovered = !shoppingCartIsHovered;
        StyleUtils.toggleHoverImage(shoppingCartIsHovered, "icons/shopping-cart-hover.png", "icons/shopping-cart.png", shoppingCartImage);
    }

    @FXML
    public void toggleProfileHover() {
        profileIsHovered = !profileIsHovered;
        StyleUtils.toggleHoverImage(profileIsHovered, "icons/profile-hover.png", "icons/profile.png", profileImage);
    }
}