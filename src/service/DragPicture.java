package service;

import controller.MainUIController;
import javafx.event.EventHandler;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import main.Main;
import model.PictureFile;
import model.PictureNode;
import service.FileCopy;
import service.NewName;

import javax.swing.plaf.BorderUIResource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class DragPicture {
    public MainUIController mainUIController;
    private ClipboardContent clipboardContent;
    private FlowPane flowPane;

    public DragPicture(MainUIController mainUIController){
        this.mainUIController = mainUIController;
        flowPane=mainUIController.getFlowPane();
        flowPane.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                flowPane.setBorder(new Border(new BorderStroke(Paint.valueOf("#0000ff"), BorderStrokeStyle.SOLID,new CornerRadii(0),new BorderWidths(0.5))));
            }
        });

        flowPane.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                flowPane.setBorder(null);
            }
        });

        flowPane.setOnDragOver((e) -> {
            if (e.getGestureSource() != flowPane && e.getDragboard().hasFiles()) {
                e.acceptTransferModes(TransferMode.COPY);
            }
            e.consume();
        });

        flowPane.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                success = true;
                Dragboard dragboard = e.getDragboard();
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
        });

        /*
        flowPane.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                System.out.println("asd");
                Dragboard dragboard=dragEvent.getDragboard();
                List<File> Files=dragboard.getFiles();
                System.out.println(Files.get(0).getName());
                PictureFile pictureFile=new PictureFile(Files.get(0));
                try {
                    PictureNode pictureNode=new PictureNode(mainUIController,pictureFile);

                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }

            }
        });

         */

    }
}
