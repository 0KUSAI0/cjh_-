package action;

import javafx.geometry.NodeOrientation;
import javafx.scene.image.ImageView;

public class UpsideDownAction {
    private ImageView imageView;
    public UpsideDownAction(ImageView imageView){
        this.imageView = imageView;
        if (imageView.getNodeOrientation().name().equals("RIGHT_TO_LEFT")) {
            imageView.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        } else {
            imageView.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        }
    }
}
