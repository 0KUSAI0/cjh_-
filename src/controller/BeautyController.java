package controller;

import action.BackAction;
import action.OpenAction;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import service.ChangeService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BeautyController implements Initializable {
    private Stage stage;

    @FXML
    private HBox toppane;
    @FXML
    private Button backBtn3;

    @FXML
    private AnchorPane leftpane;

    @FXML
    private BorderPane borderpane;

    @FXML
    private ImageView imageview;

    @FXML
    private Slider slider;

    @FXML
    private Button big;

    @FXML
    private Button small;

    @FXML
    private AnchorPane backpane;

    @FXML
    private TextArea textarea;

    @FXML
    private Button sure;

    @FXML
    private Button cancel;

    @FXML
    private AnchorPane rightpane;

    @FXML
    private ImageView image1;

    @FXML
    private ImageView image2;

    @FXML
    private ImageView image3;

    @FXML
    private ImageView image4;

    @FXML
    private ImageView image5;

    @FXML
    private Button backBtn2;
    @FXML
    private ImageView image6;


    @FXML
    private Button SaveButton;

    @FXML
    private Region veil;

    @FXML
    private ProgressIndicator indicator;

    @FXML
    private Label savelabel;

    @FXML
    private AnchorPane Existpane;


    @FXML
    private void Back(ActionEvent event) {//返回按钮
        ChangeService.change = imageview;
        new BackAction(1);
    }

    @FXML
    private void Sure(ActionEvent event) {//提示未保存 点击确定的按钮
        ChangeService.change = ChangeService.origin;
        // App.setStage(stage);
        new OpenAction(1);//参数传递1，就会去找图片的位置
    }

    @FXML
    private void Cancel(ActionEvent event) {//提示未保存 点击取消的按钮
        backpane.setVisible(false);//提示未保存界面，设置成不可见
        sure.setDisable(true);//禁用sure
        cancel.setDisable(true);//禁用cancel
    }

    private Button test;

    @FXML
    private void Origin(ActionEvent e) {//滤镜1，保持不变
        if (test != null) {
            test.setStyle("-fx-border-color:  #2e2d2d;-fx-background-color:  #2e2d2d;");
        }
        test = ((Button) e.getSource());
        slider.setOpacity(0.0);//不透明度（可见度）设置成0，隐藏滑动条
        imageview.setEffect(null);
    }
    @FXML
    private void Overlay(ActionEvent e) {//滤镜2，Overlay
        if (test != null) {
            test.setStyle("-fx-border-color:  #2e2d2d;-fx-background-color:  #2e2d2d;");
        }
        test = ((Button) e.getSource());
        ((Button) e.getSource()).setStyle("-fx-border-color:  #42ff51;-fx-background-color:  #2e2d2d;");
        slider.setOpacity(0.5);
        slider.setValue(0.5);
        Blend blend = new Blend();
        blend.setMode(BlendMode.OVERLAY);
        imageview.setEffect(blend);
        slider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            blend.setOpacity(new_val.doubleValue());
        });
    }

    @FXML
    private void Sepiatone(ActionEvent e) {//滤镜3，Sepiatone
        if (test != null) {
            test.setStyle("-fx-border-color:  red;-fx-background-color:  #2e2d2d;");
        }
        test = ((Button) e.getSource());
        ((Button) e.getSource()).setStyle("-fx-border-color:  #42ff51;-fx-background-color:  #2e2d2d;");

        slider.setOpacity(0.5);
        slider.setValue(0.5);
        SepiaTone effect = new SepiaTone(0.5);
        imageview.setEffect(effect);
        slider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            effect.setLevel(new_val.doubleValue());
        });
    }

    @FXML
    private void Bloom(ActionEvent e) {//滤镜4，Bloom
        if (test != null) {
            test.setStyle("-fx-border-color:  #2e2d2d;-fx-background-color:  #2e2d2d;");
        }
        test = ((Button) e.getSource());
        ((Button) e.getSource()).setStyle("-fx-border-color:  #42ff51;-fx-background-color:  #2e2d2d;");
        slider.setOpacity(0.5);
        slider.setValue(0.5);
        Bloom bloom = new Bloom(0.5);
        imageview.setEffect(bloom);
        slider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            bloom.setThreshold(1-new_val.doubleValue());
        });
    }

    @FXML
    private void Mercury(ActionEvent e) {//滤镜5，Mercury
        if (test != null) {
            test.setStyle("-fx-border-color:  #2e2d2d;-fx-background-color:  #2e2d2d;");
        }
        test = ((Button) e.getSource());
        ((Button) e.getSource()).setStyle("-fx-border-color:  #42ff51;-fx-background-color:  #2e2d2d;");
        slider.setOpacity(0.5);
        slider.setValue(0.5);
        ColorAdjust color = new ColorAdjust();
        color.setSaturation(-1.0);
        BoxBlur boxblur = new BoxBlur();
        boxblur.setWidth(5.0);
        boxblur.setIterations(1);
        color.setInput(boxblur);
        Blend blend = new Blend();
        blend.setMode(BlendMode.OVERLAY);
        boxblur.setInput(blend);
        imageview.setEffect(color);
        slider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            blend.setOpacity(new_val.doubleValue());
        });
    }

    @FXML
    private void Exclusion(ActionEvent e) {//滤镜6，Exclusion
        if (test != null) {
            test.setStyle("-fx-border-color:  #2e2d2d;-fx-background-color:  #2e2d2d;");
        }
        test = ((Button) e.getSource());
        ((Button) e.getSource()).setStyle("-fx-border-color:  #42ff51;-fx-background-color:  #2e2d2d;");
        slider.setOpacity(0.5);
        slider.setValue(0.5);
        Blend blend = new Blend();
        blend.setMode(BlendMode.EXCLUSION);
        blend.setOpacity(0.5);
        imageview.setEffect(blend);
        slider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            blend.setOpacity(new_val.doubleValue());

        });
    }
    public void setImageViewImage(ImageView image) {
        image.setImage(ChangeService.change.getImage());
        image.setEffect(ChangeService.change.getEffect());
        image.setViewport(ChangeService.change.getViewport());
        image.setNodeOrientation(ChangeService.change.getNodeOrientation());
        image.setRotate(ChangeService.change.getRotate());
    }

    private void setImageViewEffect() {
        this.setImageViewImage(image1);
        this.setImageViewImage(image2);
        this.setImageViewImage(image3);
        this.setImageViewImage(image4);
        this.setImageViewImage(image5);
        this.setImageViewImage(image6);
        image1.setEffect(null);
        Blend blend = new Blend();
        blend.setMode(BlendMode.OVERLAY);
        blend.setOpacity(0.5);
        image2.setEffect(blend);
        SepiaTone sep = new SepiaTone(0.5);
        image3.setEffect(sep);
        Bloom bloom = new Bloom(0.5);
        image4.setEffect(bloom);
        BoxBlur boxblur = new BoxBlur();
        boxblur.setWidth(5.0);
        boxblur.setIterations(1);
        ColorAdjust color1 = new ColorAdjust();
        color1.setSaturation(-1.0);
        color1.setInput(boxblur);
        boxblur.setInput(blend);
        image5.setEffect(color1);
        Blend blend2 = new Blend();
        blend2.setMode(BlendMode.EXCLUSION);
        blend2.setOpacity(0.5);
        image6.setEffect(blend2);
        ColorAdjust color2 = new ColorAdjust();
        color2.setHue(-0.6);
        color2.setInput(blend);
        //image7.setEffect(color2);
        ColorAdjust color3 = new ColorAdjust();
        color3.setSaturation(-1.0);
        color3.setInput(blend);
        //image8.setEffect(color3);
        ColorAdjust color4 = new ColorAdjust();
        color4.setHue(0.6);
        color4.setInput(blend);
        //image9.setEffect(color4);
    }

    public void setImage() {
        imageview.setImage(ChangeService.files.get(ImageViewController.index).getImage());
        setImageViewEffect();
    }

    @FXML
    private void Undo(ActionEvent e) {//重置所有按钮
        imageview.setImage(ChangeService.files.get(ImageViewController.index).getImage());
        //blend
        slider.setValue(0.5);
    }

    @FXML
    private void Close(ActionEvent e) {//最后一个按钮，在中间显示的：糟糕无法保存该文件，然后关闭
        Existpane.setVisible(false);
    }

    public class SaveTask extends Task<Integer> {//内部类，多线程,为什么内部类还可以使用public？
        @Override
        protected Integer call() throws Exception {
            for (int i = 0; i < 250; i++) {
                updateProgress(i, 250);
                Thread.sleep(5);
            }
            return 1;
        }
    }

    @FXML
    private void Copy(ActionEvent event) {//保存副本按钮

        File file = ChangeService.files.get(ImageViewController.index).getImageFile();
        if (file.exists()) {
            Task<Integer> task = new SaveTask();

            veil.visibleProperty().bind(task.runningProperty());
            savelabel.visibleProperty().bind(task.runningProperty());
            indicator.visibleProperty().bind(task.runningProperty());
            new Thread(task).start();

            WritableImage image = imageview.snapshot(new SnapshotParameters(), null);
            String copyfilepath = null;

            String filename = file.getName();
            String fileParentPath = file.getParent();

            String name1 = filename.substring(0, filename.lastIndexOf("."));
            System.out.println(name1);
            int a = name1.lastIndexOf("(");
            int b = name1.lastIndexOf(")");
            if (a != -1 && b != -1) {
                String index = name1.substring(name1.lastIndexOf("(") + 1, name1.lastIndexOf(")"));
                if (index != "" && index != null) {
                    int n = Integer.valueOf(index);
                    n++;
                    copyfilepath = fileParentPath + "\\" + name1 + "(" + n + ").jpg";
                }
            } else {
                copyfilepath = fileParentPath + "\\" + name1 + "(" + 1 + ").jpg";
            }
            System.out.println(copyfilepath);
            File files = new File(copyfilepath);
            System.out.println(file.getPath());
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", files);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ChangeService.change = imageview;
        } else {
            Existpane.setVisible(true);
        }

    }

    @FXML
    private void Save(ActionEvent event) {//保存按钮

        File file = ChangeService.files.get(ImageViewController.index).getImageFile();

        if (file.exists()) {
            Task<Integer> task = new SaveTask();

            veil.visibleProperty().bind(task.runningProperty());
            savelabel.visibleProperty().bind(task.runningProperty());
            indicator.visibleProperty().bind(task.runningProperty());

            new Thread(task).start();
            WritableImage image = imageview.snapshot(new SnapshotParameters(), null);

            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                ChangeService.change = imageview;
                ChangeService.change = imageview;
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Existpane.setVisible(true);
        }
    }

    private AnchorPane picturepane;

    @FXML
    private void Scroll(ScrollEvent e) {//鼠标滚轮滚动，放大、缩小图片
        if (imageview.getBoundsInParent().getWidth() >= imageview.getFitWidth() * 2.5) {
            big.setDisable(true);
            big.setOpacity(0.6);
            if (e.getDeltaY() < 0) {
                scrollcount += (int) (e.getDeltaY() / 26);
                big.setDisable(false);
                big.setOpacity(1.0);
                imageview.setScaleX(1 + count * 0.1 + scrollcount * 0.1);
                imageview.setScaleY(1 + count * 0.1 + scrollcount * 0.1);
            }
        } else if (imageview.getBoundsInParent().getWidth() < slider.getWidth()) {
            small.setDisable(true);
            small.setOpacity(0.6);
            if (e.getDeltaY() > 0) {
                scrollcount += (int) (e.getDeltaY() / 26);
                small.setDisable(false);
                small.setOpacity(1.0);
                imageview.setScaleX(1 + count * 0.1 + scrollcount * 0.1);
                imageview.setScaleY(1 + count * 0.1 + scrollcount * 0.1);
            }
        } else {
            scrollcount += (int) (e.getDeltaY() / 26);
            imageview.setScaleX(1 + count * 0.1 + scrollcount * 0.1);
            imageview.setScaleY(1 + count * 0.1 + scrollcount * 0.1);
        }
    }

    private int count = 0;
    private int scrollcount = 0;

    @FXML
    private void Big(ActionEvent e) {//放大按钮
        if (imageview.getBoundsInParent().getWidth() >= imageview.getFitWidth() * 2.5) {
            big.setDisable(true);
            big.setOpacity(0.6);
        } else {
            count++;
            small.setDisable(false);
            small.setOpacity(1.0);
            imageview.setScaleX(1 + count * 0.1 + scrollcount * 0.1);
            imageview.setScaleY(1 + count * 0.1 + scrollcount * 0.1);
        }

    }

    @FXML
    private void Small(ActionEvent e) {//缩小按钮
        if (imageview.getBoundsInParent().getWidth() < slider.getWidth()) {
            small.setDisable(true);
            small.setOpacity(0.6);
        } else {
            count--;
            imageview.setScaleX(1 + count * 0.1 + scrollcount * 0.1);
            imageview.setScaleY(1 + count * 0.1 + scrollcount * 0.1);
            big.setDisable(false);
            big.setOpacity(1.0);

        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {//初始化
        this.setImage();
        ChangeService.stage.widthProperty().addListener((a) -> {
            imageview.setScaleX(1.0);
            imageview.setScaleY(1.0);
        });
        ChangeService.stage.heightProperty().addListener((a) -> {
            imageview.setScaleX(1.0);
            imageview.setScaleY(1.0);
        });
        imageview.fitWidthProperty()
                .bind(ChangeService.stage.widthProperty().subtract(rightpane.widthProperty()).divide(4).multiply(3));
        imageview.fitHeightProperty()
                .bind(ChangeService.stage.heightProperty().subtract(toppane.heightProperty()).divide(4).multiply(3));

        slider.prefWidthProperty().bind(imageview.fitWidthProperty().divide(4).multiply(3));
    }

}
