package model;

import java.io.*;
import java.net.URL;


//PicturFile类由文件路径、文件路径与图片URL组成，该类是目录树中的文件
public class PictureFile{
    private String imageName;//文件名
    private File imageFile;//文件
    private URL imageURL;//图片的URL

    public PictureFile(File imageFile){
        this.imageFile=imageFile;
        imageName=imageFile.getName();
        if(imageName.equals("")) {
            imageName = imageFile.getPath();
        }
    }//构造函数

    public PictureFile(String imagePath){
        this(new File(imagePath));
    }//构建函数

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
    }//判断该文件是否为jpg、png、gif、jpge、bmp类型图片文件

    public boolean isDirectory(){
        return imageFile.isDirectory();
    }//判断是否为文件夹

    public boolean isFile(){
        return imageFile.isFile();
    }//判断是否为文件

    public boolean isHidden(){
        return imageFile.isHidden();
    }//判断该文件是否为隐藏文件

    public long length(){
        return imageFile.length();
    }//获取该文件大小

    public String toString(){
        return imageName;
    }//获取该文件以文件名的字符串

    public String getImageName() {
        return imageName;
    }//获取该文件的文件名称

    public File getImageFile() {
        return imageFile;
    }//获取该类下的文件

    public URL getImageURL() {
        return imageURL;
    }
}
