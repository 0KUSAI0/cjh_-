package action;

import javafx.scene.image.ImageView;

public class RotateAction {
    private ImageView imageView;
    public RotateAction(ImageView imageView){
        this.imageView=imageView;
        imageView.setRotate((imageView.getRotate()+90)%360);
        //每次增加图片角度90度，并取模360，从而完成图片的旋转
    }
}
