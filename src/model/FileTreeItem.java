package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.io.IOException;

//FileTreeItem类继承TreeItem作为该系统新的树项模型
public class FileTreeItem extends TreeItem<PictureFile> {

    private boolean isLeaf;//是否为叶子结点
    private boolean isFirstChildren=true;
    private boolean isFirstLeaf=true;

    public FileTreeItem(PictureFile fileTreeItem){
        super(fileTreeItem);
    }//构造函数

    @Override
    public ObservableList<TreeItem<PictureFile>> getChildren() {

        if (isFirstChildren){
            isFirstChildren=false;
            super.getChildren().setAll(buildChildren(this));
        }
        return super.getChildren();
    }//重写获取子节点方法，为建造目录书提供获取子节点的方法

    @Override
    public boolean isLeaf() {

        if (isFirstLeaf) {
            isFirstLeaf = false;
            PictureFile pictureFile = getValue();
            PictureFile[] pictureFiles = pictureFile.listPictures();
            if (pictureFiles == null ||pictureFiles.length == 0 ) {
                isLeaf = true;
            } else {
                isLeaf = true;
                for (PictureFile pictureFile2 : pictureFiles) {
                    if (pictureFile2.isDirectory()) {
                        isLeaf = false;
                    }
                }
            }
        }
        return isLeaf;
    }//重写isLeaf方法为目录书的判断其是否为子节点而能否继续展开提供判断方法，每当我们在目录树点击时便会自动调用这个函数以判断是否为叶子结点，如果是叶子节点则不展开

    public ObservableList<TreeItem<PictureFile>> buildChildren(TreeItem<PictureFile> TreeItem){
        PictureFile pictureFile= TreeItem.getValue();

        if(pictureFile!=null&&pictureFile.isDirectory()){
            PictureFile[] pictureFiles=pictureFile.listPictures();
            if(pictureFiles!=null&&pictureFiles.length!=0){
                ObservableList<TreeItem<PictureFile>> children= FXCollections.observableArrayList();
                for(PictureFile childFile: pictureFiles){
                    if(childFile.isHidden()||childFile.isFile())
                        continue;
                    children.add(new FileTreeItem(childFile));
                }
                return children;
            }
        }
        return FXCollections.emptyObservableList();
    }//如果给到的文件是文件夹，则将该文件中的文件夹作为其子节点构建，用于构建目录树

}
