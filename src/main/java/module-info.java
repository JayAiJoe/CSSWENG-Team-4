module Sample.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;

    opens driver;
    exports driver;

    opens controller;
    exports controller;

    opens model;
    exports model;
}