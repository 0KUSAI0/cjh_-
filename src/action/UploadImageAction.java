package action;

import controller.MainUIController;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.PictureFile;
import model.PictureNode;
import service.FileCopy;
import service.NewName;

import java.io.*;
import java.net.MalformedURLException;

//上传图片功能
public class UploadImageAction {
    private MainUIController mainUIController;
    private Stage stage;
    private File selcetFile;
    private FileChooser fc;
    public UploadImageAction(MainUIController mainUIController){
        this.mainUIController = mainUIController;
        getselcetFile();
    }

    private void getselcetFile(){
        stage=new Stage();
        fc=new FileChooser();//构建一个文件选择器
        fc.setTitle("选择你要上传的图片");
        fc.setInitialDirectory(new File(MainUIController.theFilePath));
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("图片类型","*.jpg","*.png","*.gif","*.bmp","*.jpge")
        );//设置能够上传的文件类型
        selcetFile=fc.showOpenDialog(stage);
        if (selcetFile==null)
            return;//如果用户没有选择图片则直接返回
        String newName= NewName.PasterName(MainUIController.theFilePath, selcetFile.getName());//为上传图片文件生成名字
        File newFile=new File(MainUIController.theFilePath+File.separator+newName);
        try {
            newFile.createNewFile();//并且创建成图片文件
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(newFile.exists()){
            try {
               FileCopy.copyFile(selcetFile,newFile);//通过数据传输完成复制
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            PictureNode picture=new PictureNode(mainUIController,new PictureFile(newFile));//生成一个图片节点
            mainUIController.getFlowPane().getChildren().add(picture);//同时将其增加到预览图部分
            mainUIController.getPictures().add(picture);//图片数组中也要将其放入进去
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
