package com.example.imat;

import com.example.imat.models.LocationInfo;
import com.example.imat.utils.StyleUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.regex.Pattern;

public class IMatController implements ShoppingCartListener {

    private IMatDataHandler dataHandler = IMatDataHandler.getInstance();
    private ArrayList<LocationInfo> previousLocations = new ArrayList<LocationInfo>();
    private LocationInfo currentLocation;
    private ShoppingCartHandler cartHandler = new ShoppingCartHandler();

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

    @FXML private AnchorPane detailPage;
    @FXML private ImageView detailProductImage;
    @FXML private Label detailProductLabel;
    @FXML private Label detailProductPriceLabel;
    @FXML private Label detailAmountLabel;
    @FXML private Label detailCategoryLabel;
    private Product currentProduct;

    @FXML private AnchorPane wizardPage;
    @FXML private AnchorPane wizardPage1;
    @FXML private ScrollPane wizardPage2;
    @FXML private AnchorPane wizardPage3;

    private int wizardPageNavigation = 1;

    private boolean wizardPage1Done = false;
    private boolean wizardPage2Done = false;
    private boolean wizardPage3Done = false;

    @FXML private FlowPane wizardShoppingFlowPane;
    @FXML private Label wizardTotalPriceLabel;
    @FXML private Label wizardTotalAmount;

    @FXML private TextField deliveryFirstName;
    @FXML private TextField deliveryLastName;
    @FXML private TextField deliveryAddress;
    @FXML private TextField deliveryPostalCode;
    @FXML private TextField deliveryCity;

    @FXML private TextField paymentFirstName;
    @FXML private TextField paymentLastName;
    @FXML private TextField paymentAddress;
    @FXML private TextField paymentPostalCode;
    @FXML private TextField paymentCity;
    @FXML private CheckBox paymentAddressSaveCheckbox;

    @FXML private TextField paymentCardName;
    @FXML private TextField paymentCardBank;
    @FXML private TextField paymentCardNumber;
    @FXML private TextField paymentCardDate;
    @FXML private TextField paymentCardCVC;
    @FXML private CheckBox paymentSaveCardCheckbox;

    @FXML private Label wizardIndicator1;
    @FXML private Label wizardIndicator2;
    @FXML private Label wizardIndicator3;

    @FXML private ImageView wizardBackwardsArrow;
    @FXML private ImageView wizardForwardArrow;

