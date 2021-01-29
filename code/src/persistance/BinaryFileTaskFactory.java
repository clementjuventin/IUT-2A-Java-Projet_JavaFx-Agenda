package persistance;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import model.metier.Task;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class BinaryFileTaskFactory implements TaskFactory{
    public final String TASKFILE_PATH = "./allTasks";

    //Sauvegarde une Map de task en binaire
    public void saveTasks(HashMap<LocalDate, ListProperty<Task>> allTasks){
        HashMap<LocalDate, List<Task>> hm = new HashMap<>();
        allTasks.forEach((localDate, list) -> hm.put(localDate,new ArrayList<>(list.getValue())));
        try{
            ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(TASKFILE_PATH));
            writer.writeObject(hm);
        } catch (IOException ignored) {

        }
    }
    //Lance la map qui a été enregistrée
    public HashMap<LocalDate, ListProperty<Task>> readTasks(){
        HashMap<LocalDate, List<Task>> allTasks = null;
        try{
            ObjectInputStream reader = new ObjectInputStream(new FileInputStream(TASKFILE_PATH));
            allTasks = (HashMap<LocalDate, List<Task>>) reader.readObject();
        } catch (IOException e) {
            allTasks = new HashMap<>();
        } catch (ClassNotFoundException ignored) {
        }
        HashMap<LocalDate, ListProperty<Task>> retTasks = new HashMap<>();
        assert allTasks != null;
        allTasks.forEach((localDate, task) -> retTasks.put(localDate,new SimpleListProperty<>(FXCollections.observableArrayList(task))));
        return retTasks;
    }
}
