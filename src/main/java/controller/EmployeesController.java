package controller;

import driver.Driver;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class EmployeesController extends Controller{

    @FXML
    private AnchorPane navBar_container;

    @Override
    public void update() {
        // load navigation bar
        if (navBar_container.getChildren().isEmpty()) {
            navBar_container.getChildren().add(Driver.getScreenController().getNavBar());
        }
    }
}
