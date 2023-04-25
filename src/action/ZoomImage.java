package action;

import javafx.scene.image.ImageView;
import service.ChangeService;

public class ZoomImage {
    public static int changeNum=1;
    private ImageView imageView;
    public ZoomImage(ImageView imageView){
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
