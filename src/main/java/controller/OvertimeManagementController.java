package controller;

import driver.Driver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import model.OvertimeEntry;
import model.OvertimeHandler;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.function.Predicate;

public class OvertimeManagementController extends Controller {
    @FXML
    private AnchorPane navBar_container;


    /**
     * Initialization of Accepted Overtime Table related objects
     */
    @FXML
    private TableView acceptedOvertimeTv;
    @FXML
    private TableColumn acceptedNameTc, acceptedMinTc, acceptedDateTc;
    @FXML
    private ToggleButton filterBtn2;
    @FXML
    private DatePicker datePicker2;

    /**
     * initialization of Pending Overtime Table related objects
     */
    @FXML
    private TableView<OvertimeEntry> overtimeTv;
    @FXML
    private TableColumn<OvertimeEntry, String> nameTc, dateTc;
    @FXML
    private TableColumn<OvertimeEntry, Integer> overtimeTc;
    @FXML
    private TableColumn<OvertimeEntry, Boolean> buttonTc;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button checkAllBtn;
    @FXML
    private ToggleButton filterBtn;

    private OvertimeHandler model;
    private ObservableList<OvertimeEntry> entries;
    private FilteredList<OvertimeEntry> filteredEntries;


    @Override
    public void update() {
        // load navigation bar
        if (navBar_container.getChildren().isEmpty()) {
            navBar_container.getChildren().add(Driver.getScreenController().getNavBar());
        }

        Date today = new Date();
        long ms = today.getTime();
        model = new OvertimeHandler(new Date(ms - 16 * 86400000L), today);
        entries = FXCollections.observableArrayList();
        entries.setAll(model.getEntries());
        filteredEntries = new FilteredList<>(entries);
        overtimeTv.setItems(filteredEntries);
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
                    updateCheckAll();
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
                    updateCheckAll();
                }
            });
            cell.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            cell.setAlignment(Pos.CENTER);
            return cell;
        });
    }

    /**
     * This method sets the size of table columns present in OvertimeManagement.fxml
     */

    private void setColumnWidth() {
        nameTc.prefWidthProperty().bind(overtimeTv.widthProperty().divide(11));
        overtimeTc.prefWidthProperty().bind(overtimeTv.widthProperty().divide(11));
        dateTc.prefWidthProperty().bind(overtimeTv.widthProperty().divide(11));
        buttonTc.prefWidthProperty().bind(overtimeTv.widthProperty().divide(11));
    }

    /**
     * This method simply disables reorderability for all table columns in OvertimeManagement.fxml
     */
    private void disableReorder() {
        nameTc.setReorderable(false);
        overtimeTc.setReorderable(false);
        dateTc.setReorderable(false);
        buttonTc.setReorderable(false);
    }

    @FXML
    private void onCheckAllAction() {
        boolean value = checkAllBtn.getText().equals("Check All");
        for (OvertimeEntry entry : filteredEntries) {
            entry.setStatus(value);
        }
        if (value) {
            checkAllBtn.setText("Uncheck All");
        } else {
            checkAllBtn.setText("Check All");
        }
        overtimeTv.refresh();
    }

    @FXML
    private void onApproveAction() {
        StringBuilder sb = new StringBuilder();
        ArrayList<OvertimeEntry> approved = new ArrayList<>();
        for (OvertimeEntry entry: filteredEntries) {
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
            Predicate<? super OvertimeEntry> filter = filteredEntries.getPredicate();
            filteredEntries = new FilteredList<>(entries);
            filteredEntries.setPredicate(filter);
            overtimeTv.setItems(filteredEntries);
            model.save(approved);
        }
    }

    @FXML
    private void onRejectAction() {
        StringBuilder sb = new StringBuilder();
        ArrayList<OvertimeEntry> rejected = new ArrayList<>();
        for (OvertimeEntry entry: filteredEntries) {
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
            Predicate<? super OvertimeEntry> filter = filteredEntries.getPredicate();
            filteredEntries = new FilteredList<>(entries);
            filteredEntries.setPredicate(filter);
            overtimeTv.setItems(filteredEntries);
            model.save(rejected);
        } else {
            for (OvertimeEntry entry: rejected) {
                entry.setStatus(true);
            }
        }
    }

    @FXML
    private void onFilterAction() {
        boolean status = filterBtn.isSelected();

        if (status) {
            LocalDate filterDate = datePicker.getValue();
            if (filterDate == null) {
                filteredEntries.setPredicate(null);
                updateCheckAll();
                return;
            }
            String dateString = filterDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            Predicate<OvertimeEntry> filter = entry -> entry.getDateString().equals(dateString);
            filteredEntries.setPredicate(filter);
            updateCheckAll();
            System.out.println(filteredEntries.size());
        } else {
            filteredEntries.setPredicate(null);
            updateCheckAll();
        }
    }

    private void updateCheckAll() {
        checkAllBtn.setText("Check All");
        for (OvertimeEntry entry: filteredEntries) {
            if (entry.getStatus()) {
                checkAllBtn.setText("Uncheck All");
                break;
            }
        }
    }
}