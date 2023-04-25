package controller;

import action.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
    private BorderPane borderPane;


    @FXML
    private Button rotateBtn;

    @FXML
    private HBox hbox;
    @FXML
    private Button nextImageBtn;

    @FXML
    private ImageView imageView;

    @FXML
    private BorderPane pictureBox;
    @FXML
    private Button smallBtn;

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Button UpsideDownBtn;
    @FXML
    private void Press(ActionEvent event) {
        if (toolbar.isVisible()) {
            toolbar.setVisible(false);
        }else {
            toolbar.setVisible(true);
        }
    }


    @FXML
    private void previousAction(ActionEvent event) {
       index=new SwitchPicture(imageView,index).nextPicture();
    }

    @FXML
    private void nextAction(ActionEvent event) {
        index=new SwitchPicture(imageView,index).prePicture();
    }

    @FXML
    private void PPTAction(ActionEvent event) {
        new PPTAction(1,true);
    }

    @FXML
    private void beautyAction(ActionEvent event) {
        new BeautyAction();
    }

    @FXML
    private void rotateAction(ActionEvent event) {
        new RotateAction(imageView);
    }

    @FXML
    private void UpsideDown(ActionEvent event) {
        new UpsideDownAction(imageView);
    }

    @FXML
    private void enlargeAction(ActionEvent event) {
        new ZoomImage(imageView).enlargePicture();
    }

    @FXML
    private void smallAction(ActionEvent event) {
        new ZoomImage(imageView).smallPicture();
    }

    @FXML
    private void resetAction(ActionEvent event) {
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
        pNode.setSelected(false);
        System.out.println("b"+pNode.getPictureName()+" "+pNode.selected);
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
        ScreenAdaptation();

    }

    private void ScreenAdaptation(){
        borderPane.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                double width=t1.doubleValue();
                hbox.setPrefWidth(width-25);
                pictureBox.setPrefWidth(width-previousImageBtn.getPrefWidth()-nextImageBtn.getPrefWidth()-15);
            }
        });
        borderPane.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                double height=t1.doubleValue();
                pictureBox.setPrefHeight(height-20-toolbar.getPrefHeight()-15);
            }
        });
    }

    private void searchPicturePos(){
        int len1= ChangeService.files.size();
        if(ChangeService.selectedPictures==null||ChangeService.selectedPictures.size()==0){
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
