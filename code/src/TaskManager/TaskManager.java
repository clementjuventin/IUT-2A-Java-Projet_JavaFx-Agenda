package TaskManager;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import model.metier.Task;

import java.time.LocalDate;
import java.util.HashMap;

public class TaskManager {
    //Toutes les tâches de l'utilisateur
    private HashMap<LocalDate, ListProperty<Task>> allTasks;

    public HashMap<LocalDate, ListProperty<Task>> getAllTasks() {
        return allTasks;
    }

    public TaskManager(HashMap<LocalDate, ListProperty<Task>> allTasks) {
        this.allTasks = allTasks;
    }

    //Retourne la propriété des tâches pour une journée donnée, si la journée n'existe pas on la crée
    public ListProperty<Task> allTasksProperty(LocalDate ld){
        ListProperty<Task> lp = this.allTasks.get(ld);
        if(lp==null){
            lp = new SimpleListProperty<>(FXCollections.observableArrayList());
            allTasks.put(ld, lp);
        }
        return lp;
    }
}
