package action;

import controller.ImageViewController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import service.ChangeService;

public class SwitchPicture {
    private ImageView imageView;
    public static int index;
    public SwitchPicture(ImageView imageView,int index){
        this.index=index;
        this.imageView=imageView;
    }

    public int nextPicture(){
        --index;
        if(index<0){
            String text = "这是第一张图片";
            Button button = new Button(text);
            Pane root = new Pane(button);
            Scene scene = new Scene(root);
            scene.getStylesheets().add("view/iVCSS.css");
            Stage Stage = null;
            Stage = new Stage();
            Stage.setTitle("提示");
            Stage.setScene(scene);
            Stage.show();
            return 0;
        }
        imageView.setImage(ChangeService.files.get(index).getImage());
        return index;
    }
    public int prePicture(){
        ++index;
        if(index>=ChangeService.files.size()){
            String text = "这是最后一张图片";
            Button button = new Button(text);
            Pane root = new Pane(button);
            Scene scene = new Scene(root);
            scene.getStylesheets().add("view/iVCSS.css");
            Stage Stage = null;
            Stage = new Stage();
            Stage.setTitle("提示");
            Stage.setScene(scene);
            Stage.show();
            return index-1;
        }
        imageView.setImage(ChangeService.files.get(index).getImage());
        return index;
    }
}
