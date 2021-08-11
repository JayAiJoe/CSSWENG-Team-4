module Sample.main {
    requires javafx.controls;
    requires javafx.fxml;

    opens driver;
    exports driver;

    opens controller;
    exports controller;

    opens model;
    exports model;
}