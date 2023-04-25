package action;

import controller.MainUIController;
import main.Main;
import model.MyAlert;
import model.PictureNode;

public class DeleteAction {
    private  MainUIController mainUIController;
    public DeleteAction(MainUIController mainUIController){
        this.mainUIController=mainUIController;
        if(mainUIController.getSelectedPictures().size()<=0){
            return;
        }//当当前没有选中的图片节点直接返回
        if(mainUIController.getCutedPictures().size()>0){
            for(PictureNode pNode: mainUIController.getCutedPictures()){
                pNode.getImageView().setEffect(null);
            }
            mainUIController.clearCutedPictures();
        }//当当前文件夹中有被剪切的图片结点，取消其剪切效果
        if(MyAlert.showAlert("是否删除这些图片？","", Main.mainStage)){
            for(PictureNode pNode: mainUIController.getSelectedPictures()){
                mainUIController.getFlowPane().getChildren().remove(pNode);
                System.out.println(pNode.getImageFile().getAbsoluteFile());
                pNode.getImageFile().delete();
            }
        }//生成自定义提醒框，当用户确认删除则删除被选中的图片节点
        mainUIController.getSelectedPictureFiles().clear();
        //由于被选中图片结点删除，所以清空存放选中图片结点的数组
        mainUIController.clearSelected();
    }
}
