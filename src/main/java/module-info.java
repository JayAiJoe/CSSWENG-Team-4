module Sample.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;
    requires org.apache.poi.poi;
    requires commons.math3;
    requires SparseBitSet;

    opens driver;
    exports driver;

    opens controller;
    exports controller;

    opens model;
    exports model;
    exports dao;
    opens dao;
    opens wrapper;
    exports wrapper;
}