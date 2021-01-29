package persistance;

import javafx.beans.property.ListProperty;
import model.metier.Task;

import java.time.LocalDate;
import java.util.HashMap;

public interface TaskFactory {
    void saveTasks(HashMap<LocalDate, ListProperty<Task>> allTasks);
    HashMap<LocalDate, ListProperty<Task>> readTasks();
}
