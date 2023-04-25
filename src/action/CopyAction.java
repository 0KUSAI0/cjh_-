package action;

import controller.MainUIController;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import model.PictureNode;

public class CopyAction {
    private MainUIController mainUIController;
    public CopyAction(MainUIController mainUIController){
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

        Clipboard clipboard=Clipboard.getSystemClipboard();
        ClipboardContent clipboardContent=new ClipboardContent();
        clipboard.clear();//获取系统剪切板

        for(PictureNode pNode: mainUIController.getSelectedPictures()){
            mainUIController.getSelectedPictureFiles().add(pNode.getImageFile());
        }//遍历被选中的图片节点，加入到临时存放被选中图片结点的文件数组中
        clipboardContent.putFiles(mainUIController.getSelectedPictureFiles());//将临时存放被选中图片结点的文件数组放入到系统剪切板中完成复制
        clipboard.setContent(clipboardContent);
        mainUIController.getSelectedPictureFiles().clear();//清空临时存放被选中图片结点的文件数组
        clipboard=null;
        clipboardContent=null;
    }
}
