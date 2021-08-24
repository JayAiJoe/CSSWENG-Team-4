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

    /**
     * initialization of work hours and overtime related objects
     */
    @FXML
    private Button acceptBtn, rejectBtn, checkAllBtn;

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
}