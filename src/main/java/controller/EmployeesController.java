package controller;

import driver.Driver;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class EmployeesController extends Controller{

    @FXML
    private AnchorPane navBar_container;

    @FXML
    private TableView employeesTv;

    @FXML
    private TableColumn nameTc, frequencyTc, modeTc, companyTc, wageTc, buttonTc;

    @Override
    public void update() {

        disableReorder();

        // load navigation bar
        if (navBar_container.getChildren().isEmpty()) {
            navBar_container.getChildren().add(Driver.getScreenController().getNavBar());
        }
    }

    private void disableReorder() {
        nameTc.setReorderable(false);
        companyTc.setReorderable(false);
        frequencyTc.setReorderable(false);
        buttonTc.setReorderable(false);
        modeTc.setReorderable(false);
        wageTc.setReorderable(false);
    }
}
