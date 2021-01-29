package view;

import generate.CellFactory;
import generate.GenerateGrid;
import generate.Timer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.CalendarModel;
import model.TaskModel;
import model.metier.Task;
import TaskManager.TaskManager;
import persistance.BinaryFileTaskFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Integer.parseInt;

public class MainWindow {
    private TaskManager tm = null;
    @FXML
    private ComboBox<String> yearCb;
    @FXML
    private ComboBox<String> monthCb;
    @FXML
    private Label currentDateLabel;
    @FXML
    private ListView todolist;
    @FXML
    private GridPane dayGrid;
    @FXML
    private Button addBtn;
    @FXML
    private Label taskTitle;
    @FXML
    private Label taskDescription;
    @FXML
    private Button del;
    @FXML
    private Button done;
    @FXML
    private TableView tableView;
    @FXML
    private Label hiddenLabel;

    public TaskManager getTm() {
        return tm;
    }

    public void initialize(){
        hiddenLabel.setVisible(false);
        tm = new TaskManager(new BinaryFileTaskFactory().readTasks());
        CalendarModel cm = new CalendarModel(TaskModel.getOldestYear(tm));

        monthCb.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                cm.setSelectedMonth(monthCb.getValue());
            }
        });
        yearCb.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                cm.setSelectedYear((parseInt(yearCb.getValue())));
            }
        });
        cm.getCurrentDateLabel().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                todolist.itemsProperty().bind(tm.allTasksProperty(cm.getDate()));
                todolist.getSelectionModel().selectFirst();
            }
        });
        addBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                openWindowAddTask(actionEvent, tm, cm);
            }
        });
        todolist.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                Task tsk = (Task) t1;
                try{
                    taskTitle.setText(tsk.getTitle());
                    taskDescription.setText(tsk.getDescription());
                    del.setVisible(true);
                    done.setVisible(true);
                }
                catch (NullPointerException e){
                    if(todolist.getSelectionModel().getSelectedItem()==null){
                        taskTitle.setText("");
                        taskDescription.setText("");
                        del.setVisible(false);
                        done.setVisible(false);
                    }
                }
            }
        });
        del.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Task toDelTask = (Task) todolist.getSelectionModel().selectedItemProperty().get();
                TaskModel.delTask(tm,toDelTask);
                todolist.getSelectionModel().selectFirst();
            }
        });
        done.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Task toDownTask = (Task) todolist.getSelectionModel().selectedItemProperty().get();
                TaskModel.downTask(tm,toDownTask);
            }
        });

        monthCb.itemsProperty().bind(cm.monthProperty());
        //Note: Cf Calendar pour remarque
        monthCb.setValue(String.valueOf(cm.monthOptions.get(0)));

        yearCb.itemsProperty().bindBidirectional(cm.yearsProperty());
        //Note: Cf Calendar pour remarque
        yearCb.setValue(String.valueOf(cm.getYears().get(0)));

        dayGrid = GenerateGrid.generateDayGrid(dayGrid, (double) 40, cm);
        CellFactory.setTableView(tableView);

        currentDateLabel.textProperty().bind(cm.currentDateLabelProperty());
        todolist.itemsProperty().bind(tm.allTasksProperty(cm.getDate()));
        //Note: Créer une méthode pour set todolist
        todolist.setStyle("-fx-background-color: #feded3;");
        todolist.getSelectionModel().selectFirst();
    }
    //Crée une window lorsque l'utilisateur appuis sur ajouter
    private void openWindowAddTask(ActionEvent actionEvent, TaskManager tm, CalendarModel cm) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vue/Calendar.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 280,500);
            Stage stage = new Stage();
            stage.setTitle("Ajouter une nouvelle tâche");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initOwner(yearCb.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
            try {
                TaskModel.addTask(tm,((Calendar) fxmlLoader.getController()).getTask());
                LocalDate dte = cm.getDate();
                //Note: Cf INITIALIZE
                todolist.itemsProperty().bind(tm.allTasksProperty(LocalDate.of(dte.getYear(), dte.getMonth(), cm.getSelectedDay().get())));
                todolist.getSelectionModel().selectFirst();
            }
            catch (Exception e){}
        }
        catch (IOException e)  {
            //Note: Trouver une explication à ce que c'est qu'un logger
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Erreur d'ouverture de la fenetre");
        }
    }
}
