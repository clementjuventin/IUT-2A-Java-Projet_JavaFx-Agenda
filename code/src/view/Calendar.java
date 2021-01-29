package view;

import generate.GenerateGrid;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.CalendarModel;
import model.metier.Task;

import java.time.LocalDate;

import static java.lang.Integer.parseInt;

public class Calendar {
    //La tâche faite par l'utilisateur
    private Task task = null;

    public Task getTask() {
        return task;
    }
    private void setTask(Task task) {
        this.task = task;
    }

    @FXML
    private ComboBox<String> monthChoice;
    @FXML
    private ComboBox<String> yearsComboBox;
    @FXML
    private TextField title;
    @FXML
    private TextArea description;
    @FXML
    private Button submit;
    @FXML
    private GridPane dayGrid;
    @FXML
    public void initialize(){
        CalendarModel cm = new CalendarModel(LocalDate.now().getYear());

        monthChoice.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                cm.setSelectedMonth(monthChoice.getValue());
            }
        });
        yearsComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                cm.setSelectedYear((parseInt(yearsComboBox.getValue())));
            }
        });
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Note: Binder la task sur les propriétés de CalendarModel
                Task task = cm.getTask();
                if(task!=null){
                    setTask(cm.getTask());
                    ((Stage)((Button) actionEvent.getSource()).getScene().getWindow()).close();
                }
            }
        });

        monthChoice.itemsProperty().bind(cm.monthProperty());
        //Note: Risque de créer des bugs si l'année courrante n'est pas l'année de la plus vieille tâche
        //Faire un binding sur la propriété sélectionnée
        monthChoice.setValue(String.valueOf(cm.monthOptions.get(0)));

        yearsComboBox.itemsProperty().bindBidirectional(cm.yearsProperty());
        //Note: Idem
        yearsComboBox.setValue(String.valueOf(cm.getYears().get(0)));

        dayGrid = GenerateGrid.generateDayGrid(dayGrid, (double) 40, cm);

        title.textProperty().bindBidirectional(cm.getTitle());
        description.textProperty().bindBidirectional(cm.getDescription());

        description.setWrapText(true);
    }
}
