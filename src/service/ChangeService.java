package service;

import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.PictureNode;
import java.util.ArrayList;

public class ChangeService {
    public static boolean flag=false;
    //判断是从主界面中的播放按钮打开幻灯片界面还是从图片查看界面切换到幻灯片界面
    //当flag=false时表示从主界面打开幻灯片界面
    //当flag=true时表示从图片查看界面切换到幻灯片界面
    public static int howChange=0;
    //判断是从主界面中的打开按钮打开图片查看图片界面还是双击或从幻灯片界面到图片查看界面
    //当howChange=0时表示从主界面生成图片查看界面
    //当howChange=1时表示从图片结点双击或从幻灯片界面转换到图片查看界面
    public static Stage stage;//当前的界面
    public static ArrayList<PictureNode> files;
    //存放当前文件夹下的所有图片文件
    public static ArrayList<PictureNode> selectedPictures;
    //存放当前文件夹下的所有被选中的图片结点
    public static ImageView origin,change;
    //记录当打开图片查看界面中图片的第一张图片
    public static double originHeight,originWidth;
    //记录当前图片查看界面中图片的初始宽高

}
