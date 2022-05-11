module com.example.imat {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires projectbackend;

    opens com.example.imat to javafx.fxml;
    exports com.example.imat;
}