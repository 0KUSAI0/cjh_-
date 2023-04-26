package action;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import service.ChangeService;

import java.io.IOException;

public class PPTAction {
    private Stage stage;
    private Alert infoAlert;
    public PPTAction(int howChange,boolean flag){
        if(ChangeService.files==null||ChangeService.files.size()==0){
            infoAlert=new Alert(Alert.AlertType.INFORMATION);
            infoAlert.setTitle("点击结果");
            infoAlert.setContentText("当前文件夹下没有图片哦");
            infoAlert.show();
            return;//当当前文件夹下没有图片结点时
        }else{
            try {
                ChangeService.flag=flag;
                FXMLLoader loader=new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/PPTUI.fxml"));
                Parent root = (Parent) loader.load();
                Scene scene=new Scene(root);
                scene.getStylesheets().add("view/iVCSS.css");
                if(howChange==0){
                    stage=new Stage();
                    stage.setScene(scene);
                    stage.setTitle("幻灯片");
                    stage.setResizable(true);
                    ChangeService.stage=stage;
                    stage.show();
                }else{
                    ChangeService.stage.setScene(scene);
                    ChangeService.stage.setTitle("幻灯片");
                    ChangeService.stage.show();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
