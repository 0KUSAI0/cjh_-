package action;

import javafx.scene.image.ImageView;

public class RotateAction {
    private ImageView imageView;
    public RotateAction(ImageView imageView){
        this.imageView=imageView;
        imageView.setRotate((imageView.getRotate()+90)%360);
    }
}
