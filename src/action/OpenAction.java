package action;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import service.ChangeService;

import java.io.IOException;


public class OpenAction {
    public OpenAction(int howChange){
        if(ChangeService.files==null){
            String text="没有选中照片";
            Button button =new Button(text);
            Pane root=new Pane(button);
            Scene scene=new Scene(root);
            //scene.getStylesheets().add("");
            Stage stage=new Stage();
            stage.setTitle("提醒");
            stage.setScene(scene);
            stage.show();
            return;
        }else{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/PictureView.fxml"));
            Parent root = null;
            try {
                ChangeService.howChange=howChange;
                root = (Parent) loader.load();
                Scene scene = new Scene(root);
                scene.getStylesheets().add("view/iVCSS.css");
                ChangeService.stage.setScene(scene);
                ChangeService.stage.setTitle("iViewer_1.0");

                // ChangeService.stage.setResizable(false);
                ChangeService.stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }


}
