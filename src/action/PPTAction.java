package action;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import service.ChangeService;

import java.io.IOException;

public class PPTAction {
    private Stage stage;
    public PPTAction(int howChange,boolean flag){
        if(ChangeService.files==null){
            String text="没有选中图片";
            Button button =new Button(text);
            Pane root= new Pane(button);
            Scene scene=new Scene(root);
            scene.getStylesheets().add("view/iVCSS.css");
            stage=null;
            stage=new Stage();
            stage.setTitle("提醒");
            stage.setScene(scene);
            stage.show();
            return;
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
                //ChangeService.stage.setScene(scene);
                //ChangeService.stage.setTitle("幻灯片");
                //ChangeService.stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
