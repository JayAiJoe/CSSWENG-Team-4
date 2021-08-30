package controller;

import driver.Driver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import model.OvertimeEntry;
import model.OvertimeHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class PendingOvertimeController extends Controller {
    @FXML
    private AnchorPane navBar_container;

    /**
     * initialization of Overtime Table related objects
     */
    @FXML
    private TableView<OvertimeEntry> overtimeTv;
    @FXML
    private TableColumn<OvertimeEntry, String> nameTc, dateTc;
    @FXML
    private TableColumn<OvertimeEntry, Integer> overtimeTc;
    @FXML
    private TableColumn<OvertimeEntry, Boolean> buttonTc;

    private OvertimeHandler model;
    private ObservableList<OvertimeEntry> entries = FXCollections.observableArrayList();

    @Override
    public void update() {
        // load navigation bar
        if (navBar_container.getChildren().isEmpty()) {
            navBar_container.getChildren().add(Driver.getScreenController().getNavBar());
        }

        Date today = new Date();
        long ms = today.getTime();
        model = new OvertimeHandler(new Date(ms - 15 * 86400000L), today);
        entries.setAll(model.getEntries());
        overtimeTv.setItems(entries);
    }

    @FXML
    public void initialize() {
        //setColumnWidth();
        disableReorder();

        overtimeTv.setEditable(true);
        overtimeTv.setRowFactory(tv -> {
            TableRow<OvertimeEntry> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    boolean status = row.getItem().getStatus();
                    row.getItem().setStatus(!status);
                    overtimeTv.refresh();
                }
            });
            return row;
        });

        nameTc.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        overtimeTc.setCellValueFactory(new PropertyValueFactory<>("minutes"));
        dateTc.setCellValueFactory(new PropertyValueFactory<>("dateString"));
        buttonTc.setCellValueFactory(new PropertyValueFactory<>("status"));
        buttonTc.setCellFactory(t -> {
            CheckBox checkBox = new CheckBox();
            TableCell<OvertimeEntry, Boolean> cell = new TableCell<>() {
                @Override
                public void updateItem(Boolean item, boolean empty) {
                    if (empty) {
                        setGraphic(null);
                    } else {
                        checkBox.setSelected(item);
                        setGraphic(checkBox);
                    }
                }
            };
            checkBox.selectedProperty().addListener((obs, oldValue, newValue) -> {
                if (cell.getTableRow().getItem() != null) {
                    cell.getTableRow().getItem().setStatus(newValue);
                }
            });
            cell.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            cell.setAlignment(Pos.CENTER);
            return cell;
        });
    }

    /**
     * This method sets the size of table columns present in PendingOvertime.fxml
     */

    private void setColumnWidth() {
        nameTc.prefWidthProperty().bind(overtimeTv.widthProperty().divide(11));
        overtimeTc.prefWidthProperty().bind(overtimeTv.widthProperty().divide(11));
        dateTc.prefWidthProperty().bind(overtimeTv.widthProperty().divide(11));
        buttonTc.prefWidthProperty().bind(overtimeTv.widthProperty().divide(11));
    }

    /**
     * This method simply disables reorderability for all table columns in Payroll.fxml
     */
    private void disableReorder() {
        nameTc.setReorderable(false);
        overtimeTc.setReorderable(false);
        dateTc.setReorderable(false);
        buttonTc.setReorderable(false);
    }

    @FXML
    private void onCheckAllAction() {
        for (OvertimeEntry entry: entries) {
            entry.setStatus(true);
        }
        overtimeTv.refresh();
    }

    @FXML
    private void onApproveAction() {
        StringBuilder sb = new StringBuilder();
        ArrayList<OvertimeEntry> approved = new ArrayList<>();
        for (OvertimeEntry entry: entries) {
            if (entry.getStatus()) {
                approved.add(entry);
                sb.append(String.format("%-25s\t\t", entry.getEmployeeName()));
                sb.append(String.format("%-3d\tminutes\t", entry.getMinutes()));
                sb.append(entry.getDateString()).append("\n");
            }
        }

        if (approved.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setGraphic(null);
            alert.setContentText("No entries selected!");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Approve Overtime?");
        alert.setContentText(sb.toString());
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            entries.removeAll(approved);
            model.save(approved);
        }
    }

    @FXML
    private void onRejectAction() {
        StringBuilder sb = new StringBuilder();
        ArrayList<OvertimeEntry> rejected = new ArrayList<>();
        for (OvertimeEntry entry: entries) {
            if (entry.getStatus()) {
                entry.setStatus(false);
                rejected.add(entry);
                sb.append(String.format("%-25s\t\t", entry.getEmployeeName()));
                sb.append(String.format("%-3d\tminutes\t", entry.getMinutes()));
                sb.append(entry.getDateString()).append("\n");
            }
        }

        if (rejected.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setGraphic(null);
            alert.setContentText("No entries selected!");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Reject Overtime?");
        alert.setContentText(sb.toString());
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            entries.removeAll(rejected);
            model.save(rejected);
        } else {
            for (OvertimeEntry entry: rejected) {
                entry.setStatus(true);
            }
        }
    }

    @FXML
    private void onCheckDateAction(){

    }
}