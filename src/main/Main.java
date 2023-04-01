package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import service.ChangeService;


//主函数
public class Main extends Application {
    public static Window mainStage;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        ChangeService.stage=stage;
        mainStage=stage;
        FXMLLoader loader=new FXMLLoader();//载入主页面的FXML
        loader.setLocation(getClass().getResource("/view/MainUI.fxml"));
        Parent root=(Parent) loader.load();
        Scene scene=new Scene(root);
        stage.setTitle("nihao");
        stage.setScene(scene);
        stage.show();
    }
}
