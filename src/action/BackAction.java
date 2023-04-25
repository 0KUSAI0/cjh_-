package action;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import service.ChangeService;

import java.io.IOException;

public class BackAction {
    public BackAction(int howChange){
            ChangeService.howChange=howChange;
            Stage stage=new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ImageView.fxml"));
            Parent root = null;
            try {
                root = (Parent) loader.load();
                Scene scene = new Scene(root);
                scene.getStylesheets().add("view/iVCSS.css");
                ChangeService.stage.setScene(scene);
                ChangeService.stage.setTitle("iViewer_1.0");
                ChangeService.stage.setResizable(false);
                ChangeService.stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }
}
