package model;

import controller.MainUIController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

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
        buildFileTree();
        getSelectedPicture();
    }

    private void buildFileTree(){
        for (int i=0;i<rootPath.length;++i){
            FileTreeItem item=new FileTreeItem(new PictureFile(rootPath[i]));
            root.getChildren().add(item);
        }
    }

    public TreeView<PictureFile> getTreeView(){
        return treeView;
    }


    private void getSelectedPicture(){
        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<PictureFile>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<PictureFile>> observableValue, TreeItem<PictureFile> pictureFileTreeItem, TreeItem<PictureFile> t1) {
                //mainUIController.getFlowPane().getChildren().remove(0,mainUIController.getFlowPane().getChildren().size());
                mainUIController.clearPictures();
                PictureFile pFile=treeView.getSelectionModel().getSelectedItem().getValue();

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
                    }

                    //mainUIController.showPicture();
                    mainUIController.getText().setText(total+"张图片，共"+(int)size+"Byte");
                }
            }
        });
    }

}
