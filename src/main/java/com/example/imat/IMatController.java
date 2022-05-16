package com.example.imat;

import com.example.imat.models.LocationInfo;
import com.example.imat.utils.StyleUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.*;

import java.io.InputStream;
import java.net.URL;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class IMatController implements ShoppingCartListener {

    private IMatDataHandler dataHandler = IMatDataHandler.getInstance();
    private ArrayList<LocationInfo> previousLocations = new ArrayList<LocationInfo>();
    private LocationInfo currentLocation;

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
    @FXML private ImageView backArrowImage;

    @FXML private Button categoriesButton;
    @FXML private Button offersButton;
    @FXML private Button favoritesButton;
    @FXML private Button helpButton;

    @FXML
    public void initialize() {
        System.out.println("Current home path: " + System.getProperty("user.home"));
        dataHandler.getShoppingCart().addShoppingCartListener(this);
        addToLocationHistory(new LocationInfo("Kategorier", "Kategorier", "Kategorier"), true);

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
        clearLocationHistory();
        addToLocationHistory(new LocationInfo("Kategorier", "Kategorier", "Kategorier"), addToHistory);

        categoriesPage.toFront();
    }

    @FXML public void goToShoppingCart() {
        goToShoppingCart(true);
    }

    public void goToShoppingCart(boolean addToHistory){
        addToLocationHistory(new LocationInfo("Kundvagn", "Kundvagn", "Kundvagn"), addToHistory);

        shoppingCartFlowPane.getChildren().clear();
        shoppingCartSplitPane.toFront();
        updateShoppingCartInformation();
        List<ShoppingItem> shoppingItems = dataHandler.getShoppingCart().getItems();

        for (ShoppingItem shoppingItem: shoppingItems) {
            shoppingCartFlowPane.getChildren().add(new ShoppingCartCard(shoppingItem, this));
        }
    }

    @FXML public void showCategory(ProductCategory category, boolean addToHistory) {
        addToLocationHistory(new LocationInfo(category.name(), CategoryCard.getPrettyCategoryName(category), CategoryCard.getPrettyCategoryName(category)), addToHistory);

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
        clearLocationHistory();
        addToLocationHistory(new LocationInfo("Favoriter", "Favoriter", "Favoriter"), addToHistory);

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
        clearLocationHistory();
        addToLocationHistory(new LocationInfo("Profil", "Profil", "Profil"), addToHistory);

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
        if (!currentLocation.getLocation().equals("Kundvagn")) { // Too lazy to make it hoverable when active.
            StyleUtils.toggleHoverImage(shoppingCartIsHovered, "icons/shopping-cart-hover.png", "icons/shopping-cart.png", shoppingCartImage);
        }
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
        if (!currentLocation.getLocation().equals("Profil")) { // Too lazy to make it hoverable when active.
            StyleUtils.toggleHoverImage(profileIsHovered, "icons/profile-hover.png", "icons/profile.png", profileImage);
        }
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

    private void addToLocationHistory(LocationInfo currentLocation, boolean addToHistory) {
        removeLocationHighlight();
        if (addToHistory) {
            addCurrentToLocationHistory();
        }
        updateLocationLabels(currentLocation);
        activateLocationHighlight();
    }

    private void addCurrentToLocationHistory() {
        this.previousLocations.add(this.currentLocation);
    }

    private void clearLocationHistory() {
        this.previousLocations.clear();
    }

    private void updateLocationLabels(LocationInfo currentLocation) {
        this.currentLocation = currentLocation;
        currentLocationLabel.setText(currentLocation.getBreadcrumb());
        if (previousLocations.size() <= 1) {
            backToLabel.setText("");
            backArrowImage.setStyle("-fx-opacity: 0;" + "-fx-cursor: default;");
            return;
        }
        backArrowImage.setStyle("-fx-opacity: 1;" + "-fx-cursor: hand;");
        backToLabel.setText("Gå tillbaka till " + previousLocations.get(previousLocations.size() - 1).getPrettyGoBack());
    }

    private void removeLocationHighlight() {
        if (currentLocation == null) return; // Does not work on initial load, as categories is not yet set as current location.

        switch (currentLocation.getLocation()) {
            case "Kategorier":
                categoriesButton.getStyleClass().remove("main-nav-button-active");
                break;
            case "Erbjudanden":
                offersButton.getStyleClass().remove("main-nav-button-active");
                break;
            case "Favoriter":
                favoritesButton.getStyleClass().remove("main-nav-button-active");
                break;
            case "Hjälp":
                helpButton.getStyleClass().remove("main-nav-button-active");
                break;
            case "Kundvagn":
                shoppingCartImage.setImage(new Image(getClass().getResourceAsStream("icons/shopping-cart.png")));
                break;
            case "Profil":
                profileImage.setImage(new Image(getClass().getResourceAsStream("icons/profile.png")));
                break;
            default:
                categoriesButton.getStyleClass().remove("main-nav-button-active");
                break;
        }
    }

    private void activateLocationHighlight() {
        switch (currentLocation.getLocation()) {
            case "Kategorier":
                    categoriesButton.getStyleClass().add("main-nav-button-active");
                    break;
            case "Erbjudanden":
                    offersButton.getStyleClass().add("main-nav-button-active");
                    break;
            case "Favoriter":
                    favoritesButton.getStyleClass().add("main-nav-button-active");
                    break;
            case "Hjälp":
                    helpButton.getStyleClass().add("main-nav-button-active");
                    break;
            case "Kundvagn":
                    shoppingCartImage.setImage(new Image(getClass().getResourceAsStream("icons/shopping-cart-active.png")));
                    break;
            case "Profil":
                    profileImage.setImage(new Image(getClass().getResourceAsStream("icons/profile-active.png")));
                    break;
            default:
                    categoriesButton.getStyleClass().add("main-nav-button-active");
                break;
        }
    }

    @FXML
    public void goBackToPrevious() {
        if (previousLocations.size() > 1) {
            LocationInfo previousLocation = previousLocations.get(previousLocations.size() - 1);
            previousLocations.remove(previousLocations.size() - 1);

            switch (previousLocation.getLocation()) {
                case "Profil":
                    goToProfile(true);
                    break;
                case "Favoriter":
                    showFavorites(true);
                    break;
                case "Kundvagn":
                    goToShoppingCart(false);
                    break;
                case "Kategorier":
                    goToCategories(true);
                    break;
                default:
                    showCategory(ProductCategory.valueOf(previousLocation.getLocation()), false);
            }
        }
    }
}