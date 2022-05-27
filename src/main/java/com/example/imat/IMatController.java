package com.example.imat;

import com.example.imat.models.LocationInfo;
import com.example.imat.utils.StyleUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.*;

import java.util.ArrayList;
import java.util.Arrays;
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
    @FXML private AnchorPane helpPage;
    @FXML private AnchorPane productsPage;
    @FXML private ScrollPane categoriesPage;
    @FXML private FlowPane productsFlowPane;
    @FXML private SplitPane shoppingCartSplitPane;
    @FXML private FlowPane shoppingCartFlowPane;
    @FXML private FlowPane favoriteFlowPane;
    @FXML private ScrollPane favoriteScrollPane;
    @FXML private Label shoppingCartCounterLabel;
    @FXML private ScrollPane offersPage;

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

    @FXML private TextField profileDeliveryFirstname;
    @FXML private TextField profileDeliveryLastname;
    @FXML private TextField profileDeliveryAddress;
    @FXML private TextField profileDeliveryPostalcode;
    @FXML private TextField profileDeliveryCity;

    @FXML private TextField profilePaymentName;
    @FXML private TextField profilePaymentBank;
    @FXML private TextField profilePaymentCardNumber;
    @FXML private TextField profilePaymentDate;
    @FXML private TextField profilePaymentCVC;
  
    @FXML private AnchorPane confirmationPane;
    @FXML private Label confirmDateLabel;

    @FXML private AnchorPane
            date1030, date1330, date1530,
            date1031, date1331, date1531,
            date1001, date1301, date1501,
            date1002, date1302, date1502,
            date1003, date1303, date1503,
            date1004, date1304, date1504,
            date1005, date1305, date1505;
    private ArrayList<AnchorPane> deliveryList = new ArrayList<>();

    @FXML private FlowPane profileHistoryFlowPane;

    @FXML private FlowPane offersFlowPane;

    @FXML private Label deliveryLabel;
    
    @FXML private AnchorPane profileHistoryButton;
    @FXML private AnchorPane profileDetailsButton;

    @FXML
    public void initialize() {
        // Shutdown hooks
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            dataHandler.shutDown();
        }));

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

        for (Product product : dataHandler.getProducts()) {
            switch (product.getName()) {
                case "Apelsin":
                    offersFlowPane.getChildren().add(new OfferCard(product, 5, this));
                    break;
                case "Blomkål":
                    offersFlowPane.getChildren().add(new OfferCard(product, 30, this));
                    break;
                case "Solrosfrön":
                    offersFlowPane.getChildren().add(new OfferCard(product, 100, this));
                    break;
                default:
                    break;
            }
        }

        setupDatePicker();

        searchBar.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                searchPane.getChildren().clear();
                if(t1 != ""){
                    searchBase.toFront();
                    searchBase.setStyle("-fx-opacity: 1");

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

    private void setDeliveryDate(int pickedTime) {
        deliveryList.forEach(pane -> pane.getStyleClass().remove("selectedTime"));
        deliveryList.get(pickedTime).getStyleClass().add("selectedTime");

        String deliveryTime = "";
        String deliveryDate = "";
        switch (pickedTime) {
            case 0:
                deliveryTime = "10:00 - 10:45";
                deliveryDate = "30/5";
                break;
            case 1:
                deliveryTime = "13:00 - 13:45";
                deliveryDate = "30/5";
                break;
            case 2:
                deliveryTime = "15:00 - 15:45";
                deliveryDate = "30/5";
                break;
            case 3:
                deliveryTime = "10:00 - 10:45";
                deliveryDate = "31/5";
                break;
            case 4:
                deliveryTime = "13:00 - 13:45";
                deliveryDate = "31/5";
                break;
            case 5:
                deliveryTime = "15:00 - 15:45";
                deliveryDate = "31/5";
                break;
            case 6:
                deliveryTime = "10:00 - 10:45";
                deliveryDate = "1/6";
                break;
            case 7:
                deliveryTime = "13:00 - 13:45";
                deliveryDate = "1/6";
                break;
            case 8:
                deliveryTime = "15:00 - 15:45";
                deliveryDate = "1/6";
                break;
            case 9:
                deliveryTime = "10:00 - 10:45";
                deliveryDate = "2/6";
                break;
            case 10:
                deliveryTime = "13:00 - 13:45";
                deliveryDate = "2/6";
                break;
            case 11:
                deliveryTime = "15:00 - 15:45";
                deliveryDate = "2/6";
                break;
            case 12:
                deliveryTime = "10:00 - 10:45";
                deliveryDate = "3/6";
                break;
            case 13:
                deliveryTime = "13:00 - 13:45";
                deliveryDate = "3/6";
                break;
            case 14:
                deliveryTime = "15:00 - 15:45";
                deliveryDate = "3/6";
                break;
            case 15:
                deliveryTime = "10:00 - 10:45";
                deliveryDate = "4/6";
                break;
            case 16:
                deliveryTime = "13:00 - 13:45";
                deliveryDate = "4/6";
                break;
            case 17:
                deliveryTime = "15:00 - 15:45";
                deliveryDate = "4/6";
                break;
            case 18:
                deliveryTime = "10:00 - 10:45";
                deliveryDate = "5/6";
                break;
            case 19:
                deliveryTime = "13:00 - 13:45";
                deliveryDate = "5/6";
                break;
            case 20:
                deliveryTime = "15:00 - 15:45";
                deliveryDate = "5/6";
                break;
        }

        deliveryLabel.setText("Leveransen sker mellan " + deliveryTime + " den " + deliveryDate);
        confirmDateLabel.setText("Mellan " + deliveryTime + " den " + deliveryDate);
    }

    private void showSeachProducts(List<Product> products){
        for (Product product : products) {
            searchPane.getChildren().add(new SearchCard(product,this));
        }
    }

    @FXML public void goToOffers() {
        goToOffers(true);
    }

    private void goToOffers(boolean addToHistory) {
        clearLocationHistory();
        addToLocationHistory(new LocationInfo("Erbjudanden", "Erbjudanden", "Erbjudanden"), addToHistory);
        resetWizard();

        offersPage.toFront();
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
        if (currentLocation != null) {
            if (currentLocation.getLocation().equals("Kundvagn")) return;
        }

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
            updateDeliveryDataFromBackend();
            wizardPage2Done = true;
        }
        wizardPageNavigation = 2;
        wizardPage2.toFront();
        updateWizardIndicator();
    }

    private void updateDeliveryDataFromBackend() {
        Customer customer = dataHandler.getCustomer();

        deliveryFirstName.setText(customer.getFirstName());
        deliveryLastName.setText(customer.getLastName());
        deliveryAddress.setText(customer.getAddress());
        deliveryPostalCode.setText(customer.getPostCode());
        deliveryCity.setText(customer.getPostAddress());
    }

    @FXML public void goToPayment() {
        goToPayment(true);
    }

    public void goToPayment(boolean doSpecial) {
        wizardPageNavigation = 3;

        if (doSpecial && !wizardPage3Done) {
            paymentFirstName.setText(deliveryFirstName.getText());
            paymentLastName.setText(deliveryLastName.getText());
            paymentAddress.setText(deliveryAddress.getText());
            paymentPostalCode.setText(deliveryPostalCode.getText());
            paymentCity.setText(deliveryCity.getText());

            updatePaymentDataFromBackend();

            wizardPage3Done = true;
        }

        wizardPage3.toFront();
        updateWizardIndicator();
    }

    private void updatePaymentDataFromBackend() {
        CreditCard creditCard = dataHandler.getCreditCard();

        paymentCardName.setText(creditCard.getHoldersName());
        paymentCardBank.setText(creditCard.getCardType());
        paymentCardNumber.setText(creditCard.getCardNumber());
        int month = creditCard.getValidMonth();
        int year = creditCard.getValidYear();
        paymentCardDate.setText((month > 9 ? month : "0" + month) + "/" + (year > 9 ? year : "0" + year));
        paymentCardCVC.setText(String.valueOf(creditCard.getVerificationCode()));
    }

    @FXML public void pay(){
        boolean somethingIsWrong = false;
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

        if(paymentCardName.getText().equals("")){
            setBorderColor(paymentCardName);
        }

        if(paymentCardBank.getText().equals("")){
            setBorderColor(paymentCardBank);
        }

        if(!cardPat.matcher(paymentCardNumber.getText()).find()){
            somethingIsWrong = true;
            setBorderColor(paymentCardNumber);
        }
        if(!cvcPat.matcher(paymentCardCVC.getText()).find()){
            somethingIsWrong = true;
            setBorderColor(paymentCardCVC);
        }
        if(!datePat.matcher(paymentCardDate.getText()).find()){
            somethingIsWrong = true;
            setBorderColor(paymentCardDate);
        }
        if(somethingIsWrong){
            return;
        }
        confirmPayment();
    }

    @FXML public void goToHelp() {
        goToHelp(true);
    }

    private void goToHelp(boolean addToHistory) {
        clearLocationHistory();
        addToLocationHistory(new LocationInfo("Hjälp", "Hjälp", "Hjälp"), addToHistory);
        resetWizard();

        helpPage.toFront();
    }

    private void updateHistory() {
        profileHistoryFlowPane.getChildren().clear();
        for (Order order : dataHandler.getOrders()) {
            profileHistoryFlowPane.getChildren().add(new OrderHistoryCard(order));
        }
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

        if (paymentAddressSaveCheckbox.isSelected()) {
            Customer customer = dataHandler.getCustomer();

            customer.setFirstName(paymentFirstName.getText());
            customer.setLastName(paymentLastName.getText());
            customer.setAddress(paymentAddress.getText());
            customer.setPostCode(paymentPostalCode.getText());
            customer.setPostAddress(paymentCity.getText());
        }

        dataHandler.placeOrder(true);
        confirmationPane.toFront();
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
        addToLocationHistory(new LocationInfo(category.name(), CategoryCard.getPrettyCategoryName(category), "Kategorier/" + CategoryCard.getPrettyCategoryName(category)), addToHistory);
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
        if (currentLocation != null) {
            if (currentLocation.getLocation().equals("Profil")) return;
        }
        addToLocationHistory(new LocationInfo("Profil", "Profil", "Profil"), addToHistory);
        resetWizard();

        profileAnchorPane.toFront();
        showProfileInformation();
    }

    @FXML public void showProfileHistory(){
        updateHistory();
        profileHistoryButton.getStyleClass().add("choice-bar-button-active");
        profileDetailsButton.getStyleClass().remove("choice-bar-button-active");
        profileHistoryPane.toFront();
    }

    @FXML public void showProfileInformation(){
        fillProfileInformation();
        profileHistoryButton.getStyleClass().remove("choice-bar-button-active");
        profileDetailsButton.getStyleClass().add("choice-bar-button-active");
        profileInformationPane.toFront();
    }

    private void fillProfileInformation() {
        Customer savedCustomerData = dataHandler.getCustomer();

        profileDeliveryFirstname.setText(savedCustomerData.getFirstName());
        profileDeliveryLastname.setText(savedCustomerData.getLastName());
        profileDeliveryAddress.setText(savedCustomerData.getAddress());
        profileDeliveryPostalcode.setText(savedCustomerData.getPostCode());
        profileDeliveryCity.setText(savedCustomerData.getPostAddress());

        CreditCard savedCreditCardData = dataHandler.getCreditCard();

        profilePaymentName.setText(savedCreditCardData.getHoldersName());
        profilePaymentBank.setText(savedCreditCardData.getCardType());
        profilePaymentCardNumber.setText(savedCreditCardData.getCardNumber());
        int month = savedCreditCardData.getValidMonth();
        int year = savedCreditCardData.getValidYear();
        profilePaymentDate.setText((month > 9 ? month : "0" + month) + "/" + (year > 9 ? year : "0" + year));
        profilePaymentCVC.setText(String.valueOf(savedCreditCardData.getVerificationCode()));
    }

    @FXML public void saveProfileAddressDetails() {
        Customer savedCustomerData = dataHandler.getCustomer();

        savedCustomerData.setFirstName(profileDeliveryFirstname.getText());
        savedCustomerData.setLastName(profileDeliveryLastname.getText());
        savedCustomerData.setAddress(profileDeliveryAddress.getText());
        savedCustomerData.setPostCode(profileDeliveryPostalcode.getText());
        savedCustomerData.setPostAddress(profileDeliveryCity.getText());
    }

    @FXML public void saveProfilePaymentDetails() {
        CreditCard savedCreditCardData = dataHandler.getCreditCard();

        savedCreditCardData.setHoldersName(profilePaymentName.getText());
        savedCreditCardData.setCardType(profilePaymentBank.getText());
        savedCreditCardData.setCardNumber(profilePaymentCardNumber.getText());
        savedCreditCardData.setValidMonth(Integer.parseInt(profilePaymentDate.getText().split("/")[0]));
        savedCreditCardData.setValidYear(Integer.parseInt(profilePaymentDate.getText().split("/")[1]));
        savedCreditCardData.setVerificationCode(Integer.parseInt(profilePaymentCVC.getText()));
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

        for (Node card : shoppingCartFlowPane.getChildren()) {
            card.getStyleClass().remove("background-selected");
        }

        cartProductNameLabel.setText(shoppingItem.getProduct().getName());
        Image image = new Image(getClass().getResourceAsStream("images/" + shoppingItem.getProduct().getImageName()));
        cartImage.setImage(image);
        cartProductPriceLabel.setText("(" + (int) shoppingItem.getAmount() + " st) " + shoppingItem.getTotal() + "kr");
        cartTotalProductPriceLabel.setText(shoppingItem.getProduct().getPrice() + " kr/st");
    }

    public void productItemPressed(Product product) {
        currentProduct = product;
        currentLocationLabel.setText(currentLocation.getBreadcrumb() + "/" + product.getName());
        detailProductLabel.setText(product.getName());
        detailProductPriceLabel.setText(product.getPrice() + " kr/st");
        detailProductImage.setImage(new Image(getClass().getResourceAsStream("images/" + product.getImageName())));
        detailCategoryLabel.setText("Kategori: " + CategoryCard.getPrettyCategoryName(product.getCategory()));
        updateAmountLabel();
        detailPage.toFront();
    }

    @FXML
    public void closeDetailPane(){
        updateLocationLabels(this.currentLocation);
        detailPage.toBack();
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
        shoppingCartCounterLabel.setText(String.valueOf(total) + " kr");
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
                case "Hjälp":
                    goToHelp(true);
                case "Erbjudanden":
                    goToOffers(true);
                    break;
                default:
                    showCategory(ProductCategory.valueOf(previousLocation.getLocation()), false);
            }
        }
    }

    private void setupDatePicker() {
        deliveryList.add(date1030);
        deliveryList.add(date1330);
        deliveryList.add(date1530);
        deliveryList.add(date1031);
        deliveryList.add(date1331);
        deliveryList.add(date1531);
        deliveryList.add(date1001);
        deliveryList.add(date1301);
        deliveryList.add(date1501);
        deliveryList.add(date1002);
        deliveryList.add(date1302);
        deliveryList.add(date1502);
        deliveryList.add(date1003);
        deliveryList.add(date1303);
        deliveryList.add(date1503);
        deliveryList.add(date1004);
        deliveryList.add(date1304);
        deliveryList.add(date1504);
        deliveryList.add(date1005);
        deliveryList.add(date1305);
        deliveryList.add(date1505);


        date1030.setOnMouseClicked(e -> setDeliveryDate(0));
        date1330.setOnMouseClicked(e -> setDeliveryDate(1));
        date1530.setOnMouseClicked(e -> setDeliveryDate(2));
        date1031.setOnMouseClicked(e -> setDeliveryDate(3));
        date1331.setOnMouseClicked(e -> setDeliveryDate(4));
        date1531.setOnMouseClicked(e -> setDeliveryDate(5));
        date1001.setOnMouseClicked(e -> setDeliveryDate(6));
        date1301.setOnMouseClicked(e -> setDeliveryDate(7));
        date1501.setOnMouseClicked(e -> setDeliveryDate(8));
        date1002.setOnMouseClicked(e -> setDeliveryDate(9));
        date1302.setOnMouseClicked(e -> setDeliveryDate(10));
        date1502.setOnMouseClicked(e -> setDeliveryDate(11));
        date1003.setOnMouseClicked(e -> setDeliveryDate(12));
        date1303.setOnMouseClicked(e -> setDeliveryDate(13));
        date1503.setOnMouseClicked(e -> setDeliveryDate(14));
        date1004.setOnMouseClicked(e -> setDeliveryDate(15));
        date1304.setOnMouseClicked(e -> setDeliveryDate(16));
        date1504.setOnMouseClicked(e -> setDeliveryDate(17));
        date1005.setOnMouseClicked(e -> setDeliveryDate(18));
        date1305.setOnMouseClicked(e -> setDeliveryDate(19));
        date1505.setOnMouseClicked(e -> setDeliveryDate(20));
    }
}