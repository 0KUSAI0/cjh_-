package action;

import javafx.scene.image.ImageView;
import service.ChangeService;

public class ResetAction {
    private ImageView imageView;
    public ResetAction(ImageView imageView){
        this.imageView = imageView;
        imageView.setFitHeight(ChangeService.originHeight);
        imageView.setFitWidth(ChangeService.originWidth);
        //将当前图片查看界面中图片的宽高设置会存放在ChangeService的宽高从而实现图片大小重置
        ZoomImage.changeNum=1;
    }
}
