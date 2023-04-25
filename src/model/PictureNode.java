package model;

import action.OpenAction;
import controller.MainUIController;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import service.ChangeService;
import service.MouseEventHandler;

import java.io.*;
import java.net.MalformedURLException;

//PictureFile类继承Label，是预览部分的缩略图结点模型
public class PictureNode extends Label {
  private static MainUIController mainScene;//主控制器，使得控制器每个图片节点的信息
  private PictureFile pictureFile;//该图片结点的上级文件
  private Image image;//该图片节点下的图片
  private ImageView imageView;//该图片节点下的可视化图片
  private Text pictureName;//该图片节点的名称
  private PictureNode pictureNode=this;

  public BooleanProperty selected =new SimpleBooleanProperty();//监测该图片节点是否有被选中

  //protected static ArrayList<File> selectedCopyPictureFiles=new ArrayList<>();//用来放置被选择的图片文件，用于放入系统复制粘贴的面板中，这个是一个临时的存放图片节点文件的数组，每次用完要清空
 // protected static ArrayList<PictureNode> selectedPictures=new ArrayList<>();//用来存放现在选中的图片节点，在图片被选中的时候添加到这个数组中
  //protected static ArrayList<PictureNode> cutedPictures=new ArrayList<>();//用来存放被进行剪切操作的图片

  public PictureNode(MainUIController mainUIController,PictureFile pictureFile) throws MalformedURLException {
      this.pictureFile=pictureFile;
      this.mainScene=mainUIController;
      initData();//初始化
      addPictureNodeListener();//增加图片节点是否被选中的监听
      new MyContextMenu(this,mainScene,true);//同时为图片结点实例化一个右键菜单
  }//构造函数

  private void initData()throws MalformedURLException{
      this.setGraphicTextGap(10);
      this.setPadding(new Insets(1,1,1,1));
      this.setContentDisplay(ContentDisplay.TOP);
      this.setPrefSize(110,110);
      //设置好该图片结点的大小位置
      this.image=new Image(pictureFile.getImageFile().toURI().toURL().toString(),100,100,true,true,true);
      this.imageView=new ImageView(image);
      this.pictureName=new Text(pictureFile.getImageName());
      this.setText(pictureFile.getImageName());
      this.setGraphic(imageView);
      pictureNode.setId("pictureNode");
      //将该文件的图片放入到该图片结点中进行显示

  }//对该图片节点进行初始化

  private void addPictureNodeListener(){
     selected.addListener(new InvalidationListener() {
         @Override
         public void invalidated(Observable observable) {
             if(selected.get()) {
                 pictureNode.setStyle("-fx-background-color:#a7a7a7;");
//					mainScene.getText().setText("");
                 //mainScene.getTextTwo().setText("已选中 0  张图片");
             }else {
                 pictureNode.setStyle("-fx-background-color:transparent;");
//					System.out.println(selectedPictures.size()+"--");
                 //mainScene.getTextTwo().setText("已选中 0  张图片");
             }//被选中图片将变色，没被选中背景则为透明
         }//监听图片节点是否被选中
     });
     this.setOnMouseEntered((MouseEvent e)-> {
         if(!selected.get()){
             this.setStyle("-fx-background-color:linear-gradient(to bottom,#3e4147 1%,  #a7a7a7 98%);");
         }
     });//如果监听到鼠标进入图片节点，同时该结点未被选中，则背景颜色改变
     this.setOnMouseExited((MouseEvent e)->{
         if(!selected.get()){
             this.setStyle("-fx-background-color:transparent;");
         }
     });//当鼠标离开图片结点同时该结点是没有被选中的，则将背景颜色恢复为透明
     this.addEventHandler(MouseEvent.MOUSE_CLICKED,new MouseEventHandler(this,pictureFile,mainScene));//为该图片节点设置鼠标事件处理服务
  }//对该图片节点添加listener

  public Image getImage(){
      try{
          return image=new Image(pictureFile.getImageFile().toURI().toURL().toString());
      }catch(MalformedURLException e){
          e.printStackTrace();
      }
      return image;
  }//获取这个节点的图片

    public File getImageFile(){
      return this.pictureFile.getImageFile();
  }//获取这个图片节点的上级的文件

    public PictureFile getPictureFile() {
        return pictureFile;
    }

    public String getURL(){
      try{
          return this.pictureFile.getImageFile().toURI().toURL().toString();
      }catch(MalformedURLException e){
          e.printStackTrace();
      }
      return null;
  }

  public ImageView getImageView(){
      return this.imageView;
  }


  /*

  public static ArrayList<File> getSelectedCopyPictureFiles(){
      return selectedCopyPictureFiles;
  }//获取被选中要复制的图片文件
  public static ArrayList<PictureNode> getSelectedPictures(){
      return selectedPictures;
  }//获取被选中的图片结点
  public static void setCutedPictures(ArrayList<PictureNode> cutedPictures){
      PictureNode.cutedPictures=cutedPictures;
  }
  public static void addCutedPictures(PictureNode pNode){
      PictureNode.cutedPictures.add(pNode);
  }
  public static void clearCutedPictures(){
      cutedPictures.removeAll(cutedPictures);
  }

  public static ArrayList<PictureNode> getCutedPictures(){
      return cutedPictures;
  }

  public void setSelected(boolean value){
      boolean isTrue=selected.get();
      selected.set(value);
      if(selected.get()&&!isTrue)
          selectedPictures.add(this);
      else if(isTrue&&!selected.get())
          selectedPictures.remove(this);
      mainScene.getText().setText("已选中 "+selectedPictures.size()+"张图片");
  }

  public static void clearSelected(){
      for(PictureNode pNode:selectedPictures){
          pNode.selected.set(false);
      }
      selectedPictures.removeAll(selectedPictures);
      mainScene.getText().setText("已选中 "+selectedPictures.size()+"张图片");
  }//清楚所有被选中的图片结点
*/


    public void setSelected(boolean value){
        boolean isTrue=selected.get();
        selected.set(value);
        if(selected.get()&&!isTrue)
           mainScene.selectedPictures.add(this);
        else if(isTrue&&!selected.get())
              mainScene.selectedPictures.remove(this);
        ChangeService.selectedPictures=mainScene.selectedPictures;
        mainScene.getText().setText("已选中 "+mainScene.selectedPictures.size()+"张图片");
    }



    public String getPictureName(){
      return this.pictureFile.getImageName();
    }
    public void openAction(){
      new OpenAction(1);
  }//进入幻灯片模式

}
