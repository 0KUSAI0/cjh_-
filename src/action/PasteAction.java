package action;

import controller.MainUIController;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;
import model.PictureFile;
import model.PictureNode;
import service.FileCopy;
import service.NewName;

import java.io.*;
import java.net.MalformedURLException;
import java.util.List;
//粘贴功能实现
public class PasteAction {
    private MainUIController mainUIController;
    public PasteAction(MainUIController mainUIController){
        this.mainUIController = mainUIController;
        Clipboard clipboard = Clipboard.getSystemClipboard();//获取系统剪贴板上的内容
        List<File> files =(List<File>) (clipboard.getContent(DataFormat.FILES));//将剪贴板的内容获取出来
        if(files.size()<=0)
            return;//如果是空的则返回
        if(mainUIController.getCutedPictures().size()>0){//如果当前是有在剪切的图片
            File first=files.get(0);

            if(first.getParentFile().getAbsolutePath().compareTo(MainUIController.theFilePath)==0){
                for(PictureNode pNode : mainUIController.getCutedPictures()){
                    pNode.getImageView().setEffect(null);
                }
                mainUIController.clearSelected();
                mainUIController.getCutedPictures().clear();
                mainUIController.getSelectedPictureFiles().clear();
                clipboard.clear();
                return;
            }//如果被剪切的图片粘贴在了同一个目录下的话，则先取消掉因为剪切图片的背景变灰，然后其他不变
        }
        for(File oldFile :files){
            String newName= NewName.PasterName(MainUIController.theFilePath,oldFile.getName());//为需要粘贴的图片生成名字
            File newFile=new File(MainUIController.theFilePath+File.separator+newName);//并且生成一个文件
            try {
                newFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(newFile.exists()){
                try {
                    FileCopy.copyFile(oldFile,newFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }//如果文件生成成功了，则进行真正的复制操作
            try {
                PictureNode picture=new PictureNode(mainUIController,new PictureFile(newFile));//生成一个图片节点
                mainUIController.getFlowPane().getChildren().add(picture);//同时将其增加到预览图部分
                mainUIController.getPictures().add(picture);//图片数组中也要将其放入进去
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            if(mainUIController.getCutedPictures().size()>0){
                oldFile.delete();//之前旧名字的图片文件删除
            }
        }
        clipboard.clear();
    }
}
