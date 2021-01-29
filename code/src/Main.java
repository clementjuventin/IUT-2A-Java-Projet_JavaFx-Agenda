import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import persistance.BinaryFileTaskFactory;
import view.MainWindow;

public class Main extends Application{

    private FXMLLoader loader;

    @Override
    public void start(Stage stage) throws Exception {
        loader = new FXMLLoader(getClass().getResource("/vue/MainWindow.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.setTitle("Todolist");
        stage.show();
    }
    @Override
    public void stop(){
        new BinaryFileTaskFactory().saveTasks(((MainWindow) loader.getController()).getTm().getAllTasks());
    }
}
