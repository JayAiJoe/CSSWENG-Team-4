package controller;

import dao.ColaPOJO;
import driver.Driver;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.EmployeeForm;
import wrapper.EmployeeWrapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.function.Predicate;

public class COLAController extends Controller {
    private static final PseudoClass COLUMN_HOVER_PSEUDO_CLASS = PseudoClass.getPseudoClass("column-hover");

    @FXML
    private AnchorPane navBar_container;

    @FXML
    private ChoiceBox<String> companyCb;

    @FXML
    private TextField nameTf;

    @FXML
    private TableView<EmployeeWrapper> employeesTv;
    @FXML
    private TableColumn<EmployeeWrapper, String> idTc, nameTc, companyTc;
    @FXML
    private TableColumn<EmployeeWrapper, Integer> buttonTc;

    private EmployeeForm employeeForm;
    private FilteredList<EmployeeWrapper> filteredEntries;
    private Predicate<EmployeeWrapper> nameFilter = entry -> true, companyFilter = entry -> true;

    @Override
    public void update() {
        // load navigation bar
        if (navBar_container.getChildren().isEmpty()) {
            navBar_container.getChildren().add(Driver.getScreenController().getNavBar());
        }

        employeeForm = new EmployeeForm();
        employeeForm.initializeCola();
        ObservableList<EmployeeWrapper> entries = FXCollections.observableArrayList();
        entries.setAll(employeeForm.getEmployees());
        filteredEntries = new FilteredList<>(entries);
        employeesTv.setItems(filteredEntries);
    }

    @FXML
    public void initialize(){
        disableReorder();

        initCol(idTc, "employeeID");
        initCol(nameTc, "completeName");
        initCol(companyTc, "companyFull");
        buttonTc.setCellValueFactory(new PropertyValueFactory<>("cola"));
        buttonTc.setCellFactory(t -> {
            ChoiceBox<String> choiceBox = new ChoiceBox<>();
            ObservableList<String> values = FXCollections.observableArrayList(
                    "Full day", "Half day", "None");
            choiceBox.setItems(values);
            choiceBox.setPrefWidth(70);
            
            TableCell<EmployeeWrapper, Integer> cell = new TableCell<>() {
                @Override
                public void updateItem(Integer item, boolean empty) {
                    if (empty) {
                        setGraphic(null);
                    } else {
                        switch (item) {
                            case 0: choiceBox.setValue("None"); break;
                            case 5: choiceBox.setValue("Half day"); break;
                            default: choiceBox.setValue("Full day"); break;
                        }
                        setGraphic(choiceBox);
                    }
                }
            };

            choiceBox.setOnAction(e -> {
                String value = choiceBox.getValue();
                EmployeeWrapper employee = cell.getTableRow().getItem();
                if (employee == null) {
                    return;
                }
                if (value.equals("Full day")) {
                    employee.setCola(10);
                } else if (value.equals("Half day")) {
                    employee.setCola(5);
                } else {
                    employee.setCola(0);
                }
            });

            cell.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            cell.setAlignment(Pos.CENTER);
            return cell;
        });
    }

    private <T> void initCol(TableColumn<EmployeeWrapper, T> col, String tag) {
        col.setCellValueFactory(new PropertyValueFactory<>(tag));

        // hover property
        BooleanProperty columnHover = new SimpleBooleanProperty();

        col.setCellFactory(column -> {
            TableCell<EmployeeWrapper, T> cell = new TableCell<>();

            if (!tag.isEmpty()) {
                cell.textProperty().bind(Bindings.createStringBinding(() -> cell.isEmpty() ? "" : String.format("%s", cell.getItem()),
                        cell.itemProperty(), cell.emptyProperty()));
            }
            cell.hoverProperty().addListener((obs, wasHovered, isNowHovered) -> columnHover.set(isNowHovered));

            columnHover.addListener((obs, columnWasHovered, columnIsNowHovered) ->
                    cell.pseudoClassStateChanged(COLUMN_HOVER_PSEUDO_CLASS, columnIsNowHovered)
            );
            return cell;
        });
    }

    private void disableReorder() {
        idTc.setReorderable(false);
        nameTc.setReorderable(false);
        companyTc.setReorderable(false);
        buttonTc.setReorderable(false);
    }

    @FXML
    private void onFilterNameAction() {
        nameFilter = entry -> entry.getCompleteName().toLowerCase()
                .contains(nameTf.getText().toLowerCase());
        filteredEntries.setPredicate(nameFilter.and(companyFilter));
    }

    @FXML
    private void onFilterCompanyAction() {
        if (companyCb.getValue().equals("All")) {
            companyFilter = entry -> true;
        } else {
            companyFilter = entry ->
                    entry.getCompanyFull().equals(companyCb.getValue());
        }
        filteredEntries.setPredicate(companyFilter.and(nameFilter));
    }

    @FXML
    private void onSaveAction() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Save changes?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ArrayList<ColaPOJO> colas = new ArrayList<>();
            Date date = new Date();
            for (EmployeeWrapper employee: filteredEntries) {
                colas.add(new ColaPOJO(employee.getEmployeeID(), date, employee.getCola()));
            }
            employeeForm.updateCola(colas, date);

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setGraphic(null);
            alert.setTitle("Success");
            alert.setContentText("Cola has been updated");

            alert.showAndWait();
        }
    }
}