package controller;

import dao.EmployeePOJO;
import dao.WorkdayPOJO;
import driver.Driver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.WorkdayHandler;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

public class ApprovedOvertimeController extends Controller{

    @FXML
    private AnchorPane navBar_container;

    @FXML
    private Button  overtimeAddBtn;

    @FXML
    private TableView<WorkdayPOJO> workdayTv;
    @FXML
    private TableColumn<WorkdayPOJO, String> dateTc, overtimeInTc, overtimeOutTc, timeIn1Tc,
            timeOut1Tc, timeIn2Tc, timeOut2Tc, overtimehead1Tc, overtimehead2Tc, workhourheadTc, editTc;

    private WorkdayHandler model;
    private ObservableList<WorkdayPOJO> entries = FXCollections.observableArrayList();

    @Override
    public void update() {
        // load navigation bar
        if (navBar_container.getChildren().isEmpty()) {
            navBar_container.getChildren().add(Driver.getScreenController().getNavBar());
        }

        Date today = new Date();
        long ms = today.getTime();
        model = new WorkdayHandler(new Date(ms - 15 * 86400000L), today);
        entries.setAll(model.getEntries());
        workdayTv.setItems(entries);
    }

    @FXML
    public void initialize() {
        disableReorder();

        dateTc.setCellValueFactory(new PropertyValueFactory<>("dateString"));
        overtimeInTc.setCellValueFactory(new PropertyValueFactory<>("overtimeInString"));
        overtimeOutTc.setCellValueFactory(new PropertyValueFactory<>("overtimeOutString"));
        timeIn1Tc.setCellValueFactory(new PropertyValueFactory<>("timeIn1String"));
        timeOut1Tc.setCellValueFactory(new PropertyValueFactory<>("timeOut1String"));
        timeIn2Tc.setCellValueFactory(new PropertyValueFactory<>("timeIn2String"));
        timeOut2Tc.setCellValueFactory(new PropertyValueFactory<>("timeOut2String"));
    }

    /**
     * Method for adding a table row
     *
     */
    public void onAddClick () throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ApprovedOvertimeForm.fxml"));
        Parent root = fxmlLoader.load();

        Stage stage = new Stage();
        stage.setOpacity(1);
        stage.setScene(new Scene(root, 504, 448));
        stage.setResizable(false);
        stage.showAndWait();
    }


    /**
     * This method simply disables reorderability for all table columns in ApprovedOvertime.fxml
     */
    private void disableReorder() {
        overtimeInTc.setReorderable(false);
        overtimeOutTc.setReorderable(false);
        timeIn1Tc.setReorderable(false);
        timeOut1Tc.setReorderable(false);
        timeIn2Tc.setReorderable(false);
        timeOut2Tc.setReorderable(false);
        dateTc.setReorderable(false);
        overtimehead1Tc.setReorderable(false);
        overtimehead2Tc.setReorderable(false);
        workhourheadTc.setReorderable(false);
    }


}
