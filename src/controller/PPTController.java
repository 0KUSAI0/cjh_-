package controller;

import action.BackAction;
import action.OpenAction;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.PictureNode;
import service.ChangeService;
import javafx.util.Duration;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class PPTController implements Initializable {

    private Timeline timeline;
    @FXML
    private Button stop;

    @FXML
    private ImageView imageview;

    @FXML
    private Button start;

    @FXML
    private void Begin(ActionEvent event) {
        timeline.play();
    }

    @FXML
    private void Stop(ActionEvent event) {
        timeline.stop();
    }

    @FXML
    private void Back(ActionEvent event) {
        //new OpenAction(0);
        new BackAction(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(!ChangeService.flag){
            ImageViewController.index=0;
        }
        PictureNode pNode=ChangeService.files.get(ImageViewController.index);

        try {
            Image image=new Image(pNode.getPictureFile().getImageFile().toURI().toURL().toString(),0,529,true,true,true);
            imageview.setImage(image);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        imageview.setEffect(pNode.getImageView().getEffect());
        imageview.setViewport(pNode.getImageView().getViewport());
        imageview.setNodeOrientation(pNode.getImageView().getNodeOrientation());
        imageview.setRotate(pNode.getImageView().getRotate());


        timeline=new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        KeyValue keyValue=new KeyValue(imageview.scaleXProperty(),1.5);
        KeyValue keyValue2=new KeyValue(imageview.scaleYProperty(),1.5);
        Duration duration=Duration.seconds(3);
        EventHandler<ActionEvent> onFinished=(ActionEvent t)->{
            ++ImageViewController.index;
            if(ImageViewController.index<ChangeService.files.size()){
                imageview.setImage(ChangeService.files.get(ImageViewController.index).getImage());
            }else if(ImageViewController.index==ChangeService.files.size()){
                ImageViewController.index=0;
                imageview.setScaleX(1.0);
                imageview.setScaleY(1.0);
                timeline.stop();
            }
        };

        KeyFrame keyFrame=new KeyFrame(duration,onFinished,keyValue,keyValue2);

        timeline.getKeyFrames().add(keyFrame);
    }


}
