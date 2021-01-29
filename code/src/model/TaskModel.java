package model;

import TaskManager.TaskManager;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import model.metier.Task;
import java.time.LocalDate;

public class TaskModel {

    //Permet d'ajouter une tâche
    public static void addTask(TaskManager tm,Task task){
        ListProperty<Task> lp = tm.getAllTasks().get(task.getDate());
        if(lp==null){
            lp = new SimpleListProperty<>(FXCollections.observableArrayList(task));
            tm.getAllTasks().put(task.getDate(),lp);
        }
        else {
            lp.get().add(task);
        }
    }
    //Permet de noter une tâche comme réalisée
    public static void downTask(TaskManager tm,Task task){
        try{
            ListProperty<Task> lp = tm.getAllTasks().get(task.getDate());
            lp.get().set(lp.get().indexOf(task),task);
            task.setDone(true);
        }
        catch (Exception ignored){
        }
    }
    //Permet de supprimer une tâche
    public static void delTask(TaskManager tm,Task task){
        try{
            ListProperty<Task> lp = tm.getAllTasks().get(task.getDate());
            lp.get().remove(task);
        }
        catch (Exception ignored){
        }
    }
    //Retourne la plus vieille année qu'une tâche enregistrée puisse avoir
    public static int getOldestYear(TaskManager tm){
        if(tm.getAllTasks().size()==0){
            return LocalDate.now().getYear();
        }
        LocalDate min = LocalDate.MAX;
        for(LocalDate ld: tm.getAllTasks().keySet()){
            if(min.isAfter(ld)){
                min=ld;
            }
        }
        return min.getYear();
    }
}
