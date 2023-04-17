package action;

import controller.MainUIController;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import model.PictureNode;

public class CopyAction {
    public MainUIController mainUIController;
    public CopyAction(MainUIController mainUIController){
        this.mainUIController=mainUIController;
        if(mainUIController.getSelectedPictures().size()<=0){
            return;
        }
        if(mainUIController.getCutedPictures().size()>0){
            for(PictureNode pNode: mainUIController.getCutedPictures()){
                pNode.getImageView().setEffect(null);
            }
            mainUIController.clearCutedPictures();
        }

        Clipboard clipboard=Clipboard.getSystemClipboard();
        ClipboardContent clipboardContent=new ClipboardContent();
        clipboard.clear();

        for(PictureNode pNode: mainUIController.getSelectedPictures()){
            mainUIController.getSelectedPictureFiles().add(pNode.getImageFile());
        }
        clipboardContent.putFiles(mainUIController.getSelectedPictureFiles());
        clipboard.setContent(clipboardContent);
        mainUIController.getSelectedPictureFiles().clear();
        clipboard=null;
        clipboardContent=null;

        /*
        if(PictureNode.getSelectedPictures().size()<=0){
            return;
        }
        if(PictureNode.getCutedPictures().size()>0){
            for(PictureNode pNode: PictureNode.getCutedPictures()){
                pNode.getImageView().setEffect(null);
            }
            PictureNode.clearCutedPictures();
        }

        Clipboard clipboard=Clipboard.getSystemClipboard();
        ClipboardContent clipboardContent=new ClipboardContent();
        clipboard.clear();

        for(PictureNode pNode: PictureNode.getSelectedPictures()){
            PictureNode.getSelectedCopyPictureFiles().add(pNode.getImageFile());
        }
        clipboardContent.putFiles(PictureNode.getSelectedCopyPictureFiles());
        clipboard.setContent(clipboardContent);
        PictureNode.getSelectedCopyPictureFiles().clear();
        clipboard=null;
        clipboardContent=null;
        */
    }
}
