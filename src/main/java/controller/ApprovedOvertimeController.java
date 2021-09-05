package controller;

import dao.WorkdayPOJO;
import driver.Driver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.WorkdayHandler;

import java.util.Date;
import java.util.Optional;

public class ApprovedOvertimeController extends Controller{

    @FXML
    private AnchorPane navBar_container;

    @FXML
    private Button overtimeEditBtn, overtimeCancelBtn, overtimeSaveBtn, overtimeAddBtn, overtimeRemoveBtn;

    @FXML
    private TableView<WorkdayPOJO> workdayTv;
    @FXML
    private TableColumn<WorkdayPOJO, String> dateTc, overtimeInTc, overtimeOutTc, timeIn1Tc,
            timeOut1Tc, timeIn2Tc, timeOut2Tc, overtimehead1Tc, overtimehead2Tc, workhourheadTc;

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
     * @param mouseEvent
     */
    public void onAddClick (MouseEvent mouseEvent){

    }

    /**
     * Method for removing a table row
     * @param mouseEvent
     */
    public void onRemoveClick (MouseEvent mouseEvent){

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

    /**
     * This method is responsible for handling the edits for approved overtime hours.
     */
    public void onOvertimeEditClick(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == overtimeEditBtn) {
            //buttons
            overtimeEditBtn.setVisible(false);
            overtimeEditBtn.setDisable(true);
            overtimeCancelBtn.setDisable(false);
            overtimeCancelBtn.setVisible(true);
            overtimeSaveBtn.setVisible(true);
            overtimeSaveBtn.setDisable(false);

        } else if (mouseEvent.getSource() == overtimeCancelBtn) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setGraphic(null);
            alert.setContentText("Cancel changes?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                //buttons
                overtimeEditBtn.setVisible(true);
                overtimeEditBtn.setDisable(false);
                overtimeCancelBtn.setVisible(false);
                overtimeCancelBtn.setDisable(true);
                overtimeSaveBtn.setVisible(false);
                overtimeSaveBtn.setDisable(true);
                System.out.println("Ok is pressed");
            } else {
                // ... user chose CANCEL or closed the dialog
                System.out.println("cancel is pressed");
            }
        } else if (mouseEvent.getSource() == overtimeSaveBtn) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setGraphic(null);
            alert.setContentText("Apply changes?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                overtimeEditBtn.setVisible(true);
                overtimeEditBtn.setDisable(false);
                overtimeCancelBtn.setVisible(false);
                overtimeCancelBtn.setDisable(true);
                overtimeSaveBtn.setVisible(false);
                overtimeSaveBtn.setDisable(true);

                System.out.println("Ok is pressed");
            } else {
                // ... user chose CANCEL or closed the dialog
                System.out.println("cancel is pressed");
            }
        }
    }


}
