package action;

import controller.MainUIController;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.Main;
import model.PictureFile;
import model.PictureNode;

import java.io.*;
import java.net.MalformedURLException;

//上传图片功能
public class uploadImageAction {
    public MainUIController mainUIController;
    private Stage stage;
    private File selcetFile;
    private File uploadFile;
    private FileChooser fc;
    public uploadImageAction(MainUIController mainUIController){
        this.mainUIController = mainUIController;
        stage=new Stage();
        fc=new FileChooser();
        fc.setTitle("选择你要上传的图片");
        fc.setInitialDirectory(new File(MainUIController.theFilePath));
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("图片类型","*.jpg","*.png","*.gif","*.bmp","*.jpge")
        );
        selcetFile=fc.showOpenDialog(stage);
        String newName=newName(MainUIController.theFilePath,selcetFile.getName());
        File newFile=new File(MainUIController.theFilePath+File.separator+newName);//并且生成一个文件
        try {
            newFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(newFile.exists()){
            try {
                upload(selcetFile,newFile);
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

    }
    private void upload(File fromFile,File toFile) throws IOException {
        DataInputStream dis= new DataInputStream( new BufferedInputStream(new FileInputStream(fromFile)));
        DataOutputStream dos= new DataOutputStream( new BufferedOutputStream(new FileOutputStream(toFile)));
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = dis.read(buffer))!= -1) {
            dos.write(buffer, 0, bytesRead);
        }
        dis.close();
        dos.close();
    }//将原图片进行复制

    private String newName(String theFilePath,String name){
        String newName = name;
        File fatherPathFile=new File(theFilePath);
        File[] filesInFatherPath=fatherPathFile.listFiles();
        for(File fileInFatherPath: filesInFatherPath){
            String fileName=fileInFatherPath.getName();
            if(newName.compareTo(fileName)==0){
                String str=null;
                int end=newName.lastIndexOf("."),start=newName.lastIndexOf("_副本");
                if(start!=-1){
                    str=newName.substring(start,end);
                    int num=1;
                    num=Integer.parseInt(str.substring(str.lastIndexOf("_副本")+3))+1;
                    int cnt=0,d=num-1;
                    while(d!=0){
                        d/=10;
                        cnt++;
                    }
                    newName=newName.substring(0,end-cnt)+num+newName.substring(end);
                }else{
                    newName=newName.substring(0,end)+"_副本1"+newName.substring(end);
                }
            }

        }
        return newName;
    }//生成新的名字
}
