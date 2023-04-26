package action;

import controller.ImageViewController;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import service.ChangeService;

public class SwitchPicture {
    private Alert infoAlert;
    private ImageView imageView;
    private int index;
    public SwitchPicture(ImageView imageView,int index){
        this.index=index;
        this.imageView=imageView;
    }

    public int prePicture(){
        --index;
        if(index<0){
            //当图片索引小于0时说明此时为第一张图片无法再切换到前一张
            infoAlert=new Alert(Alert.AlertType.INFORMATION);
            infoAlert.setTitle("点击结果");
            infoAlert.setContentText("这是第一张图片");
            infoAlert.show();
            return 0;//并返回现在的索引应为0
        }
        //如果可以切换到前一张，则将当前图片查看界面中的图片设置为前一张图片
        imageView.setImage(ChangeService.files.get(index).getImage());
        return index;
    }
    public int nextPicture(){
        ++index;
        if(index>=ChangeService.files.size()){
            //当图片索引大于当前文件中的图片文件数量时说明此时为最后一张图片无法再切换到下一张
            infoAlert=new Alert(Alert.AlertType.INFORMATION);
            infoAlert.setTitle("点击结果");
            infoAlert.setContentText("这是最后一张图片");
            infoAlert.show();
            return index-1;//返回当前索引
        }
        //如果可以切换到下一张，则将当前图片查看界面中的图片设置为下一张图片
        imageView.setImage(ChangeService.files.get(index).getImage());
        return index;
    }
}
