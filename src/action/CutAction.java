package action;

import controller.MainUIController;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import model.PictureNode;
public class CutAction {
    private MainUIController mainUIController;
    public CutAction(MainUIController mainUIController){
        this.mainUIController = mainUIController;
        if(mainUIController.getSelectedPictures().size()==0){
            return;
        }//当当前没有选中的图片节点直接返回
        if(mainUIController.getCutedPictures().size()>0){
            for(PictureNode pNode : mainUIController.getCutedPictures()){
                pNode.getImageView().setEffect(null);
            }
            mainUIController.getCutedPictures().clear();
        }//当当前文件夹中有被剪切的图片结点，取消其剪切效果
        Clipboard clipboard=Clipboard.getSystemClipboard();
        ClipboardContent content=new ClipboardContent();
        clipboard.clear();//获取系统剪切板
        for(PictureNode pNode : mainUIController.getSelectedPictures()){
            mainUIController.getSelectedPictureFiles().add(pNode.getImageFile());
            pNode.getImageView().setEffect(new ColorAdjust(0,0,0.5,0));
            mainUIController.addCutedPictures(pNode);
        }//遍历被选中的图片节点，加入到临时存放被选中图片结点的文件数组中，同时为这些被剪切的图片结点添加效果表示其被剪切
        //同时将其存放入被剪切图片数组中
        content.putFiles(mainUIController.getSelectedPictureFiles());
        clipboard.setContent(content);//将临时存放被选中图片结点的文件数组放入到系统剪切板中完成剪切
        clipboard=null;
        content=null;
    }

}
