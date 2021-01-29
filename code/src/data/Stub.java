package data;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import model.metier.Task;
import TaskManager.TaskManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Stub {
    public static TaskManager createTaskManager() {
        HashMap<LocalDate, List<Task>> hm = new HashMap<LocalDate, List<Task>>();
        List<Task> list = new ArrayList<>();
        LocalDate date = LocalDate.now();
        list.add(new Task("Tache 1","Description tache 1", date));
        list.add(new Task("Tache 2","Description tache 2", date));
        list.add(new Task("Tache 3","Description tache 3", date));
        hm.put(date, list);

        list = new LinkedList<>();
        date = LocalDate.of(2021,6,28);
        list.add(new Task("Tache 4","Description tache 4", date));
        hm.put(date, list);

        HashMap<LocalDate, ListProperty<Task>> retTasks = new HashMap<>();
        hm.forEach((localDate, task) -> retTasks.put(localDate,new SimpleListProperty<>(FXCollections.observableArrayList(task))));

        return new TaskManager(retTasks);
    }
}
