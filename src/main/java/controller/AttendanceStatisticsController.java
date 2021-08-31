package controller;

import driver.Driver;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;

public class AttendanceStatisticsController extends Controller{

    @FXML
    private AnchorPane navBar_container;

    //table columns for tableview in AttendanceStatisticsController.java
    @FXML
    private TableColumn startdateTc, paydateTc, idTc, nameTc, presentTc, absentTc,
        overtimeTc, lateTc, holidayTc;

    @Override
    public void update() {
        // load navigation bar
        if (navBar_container.getChildren().isEmpty()) {
            navBar_container.getChildren().add(Driver.getScreenController().getNavBar());
        }
    }


}
