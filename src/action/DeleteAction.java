package action;

import controller.MainUIController;
import main.Main;
import model.MyAlert;
import model.PictureNode;

public class DeleteAction {
    MainUIController mainUIController;
    public DeleteAction(MainUIController mainUIController){
        this.mainUIController=mainUIController;
        if(PictureNode.getSelectedPictures().size()<=0){
            return;
        }
        if(PictureNode.getCutedPictures().size()>0){
            for(PictureNode pNode: PictureNode.getCutedPictures()){
                pNode.getImageView().setEffect(null);
            }
            PictureNode.clearCutedPictures();
        }
        if(MyAlert.showAlert("是否删除这些图片？","", Main.mainStage)){
            for(PictureNode pNode: PictureNode.getSelectedPictures()){
                mainUIController.getFlowPane().getChildren().remove(pNode);
                pNode.getImageFile().delete();
            }
        }
        PictureNode.getSelectedCopyPictureFiles().clear();
        PictureNode.clearSelected();
    }
}
