package action;

import controller.BeautyController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import service.ChangeService;

public class BeautyAction {
    public BeautyAction(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/Beauty.fxml"));
            //加载图片美化界面
            Parent root = (Parent)loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("view/iVCSS.css");
            ChangeService.stage.setScene(scene);
            ChangeService.stage.setTitle("图片美化");
            ChangeService.stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
