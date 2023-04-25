package action;

import javafx.scene.image.ImageView;
import service.ChangeService;

public class ResetAction {
    private ImageView imageView;
    public ResetAction(ImageView imageView){
        this.imageView = imageView;
        imageView.setFitHeight(ChangeService.originHeight);
        imageView.setFitWidth(ChangeService.originWidth);
        ZoomImage.changeNum=1;
    }
}
