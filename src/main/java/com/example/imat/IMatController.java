package com.example.imat;

import com.example.imat.utils.StyleUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.*;

import java.net.URL;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class IMatController implements ShoppingCartListener {

    private IMatDataHandler dataHandler = IMatDataHandler.getInstance();
    private ArrayList<String> previousLocations = new ArrayList<String>();
    private String currentLocation;

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
    @FXML private FlowPane favoriteFlowPane;
    @FXML private ScrollPane favoriteScrollPane;
    @FXML private Label shoppingCartCounterLabel;

    @FXML private AnchorPane profileAnchorPane;
    @FXML private ScrollPane profileHistoryPane;
    @FXML private AnchorPane profileInformationPane;

    @FXML private Label cartProductNameLabel;
    @FXML private Label cartProductPriceLabel, cartTotalProductPriceLabel,
            cartTotalAmountItemsLabel, cartTotalPriceLabel;
    @FXML private TextArea cartDescriptionTextArea;
    @FXML private ImageView cartImage;
    @FXML private Label backToLabel;
    @FXML private Label currentLocationLabel;

    @FXML
    public void initialize() {
        System.out.println("Current home path: " + System.getProperty("user.home"));
        dataHandler.getShoppingCart().addShoppingCartListener(this);
        addToLocationHistory("Kategorier", true);

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
        goToCategories(true);
    }

    public void goToCategories(boolean addToHistory){
        addToLocationHistory("Kategorier", addToHistory);

        categoriesPage.toFront();
    }

    @FXML public void goToShoppingCart() {
        goToShoppingCart(true);
    }

    public void goToShoppingCart(boolean addToHistory){
        addToLocationHistory("Kundvagn", addToHistory);

        shoppingCartFlowPane.getChildren().clear();
        shoppingCartSplitPane.toFront();
        updateShoppingCartInformation();
        List<ShoppingItem> shoppingItems = dataHandler.getShoppingCart().getItems();

        for (ShoppingItem shoppingItem: shoppingItems) {
            shoppingCartFlowPane.getChildren().add(new ShoppingCartCard(shoppingItem, this));
        }
    }

    @FXML public void showCategory(ProductCategory category, boolean addToHistory) {
        addToLocationHistory(category.name(), addToHistory);

        productsFlowPane.getChildren().clear();
        productsPage.toFront();

        List<Product> products = dataHandler.getProducts();
        for (Product product : products) {
            if (product.getCategory() == category) {
               productsFlowPane.getChildren().add(new ProductCard(product));
            }
        }
    }

    @FXML public void showFavorites() {
        showFavorites(true);
    }

    public void showFavorites(boolean addToHistory) {
        addToLocationHistory("Favoriter", addToHistory);

        favoriteFlowPane.getChildren().clear();
        favoriteScrollPane.toFront();
        List<Product> products = dataHandler.favorites();
        for (Product product : products) {
            favoriteFlowPane.getChildren().add(new ProductCard(product));
        }
    }

    @FXML public void goToProfile() {
        goToProfile(true);
    }

    public void goToProfile(boolean addToHistory){
        addToLocationHistory("Profil", addToHistory);

        profileAnchorPane.toFront();
    }

    @FXML public void showProfileHistory(){
        profileHistoryPane.toFront();
    }

    @FXML public void showProfileInformation(){
        profileInformationPane.toFront();
    }

    @FXML
    public void toggleShoppingCartHover() {
        shoppingCartIsHovered = !shoppingCartIsHovered;
        StyleUtils.toggleHoverImage(shoppingCartIsHovered, "icons/shopping-cart-hover.png", "icons/shopping-cart.png", shoppingCartImage);
    }

    @FXML
    public void cartItemPressed(ShoppingItem shoppingItem){
        cartProductNameLabel.setText(shoppingItem.getProduct().getName());
        Image image = new Image(getClass().getResourceAsStream("images/" + shoppingItem.getProduct().getImageName()));
        cartImage.setImage(image);
        cartProductPriceLabel.setText("(" + shoppingItem.getAmount() + " st) " + shoppingItem.getTotal() + "kr");
        cartTotalProductPriceLabel.setText(shoppingItem.getProduct().getPrice() + " kr");
    }

    @FXML
    public void toggleProfileHover() {
        profileIsHovered = !profileIsHovered;
        StyleUtils.toggleHoverImage(profileIsHovered, "icons/profile-hover.png", "icons/profile.png", profileImage);
    }

    private void updateShoppingCartInformation(){
        List<ShoppingItem> shoppingItems = dataHandler.getShoppingCart().getItems();
        int ctr = 0;
        for (ShoppingItem shoppingitem:
             shoppingItems) {
            ctr += shoppingitem.getAmount();
        }
        cartTotalAmountItemsLabel.setText(ctr + " st");
        cartTotalPriceLabel.setText(dataHandler.getShoppingCart().getTotal() + " kr");
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        List<ShoppingItem> shopItem = dataHandler.getShoppingCart().getItems();
        shoppingCartCounterLabel.setText(String.valueOf(shopItem.size()));
    }

    private void addToLocationHistory(String currentLocation, boolean addToHistory) {
        if (addToHistory) {
            addCurrentToLocationHistory();
        }
        updateLocationLabels(currentLocation);
    }

    private void addCurrentToLocationHistory() {
        this.previousLocations.add(this.currentLocation);
    }

    private void updateLocationLabels(String currentLocation) {
        this.currentLocation = currentLocation;
        currentLocationLabel.setText(currentLocation);
        if (previousLocations.size() <= 1) {
            backToLabel.setText("");
            return;
        }
        backToLabel.setText("Gå tillbaka till " + previousLocations.get(previousLocations.size() - 1));
    }

    @FXML
    public void goBackToPrevious() {
        if (previousLocations.size() > 1) {
            String previousLocation = previousLocations.get(previousLocations.size() - 1);
            previousLocations.remove(previousLocations.size() - 1);

            switch (previousLocation) {
                case "Profil":
                    goToProfile(false);
                    break;
                case "Favoriter":
                    showFavorites(false);
                    break;
                case "Kundvagn":
                    goToShoppingCart(false);
                    break;
                case "Kategorier":
                    goToCategories(false);
                    break;
                default:
                    goToCategories(false);
            }
        }
    }
}