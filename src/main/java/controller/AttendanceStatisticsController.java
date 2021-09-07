package controller;

import driver.Driver;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.AttendanceStatistics;
import wrapper.PerformanceWrapper;

import java.util.Date;

public class AttendanceStatisticsController extends Controller {
    private static final PseudoClass COLUMN_HOVER_PSEUDO_CLASS = PseudoClass.getPseudoClass("column-hover");

    @FXML
    private AnchorPane navBar_container;
    @FXML
    private TableView<PerformanceWrapper> attendanceTv;

    //table columns for tableview in AttendanceStatisticsController.java
    @FXML
    private TableColumn<PerformanceWrapper, Date> startdateTc, paydateTc;
    @FXML
    private TableColumn<PerformanceWrapper, String> nameTc, presentTc, absentTc,
            overtimeTc, lateTc, holidayTc, idTc;

    private AttendanceStatistics statistics;
    private ObservableList<PerformanceWrapper> entries = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        initCol(startdateTc, "dateStartString");
        initCol(paydateTc, "datePaidString");
        initCol(idTc, "employeeID");
        initCol(nameTc, "completeName");
        initCol(presentTc, "daysPresent");
        initCol(absentTc, "daysAbsent");
        initCol(overtimeTc, "minsOvertime");
        initCol(lateTc, "minsLate");
        initCol(holidayTc, "");
    }

    @Override
    public void update() {
        // load navigation bar
        if (navBar_container.getChildren().isEmpty()) {
            navBar_container.getChildren().add(Driver.getScreenController().getNavBar());
        }

        statistics = new AttendanceStatistics();
        entries.setAll(statistics.getStatistics());
        attendanceTv.setItems(entries);
    }

    private <T> void initCol(TableColumn<PerformanceWrapper, T> col, String tag) {
        col.setCellValueFactory(new PropertyValueFactory<>(tag));
        col.setReorderable(false);

        // hover property
        BooleanProperty columnHover = new SimpleBooleanProperty();

        col.setCellFactory(column -> {
            TableCell<PerformanceWrapper, T> cell = new TableCell<>();

            if (!tag.isEmpty()) {
                cell.textProperty().bind(Bindings.createStringBinding(() -> cell.isEmpty() ? "" : String.format("%s", cell.getItem())
                        , cell.itemProperty(), cell.emptyProperty()));
            }
            cell.hoverProperty().addListener((obs, wasHovered, isNowHovered) -> columnHover.set(isNowHovered));

            columnHover.addListener((obs, columnWasHovered, columnIsNowHovered) ->
                    cell.pseudoClassStateChanged(COLUMN_HOVER_PSEUDO_CLASS, columnIsNowHovered)
            );
            return cell;
        });
    }
}