    @FXML private TextField searchBar;
    @FXML private FlowPane searchPane;
    @FXML private ScrollPane searchBase;

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
        searchBar.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                searchPane.getChildren().clear();
                if(t1 != ""){
                    searchBase.toFront();
                    searchBase.setStyle("-fx-opacity: 0");

                    List<Product> products = dataHandler.findProducts(s);
                    showSeachProducts(products);
                }
                else {
                    searchBase.toBack();
                    searchBase.setStyle("-fx-opacity: 0");
                }
            }
        });
    }

    private void showSeachProducts(List<Product> products){
        for (Product product : products) {
            searchPane.getChildren().add(new SearchCard(product,this));
        }
    }

    @FXML public void goToCategories() {
        goToCategories(true);
    }

    public void goToCategories(boolean addToHistory){
        clearLocationHistory();
        addToLocationHistory(new LocationInfo("Kategorier", "Kategorier", "Kategorier"), addToHistory);
        resetWizard();

        categoriesPage.toFront();
    }

    @FXML public void goToShoppingCart() {
        goToShoppingCart(true);
    }

    public void goToShoppingCart(boolean addToHistory){
        addToLocationHistory(new LocationInfo("Kundvagn", "Kundvagn", "Kundvagn"), addToHistory);
        resetWizard();
        cartHandler.updateShoppingCart();

        shoppingCartFlowPane.getChildren().clear();
        shoppingCartSplitPane.toFront();
        updateShoppingCartInformation();
        List<ShoppingItem> shoppingItems = dataHandler.getShoppingCart().getItems();

        for (ShoppingItem shoppingItem: shoppingItems) {
            if(shoppingItem.getAmount() > 0){
                shoppingCartFlowPane.getChildren().add(new ShoppingCartCard(shoppingItem, this));
            }
        }
    }

    @FXML public void goToWizard() {
        goToWizard(true);
    }

    public void goToWizard(boolean doSpecial) {
        wizardPageNavigation = 1;

        if (doSpecial && !wizardPage1Done) {
            addToLocationHistory(new LocationInfo("Wizard", "", ""), true);
            wizardShoppingFlowPane.getChildren().clear();

            wizardTotalPriceLabel.setText(dataHandler.getShoppingCart().getTotal() + " kr");
            int totalAmount = 0;
            for (ShoppingItem shoppingItem: dataHandler.getShoppingCart().getItems()) {
                totalAmount += shoppingItem.getAmount();
            }

            wizardTotalAmount.setText("Totalt (" + totalAmount + " varor)");

            List<ShoppingItem> shoppingItems = dataHandler.getShoppingCart().getItems();
            for (ShoppingItem shoppingItem: shoppingItems) {
                wizardShoppingFlowPane.getChildren().add(new WizardProductCard(shoppingItem));
            }
            wizardPage1Done = true;
        }

        wizardPage.toFront();
        wizardPage1.toFront();
        updateWizardIndicator();
    }

    @FXML void goToDelivery() {
        goToDelivery(true);
    }

    public void goToDelivery(boolean doSpecial) {
        if (doSpecial) {
            wizardPage2Done = true;
        }
        wizardPageNavigation = 2;
        wizardPage2.toFront();
        updateWizardIndicator();
    }

    @FXML public void goToPayment() {
        goToPayment(true);
    }

    public void goToPayment(boolean doSpecial) {
        wizardPageNavigation = 3;

        if (doSpecial && !wizardPage3Done) {
            Customer customer = dataHandler.getCustomer();
            customer.setFirstName(deliveryFirstName.getText());
            customer.setLastName(deliveryLastName.getText());
            customer.setAddress(deliveryAddress.getText());
            customer.setPostCode(deliveryPostalCode.getText());
            customer.setPostAddress(deliveryCity.getText());

            paymentFirstName.setText(customer.getFirstName());
            paymentLastName.setText(customer.getLastName());
            paymentAddress.setText(customer.getAddress());
            paymentPostalCode.setText(customer.getPostCode());
            paymentCity.setText(customer.getPostAddress());

            wizardPage3Done = true;
        }

        wizardPage3.toFront();
        updateWizardIndicator();
    }

    @FXML public void pay(){
        boolean somthingIsWrong = false;
        removeBorder(paymentCardNumber);
        removeBorder(paymentCardCVC);
        removeBorder(paymentCardDate);
        removeBorder(paymentCardName);
        removeBorder(paymentCardBank);
        final String dateRegex = "^\\d{2}\\/\\d{2}$";
        final String cardRegex = "^\\d{16}$";
        final String cvcRegex = "^\\d{3}$";
        Pattern cardPat = Pattern.compile(cardRegex);
        Pattern cvcPat = Pattern.compile(cvcRegex);
        Pattern datePat = Pattern.compile(dateRegex);

        if(paymentCardName.getText() == ""){
            setBorderColor(paymentCardName);
        }

        if(paymentCardBank.getText() == ""){
            setBorderColor(paymentCardBank);
        }

        if(!cardPat.matcher(paymentCardNumber.getText()).find()){
            somthingIsWrong = true;
            setBorderColor(paymentCardNumber);
        }
        if(!cvcPat.matcher(paymentCardCVC.getText()).find()){
            somthingIsWrong = true;
            setBorderColor(paymentCardCVC);
        }
        if(!datePat.matcher(paymentCardDate.getText()).find()){
            somthingIsWrong = true;
            setBorderColor(paymentCardDate);
        }
        if(somthingIsWrong){
            return;
        }
        confirmPayment();
    }

    private void setBorderColor(TextField textField){
        textField.setStyle("-fx-border-width: 2px; -fx-border-color: #EC2020; -fx-border-radius: 9999999999;");
    }

    private void removeBorder(TextField textField) {
        textField.setStyle("-fx-border-width: 0px; -fx-border-color: #EC2020; -fx-border-radius: 9999999999;");
    }

        public void confirmPayment() {
        if (paymentSaveCardCheckbox.isSelected()) {
            CreditCard card = dataHandler.getCreditCard();
            card.setHoldersName(paymentCardName.getText());
            card.setCardType(paymentCardBank.getText());
            card.setCardNumber(paymentCardNumber.getText());
            String cardExpiry = paymentCardDate.getText();
            String expiryMonth = cardExpiry.substring(0, 2);
            String expiryYear = cardExpiry.substring(3, 5);

            card.setValidMonth(Integer.parseInt(expiryMonth));
            card.setValidYear(Integer.parseInt(expiryYear));
            card.setVerificationCode(Integer.parseInt(paymentCardCVC.getText()));
        }

        if (!paymentAddressSaveCheckbox.isSelected()) {
            Customer customer = dataHandler.getCustomer();

            customer.setFirstName("");
            customer.setLastName("");
            customer.setAddress("");
            customer.setPostCode("");
            customer.setPostAddress("");
        }

        dataHandler.placeOrder(true);

        goToCategories(true);
    }

    public void wizardForward() {
        switch (wizardPageNavigation) {
            case 1:
                goToDelivery(false);
                break;
            case 2:
                goToPayment(false);
                break;
            default:
                goToWizard(false);
                break;
        }
    }

    public void wizardBackwards() {
        switch (wizardPageNavigation) {
            case 2:
                goToWizard(false);
                break;
            case 3:
                goToDelivery();
                break;
            default:
                wizardPageNavigation = 1;
                goToWizard(false);
                break;
        }
    }

    private void updateWizardIndicator() {
        wizardIndicator1.getStyleClass().remove("active-wizard-tab-number");
        wizardIndicator2.getStyleClass().remove("active-wizard-tab-number");
        wizardIndicator3.getStyleClass().remove("active-wizard-tab-number");

        switch (wizardPageNavigation) {
            case 1:
                wizardIndicator1.getStyleClass().add("active-wizard-tab-number");
                break;
            case 2:
                wizardIndicator2.getStyleClass().add("active-wizard-tab-number");
                break;
            case 3:
                wizardIndicator3.getStyleClass().add("active-wizard-tab-number");
                break;
            default:
                break;
        }

        if (wizardPageNavigation > 1) {
            wizardBackwardsArrow.setVisible(true);
        } else {
            wizardBackwardsArrow.setVisible(false);
        }

        if (wizardPage2Done && wizardPageNavigation < 2 || wizardPage3Done && wizardPageNavigation < 3) {
            wizardForwardArrow.setVisible(true);
        } else {
            wizardForwardArrow.setVisible(false);
        }
    }

    private void resetWizard() {
        wizardPageNavigation = 1;
        wizardPage1Done = false;
        wizardPage2Done = false;
        wizardPage3Done = false;

        deliveryFirstName.setText("");
        deliveryLastName.setText("");
        deliveryAddress.setText("");
        deliveryCity.setText("");
        deliveryPostalCode.setText("");

        paymentFirstName.setText("");
        paymentLastName.setText("");
        paymentAddress.setText("");
        paymentCity.setText("");
        paymentPostalCode.setText("");

        paymentCardName.setText("");
        paymentCardBank.setText("");
        paymentCardNumber.setText("");
        paymentCardDate.setText("");
        paymentCardCVC.setText("");

        if (previousLocations.size() > 1) {
            if (previousLocations.get(1).getLocation().equals("Wizard")) {
                previousLocations.remove(previousLocations.size() - 1);
                updateLocationLabels(this.currentLocation);
            }
        }

    }


    @FXML public void showCategory(ProductCategory category, boolean addToHistory) {
        addToLocationHistory(new LocationInfo(category.name(), CategoryCard.getPrettyCategoryName(category), CategoryCard.getPrettyCategoryName(category)), addToHistory);
        resetWizard();
        cartHandler.updateShoppingCart();

        productsFlowPane.getChildren().clear();
        productsPage.toFront();

        List<Product> products = dataHandler.getProducts();
        for (Product product : products) {
            if (product.getCategory() == category) {
               productsFlowPane.getChildren().add(new ProductCard(product, this));
            }
        }
    }

    @FXML public void showFavorites() {
        showFavorites(true);
    }

    public void showFavorites(boolean addToHistory) {
        clearLocationHistory();
        cartHandler.updateShoppingCart();
        addToLocationHistory(new LocationInfo("Favoriter", "Favoriter", "Favoriter"), addToHistory);
        resetWizard();

        favoriteFlowPane.getChildren().clear();
        favoriteScrollPane.toFront();
        List<Product> products = dataHandler.favorites();
        for (Product product : products) {
            favoriteFlowPane.getChildren().add(new ProductCard(product, this));
        }
    }

    @FXML public void goToProfile() {
        goToProfile(true);
    }

    public void goToProfile(boolean addToHistory){
        clearLocationHistory();
        addToLocationHistory(new LocationInfo("Profil", "Profil", "Profil"), addToHistory);
        resetWizard();

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
    public void togglePlusbuttonHover(){

    }

    @FXML
    public void cartItemPressed(ShoppingItem shoppingItem){
        cartProductNameLabel.setText(shoppingItem.getProduct().getName());
        Image image = new Image(getClass().getResourceAsStream("images/" + shoppingItem.getProduct().getImageName()));
        cartImage.setImage(image);
        cartProductPriceLabel.setText("(" + (int) shoppingItem.getAmount() + " st) " + shoppingItem.getTotal() + "kr");
        cartTotalProductPriceLabel.setText(shoppingItem.getProduct().getPrice() + " kr/st");
    }

    public void productItemPressed(Product product) {
        currentProduct = product;
        detailProductLabel.setText(product.getName());
        detailProductPriceLabel.setText(product.getPrice() + " kr/st");
        detailProductImage.setImage(new Image(getClass().getResourceAsStream("images/" + product.getImageName())));
        detailCategoryLabel.setText("Kategori: " + CategoryCard.getPrettyCategoryName(product.getCategory()));
        updateAmountLabel();
        detailPage.toFront();
    }

    @FXML
    public void closeDetailPane(){
        detailPage.toBack();
        /*if(currentLocation.getLocation() == "Favoriter")
            showFavorites(false);
        else
            showCategory(currentProduct.getCategory(),false);*/
    }

    private void updateAmountLabel(){
        detailAmountLabel.setText(cartHandler.getAmountInCart(currentProduct) + " st");
    }
    @FXML
    public void addItemToCartInteraction(){
        cartHandler.addProductToCart(currentProduct);
        updateAmountLabel();
    }

    @FXML
    public void removeItemFromCartInteraction(){
        cartHandler.removeProductFromCart(currentProduct, false);
        updateAmountLabel();
    }


    @FXML
    public void toggleProfileHover() {
        profileIsHovered = !profileIsHovered;
        if (!currentLocation.getLocation().equals("Profil")) { // Too lazy to make it hoverable when active.
            StyleUtils.toggleHoverImage(profileIsHovered, "icons/profile-hover.png", "icons/profile.png", profileImage);
        }
    }

    public void updateShoppingCartInformation(){
        List<ShoppingItem> shoppingItems = dataHandler.getShoppingCart().getItems();
        int ctr = 0;
        for (ShoppingItem shoppingitem:
             shoppingItems) {
            ctr += (int) shoppingitem.getAmount();
        }
        cartTotalAmountItemsLabel.setText(ctr + " st");
        cartTotalPriceLabel.setText(dataHandler.getShoppingCart().getTotal() + " kr");
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        int total = (int)dataHandler.getShoppingCart().getTotal();
        shoppingCartCounterLabel.setText(String.valueOf(total));
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