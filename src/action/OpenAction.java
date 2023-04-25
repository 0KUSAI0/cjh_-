package action;

import controller.MainUIController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.Main;
import service.ChangeService;

import java.io.IOException;


public class OpenAction {
    public OpenAction(int howChange){
        if(ChangeService.selectedPictures==null||ChangeService.selectedPictures.size()==0){
            String text="没有选中照片";
            Button button =new Button(text);
            Pane root=new Pane(button);
            Scene scene=new Scene(root);
            //scene.getStylesheets().add("");
            Stage stage=new Stage();
            stage.setTitle("提醒");
            stage.setScene(scene);
            stage.show();
            return;//当当前没有选中图片结点时进行打开操作，则会进行提醒
        }else{
            Stage stage=new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/ImageView.fxml"));
            Parent root = null;
            try {
                ChangeService.howChange=howChange;
                root = (Parent) loader.load();
                Scene scene = new Scene(root);
                scene.getStylesheets().add("view/iVCSS.css");
                stage.setScene(scene);
                stage.setTitle("图片查看");
                stage.setResizable(true);
                ChangeService.stage=stage;
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }//当当前有选中的图片结点时，加载图片查看界面
    }


}
