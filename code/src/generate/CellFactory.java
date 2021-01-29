package generate;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.metier.TimeZoneHourSet;

public class CellFactory {
    public static void setTableView(TableView tv){
        setTableAppearance(tv);

        ObservableList<TimeZoneHourSet> timeZoneHourSets = Timer.getTimerProperty();

        TableColumn<String, String> timeZone = new TableColumn("TimeZone");
        TableColumn<SimpleStringProperty, String> hourProperty = new TableColumn("Heure");

        tv.getColumns().add(timeZone);
        tv.getColumns().add(hourProperty);

        timeZone.setCellValueFactory(new PropertyValueFactory<>("timeZone"));
        hourProperty.setCellValueFactory(new PropertyValueFactory<>("hourProperty"));

        tv.setItems(timeZoneHourSets);
    }

    private static void setTableAppearance(TableView tv){
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv.setMinHeight(100);
        tv.setMaxHeight(100);
    }
}
