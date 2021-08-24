package controller;

import driver.Driver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.OvertimeEntry;
import model.OvertimeHandler;

import java.util.ArrayList;
import java.util.Date;

public class OvertimeWorkHoursController extends Controller {
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
     * This method sets the size of table columns present in OvertimeWorkHours.fxml
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
        ArrayList<OvertimeEntry> approved = new ArrayList<>();
        for (OvertimeEntry entry: entries) {
            if (entry.getStatus()) {
                approved.add(entry);
            }
        }
        entries.removeAll(approved);
        model.save(approved);
    }

    @FXML
    private void onRejectAction() {
        ArrayList<OvertimeEntry> rejected = new ArrayList<>();
        for (OvertimeEntry entry: entries) {
            if (entry.getStatus()) {
                entry.setStatus(false);
                rejected.add(entry);
            }
        }
        entries.removeAll(rejected);
        model.save(rejected);
    }
}