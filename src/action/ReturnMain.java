package action;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import service.ChangeService;

public class ReturnMain {
    public ReturnMain(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/MainUI.fxml"));
            Parent root = (Parent)loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("view/iVCSS.css");
            ChangeService.stage.setScene(scene);
            ChangeService.stage.setTitle("iViewer1.0");
            ChangeService.stage.setResizable(false);
            ChangeService.stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
