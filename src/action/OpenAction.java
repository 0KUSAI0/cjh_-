package action;

import controller.MainUIController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.Main;
import service.ChangeService;

import java.io.IOException;


public class OpenAction {
    private Alert infoAlert;
    public OpenAction(int howChange){
        if(ChangeService.selectedPictures==null||ChangeService.selectedPictures.size()==0){
            infoAlert=new Alert(Alert.AlertType.INFORMATION);
            infoAlert.setTitle("打开结果");
            infoAlert.setContentText("您还未选择图片");
            infoAlert.show();
            return;//当前没有选中图片结点时进行打开操作，则会进行提醒
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
        }//当前有选中的图片结点时，加载图片查看界面
    }


}
