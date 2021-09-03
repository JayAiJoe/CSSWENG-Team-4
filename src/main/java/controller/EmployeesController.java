package controller;

import driver.Driver;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

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

    public void onAddAction() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/EmployeeForm.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setOpacity(1);
        stage.setScene(new Scene(root, 481, 448));
        stage.setResizable(false);
        stage.showAndWait();
    }
}
