package action;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import service.ChangeService;



public class OpenAction {
    public OpenAction(){
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
            try{

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


}
