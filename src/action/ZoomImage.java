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
        imageView.setFitWidth(ChangeService.originWidth *(changeNum*0.2+1));
        imageView.setFitHeight(ChangeService.originHeight*(changeNum*0.2+1));
        //在当前图片的宽高下进行放大从而实现图片整体放大
        imageView.setPreserveRatio(true);//同时保持图片的比率，放置图片被拉伸
    }

    public void smallPicture(){
        changeNum -=1;
        imageView.setFitWidth(ChangeService.originWidth*(changeNum*0.2+1));
        imageView.setFitHeight(ChangeService.originHeight*(changeNum*0.2+1));
        //在当前图片的宽高下进行缩小从而实现图片整体缩小
        imageView.setPreserveRatio(true);//同时保持图片的比率，放置图片被压缩
    }

}
