package controller;

import action.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.PictureNode;
import service.ChangeService;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class ImageViewController implements Initializable {
    private PictureNode pNode;
    public static int index;
    @FXML
    private ToolBar toolbar;

    @FXML
    private Button resetBtn;

    @FXML
    private Button enlargeBtn;

    @FXML
    private Button previousImageBtn;

    @FXML
    private Button pptBtn;

    @FXML
    private Button beautyBtn;

    @FXML
    private Button rotateBtn;

    @FXML
    private Button backBtn;

    @FXML
    private Button nextImageBtn;

    @FXML
    private ImageView imageView;

    @FXML
    private Button smallBtn;

    @FXML
    private Button UpsideDownBtn;
    @FXML
    void Press(ActionEvent event) {
        if (toolbar.isVisible()) {
            toolbar.setVisible(false);
        }else {
            toolbar.setVisible(true);
        }
    }

    @FXML
    void Back(ActionEvent event) {
        new ReturnMain();
    }

    @FXML
    void previousAction(ActionEvent event) {
       index=new SwitchPicture(imageView,index).nextPicture();
    }

    @FXML
    void nextAction(ActionEvent event) {
        index=new SwitchPicture(imageView,index).prePicture();
    }

    @FXML
    void PPTAction(ActionEvent event) {
        new PPTAction();
    }

    @FXML
    void beautyAction(ActionEvent event) {
        new BeautyAction();
    }

    @FXML
    void rotateAction(ActionEvent event) {
        new RotateAction(imageView);
    }

    @FXML
    void UpsideDown(ActionEvent event) {
        new UpsideDownAction(imageView);
    }

    @FXML
    void enlargeAction(ActionEvent event) {
        new ZoomPicture(imageView).enlargePicture();
    }

    @FXML
    void smallAction(ActionEvent event) {
        new ZoomPicture(imageView).smallPicture();
    }

    @FXML
    void resetAction(ActionEvent event) {
        new ResetAction(imageView);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initData();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initData() throws MalformedURLException {
        if(ChangeService.howChange==1){
            searchPicturePos();
        }

        pNode=ChangeService.files.get(index);
        Image image=new Image(pNode.getPictureFile().getImageFile().toURI().toURL().toString(),0,529,true,true,true);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setImage(image);

        ChangeService.origin=pNode.getImageView();
        ChangeService.change=pNode.getImageView();
        ChangeService.originHeight=imageView.getFitHeight();
        ChangeService.originWidth=imageView.getFitWidth();
        imageView.setPreserveRatio(true);
        toolbar.setVisible(true);
        enlargeBtn.setTooltip(new Tooltip("放大"));
        smallBtn.setTooltip(new Tooltip("缩小"));
        resetBtn.setTooltip(new Tooltip("重置"));
        rotateBtn.setTooltip(new Tooltip("旋转"));
        beautyBtn.setTooltip(new Tooltip("美化"));
        previousImageBtn.setTooltip(new Tooltip("上一张"));
        nextImageBtn.setTooltip(new Tooltip("下一张"));


    }

    private void searchPicturePos(){
        int len1= ChangeService.files.size();
        if(ChangeService.selectedPictures==null){
            index=0;
            return;
        }
        String pictureName=ChangeService.selectedPictures.get(0).getPictureName();
        for(index=0;index<len1;++index){
            if(pictureName.compareTo(ChangeService.files.get(index).getPictureName())==0)
                break;
        }
    }

}
