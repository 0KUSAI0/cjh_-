package action;

import controller.MainUIController;
import javafx.event.EventHandler;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import model.PictureFile;
import model.PictureNode;
import service.FileCopy;
import service.NewName;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public class DragImageAction {
    private MainUIController mainUIController;
    private FlowPane flowPane;

    public DragImageAction(MainUIController mainUIController){
        this.mainUIController = mainUIController;
        flowPane=mainUIController.getFlowPane();
        flowPane.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                flowPane.setBorder(new Border(new BorderStroke(Paint.valueOf("#0000ff"),
                        BorderStrokeStyle.SOLID,new CornerRadii(0),new BorderWidths(0.5))));
                //当有图片拖拽进入该部分时，将会设置蓝色边框，暗示用户可以拖拽添加图片
            }
        });//设置图片预览部分Pane的拖拽进入活动监听

        flowPane.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                flowPane.setBorder(null);
            }
            //拖拽的图片离开了该部分时取消边框设置
        });//设置图片预览部分Pane的拖拽移出监听

        flowPane.setOnDragOver((e) -> {
            if (e.getGestureSource() != flowPane && e.getDragboard().hasFiles()) {
                e.acceptTransferModes(TransferMode.COPY);
            }
            e.consume();
        });//设置图片预览部分Pane的拖拽移动到Pane上方监听

        flowPane.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                success = true;
                Dragboard dragboard = e.getDragboard();//获取拖拽板
                List<File> files = dragboard.getFiles();
                File oldFile = files.get(0);
                String newName = NewName.PasterName(MainUIController.theFilePath, files.get(0).getName());//为需要粘贴的图片生成名字
                File newFile = new File(MainUIController.theFilePath + File.separator + newName);//并且生成一个文件
                try {
                    newFile.createNewFile();
                } catch (IOException e1) {
                    throw new RuntimeException(e1);
                }
                if (newFile.exists()) {
                    try {
                        FileCopy.copyFile(oldFile, newFile);
                    } catch (IOException e2) {
                        throw new RuntimeException(e2);
                    }
                }
                try {
                    PictureNode picture = new PictureNode(mainUIController, new PictureFile(newFile));//生成一个图片节点
                    mainUIController.getFlowPane().getChildren().add(picture);//同时将其增加到预览图部分
                    mainUIController.getPictures().add(picture);//图片数组中也要将其放入进去
                } catch (MalformedURLException e3) {
                    e3.printStackTrace();
                }
            }
            e.setDropCompleted(success);
            e.consume();
        });//设置图片预览部分Pane的拖拽的东西放置入Pane上的监听

    }
}
