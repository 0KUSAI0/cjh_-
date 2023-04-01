package action;

import controller.MainUIController;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;
import model.PictureFile;
import model.PictureNode;

import java.io.*;
import java.net.MalformedURLException;
import java.util.List;

public class PasteAction {
    MainUIController mainUIController;
    public PasteAction(MainUIController mainUIController){
        this.mainUIController = mainUIController;
        Clipboard clipboard = Clipboard.getSystemClipboard();
        List<File> files =(List<File>) (clipboard.getContent(DataFormat.FILES));
        if(files.size()<=0)
            return;
        if(PictureNode.getCutedPictures().size()>0){
            File first=files.get(0);

            if(first.getParentFile().getAbsolutePath().compareTo(MainUIController.theFilePath)==0){
                for(PictureNode pNode : PictureNode.getCutedPictures()){
                    pNode.getImageView().setEffect(null);
                }
                PictureNode.clearSelected();
                PictureNode.getCutedPictures().clear();
                PictureNode.getSelectedCopyPictureFiles().clear();
                clipboard.clear();
                return;
            }
        }

        for(File oldFile :files){
            String newName=PasterName(MainUIController.theFilePath,oldFile.getName());
            File newFile=new File(MainUIController.theFilePath+File.separator+newName);
            try {
                   newFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(newFile.exists()){
                try {
                    copyFile(oldFile,newFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                PictureNode picture=new PictureNode(mainUIController,new PictureFile(newFile));
                mainUIController.getFlowPane().getChildren().add(picture);
                mainUIController.getPictures().add(picture);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            if(PictureNode.getCutedPictures().size()>0){
                    oldFile.delete();
            }
        }
        clipboard.clear();
    }

    private void copyFile(File fromFile,File toFile) throws IOException {
        DataInputStream dis= new DataInputStream( new BufferedInputStream(new FileInputStream(fromFile)));
        DataOutputStream dos= new DataOutputStream( new BufferedOutputStream(new FileOutputStream(toFile)));
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = dis.read(buffer))!= -1) {
            dos.write(buffer, 0, bytesRead);
        }
        dis.close();
        dos.close();
    }

    private String PasterName(String theFilePath,String name){
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
    }
}
