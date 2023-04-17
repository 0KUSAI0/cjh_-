package action;

import controller.MainUIController;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import model.PictureNode;
public class CutAction {
    public MainUIController mainUIController;
    public CutAction(MainUIController mainUIController){
        this.mainUIController = mainUIController;
        if(mainUIController.getSelectedPictures().size()==0){
            return;
        }
        if(mainUIController.getCutedPictures().size()>0){
            for(PictureNode pNode : mainUIController.getCutedPictures()){
                pNode.getImageView().setEffect(null);
            }
            mainUIController.getCutedPictures().clear();
        }
        Clipboard clipboard=Clipboard.getSystemClipboard();
        ClipboardContent content=new ClipboardContent();
        clipboard.clear();
        for(PictureNode pNode : mainUIController.getSelectedPictures()){
            mainUIController.getSelectedPictureFiles().add(pNode.getImageFile());
            pNode.getImageView().setEffect(new ColorAdjust(0,0,0.5,0));
            mainUIController.addCutedPictures(pNode);
        }
        content.putFiles(mainUIController.getSelectedPictureFiles());
        clipboard.setContent(content);
        clipboard=null;
        content=null;



        /*
        if(PictureNode.getSelectedPictures().size()==0){
            return;
        }
        if(PictureNode.getCutedPictures().size()>0){
            for(PictureNode pNode : PictureNode.getCutedPictures()){
                pNode.getImageView().setEffect(null);
            }
            PictureNode.getCutedPictures().clear();
        }
        Clipboard clipboard=Clipboard.getSystemClipboard();
        ClipboardContent content=new ClipboardContent();
        clipboard.clear();
        for(PictureNode pNode : PictureNode.getSelectedPictures()){
            PictureNode.getSelectedCopyPictureFiles().add(pNode.getImageFile());
            pNode.getImageView().setEffect(new ColorAdjust(0,0,0.5,0));
            PictureNode.addCutedPictures(pNode);
        }
        content.putFiles(PictureNode.getSelectedCopyPictureFiles());
        clipboard.setContent(content);
        clipboard=null;
        content=null;

         */
    }

}
