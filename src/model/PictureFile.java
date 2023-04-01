package model;

import java.io.*;
import java.net.URL;


//树状图下的最下层的文件节点
public class PictureFile{
    private String imageName;//文件名
    private File imageFile;//文件
    private URL imageURL;//

    public PictureFile(File imageFile){
        this.imageFile=imageFile;
        imageName=imageFile.getName();
        if(imageName.equals("")) {
            imageName = imageFile.getPath();
        }
    }//构造函数

    public PictureFile(String imagePath){
        this(new File(imagePath));
    }

    public PictureFile[] listPictures(){
        File[] files=this.imageFile.listFiles();
        if(files==null||files.length==0)
            return null;
        int len=files.length;

        PictureFile[] pictureFiles=new PictureFile[len];
        for(int i=0;i<len;++i){
            pictureFiles[i]=new PictureFile(files[i]);
        }
        return pictureFiles;
    }//将该文件下的所有文件列出

    public  URL toURL(){
        return imageURL;
    }

    public boolean isPicture(){
        if(imageName.toLowerCase().endsWith(".jpg")||
                imageName.toLowerCase().endsWith(".png")||
                imageName.toLowerCase().endsWith(".gif")||
                imageName.toLowerCase().endsWith(".jpge")||
                imageName.toLowerCase().endsWith(".bmp")
        ){
            return true;
        }
        return false;
    }

    public boolean isDirectory(){
        return imageFile.isDirectory();
    }

    public boolean isFile(){
        return imageFile.isFile();
    }

    public boolean isHidden(){
        return imageFile.isHidden();
    }

    public long length(){
        return imageFile.length();
    }

    public String toString(){
        return imageName;
    }

    public String getImageName() {
        return imageName;
    }

    public File getImageFile() {
        return imageFile;
    }

    public URL getImageURL() {
        return imageURL;
    }
}
