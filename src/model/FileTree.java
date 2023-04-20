package model;

import controller.MainUIController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import service.ChangeService;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

//FileTree类为目录树模型，实现目录树功能
public class FileTree {
    private MainUIController mainUIController;
    private TreeView<PictureFile> treeView;
    private TreeItem<PictureFile> root;

    private final File[] rootPath= File.listRoots(); //获取系统的路径

    public FileTree(MainUIController mainUIController,TreeView<PictureFile> treeView){
        this.mainUIController=mainUIController;
        this.treeView=treeView;
        root=new TreeItem<PictureFile>(new PictureFile(("root")));
        root.setExpanded(true);
        treeView.setRoot(root);
        treeView.setShowRoot(false);
        buildFileTree();//调用构建目录树方法
        getSelectedPicture();//监听现在是选中了哪个文件夹
    }//目录树构造函数

    private void buildFileTree(){
        for (int i=0;i<rootPath.length;++i){
            FileTreeItem item=new FileTreeItem(new PictureFile(rootPath[i]));//将系统路径下的文件夹列出放入加入到目录树中
            root.getChildren().add(item);
        }
    }//构建目录树

    public TreeView<PictureFile> getTreeView(){
        return treeView;
    }


    private void getSelectedPicture(){
        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<PictureFile>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<PictureFile>> observableValue, TreeItem<PictureFile> pictureFileTreeItem, TreeItem<PictureFile> t1) {
                //mainUIController.getFlowPane().getChildren().remove(0,mainUIController.getFlowPane().getChildren().size());
                mainUIController.clearPictures();//清除当前再预览部分中的缩略图
                PictureFile pFile=treeView.getSelectionModel().getSelectedItem().getValue();//获取现在是选中了哪个文件夹
                if(pFile.isDirectory()){
                    MainUIController.theFilePath=pFile.getImageFile().getAbsolutePath();
                    int total=0;
                    double size=0;
                    boolean first=true;

                    PictureFile[] pictureFiles=pFile.listPictures();

                    if(pictureFiles==null||pictureFiles.length==0)
                        return;

                    for(PictureFile pictureFile:pictureFiles){
                        if(pictureFile.isPicture()){
                            ++total;
                            size+=pictureFile.length();
                            try{
                                PictureNode pictureNode=new PictureNode(mainUIController,pictureFile);
                                mainUIController.getFlowPane().getChildren().add(pictureNode);
                                mainUIController.addPictures(pictureNode);
                            }catch (MalformedURLException e){
                                e.printStackTrace();
                            }
                        }
                    }//将该文件夹下的图片加入到预览部分中的图片数组中，同时将其显示在预览部分

                    //mainUIController.showPicture();
                    mainUIController.getText().setText(total+"张图片，共"+(int)size+"Byte");//该文件夹下图片数量
                    ChangeService.files=mainUIController.pictures;
                }//如果该文件是文件夹
            }
        });
    }

}
