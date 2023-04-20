package action;

import controller.ImageViewController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import service.ChangeService;

public class ZoomPicture {
    public static int changeNum=1;
    private ImageView imageView;
    public ZoomPicture(ImageView imageView){
        this.imageView=imageView;
    }
    public void enlargePicture(){
        changeNum+=1;
        System.out.println(changeNum);
        imageView.setFitWidth(ChangeService.originWidth *(changeNum*0.2+1));
        imageView.setFitHeight(ChangeService.originHeight*(changeNum*0.2+1));
        imageView.setPreserveRatio(true);
    }

    public void smallPicture(){
        changeNum -=1;
        imageView.setFitWidth(ChangeService.originWidth*(changeNum*0.2+1));
        imageView.setFitHeight(ChangeService.originHeight*(changeNum*0.2+1));
        imageView.setPreserveRatio(true);
    }

}
