module Sample.main {
    requires javafx.controls;
    requires javafx.fxml;

    opens driver;

    exports driver;
}