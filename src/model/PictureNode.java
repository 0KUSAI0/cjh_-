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
import main.Main;
import service.MouseEventHandler;

import javax.swing.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;


public class PictureNode extends Label {
  private static MainUIController mainScene;
  private PictureFile pictureFile;
  private Image image;
  private ImageView imageView;
  private Text pictureName;
  private PictureNode pictureNode=this;

  public BooleanProperty selected =new SimpleBooleanProperty();

  protected static ArrayList<File> selectedCopyPictureFiles=new ArrayList<>();//用来放置被选择的图片文件，用于放入系统复制粘贴的面板重，这个是一个临时的存放图片节点文件的数组，每次用完要清空
  protected static ArrayList<PictureNode> selectedPictures=new ArrayList<>();//用来存放现在选中的图片节点，在图片被选中的时候添加到这个数组中
  protected static ArrayList<PictureNode> cutedPictures=new ArrayList<>();//用来存放被进行剪切操作的图片

  public PictureNode(MainUIController mainUIController,PictureFile pictureFile) throws MalformedURLException {
      this.pictureFile=pictureFile;
      this.mainScene=mainUIController;
      initData();
      addPictureNodeListener();
      new MyContextMenu(this,mainScene,true);
  }

  private void initData()throws MalformedURLException{
      this.setGraphicTextGap(10);
      this.setPadding(new Insets(1,1,1,1));
      this.setContentDisplay(ContentDisplay.TOP);
      this.setPrefSize(110,110);

      this.image=new Image(pictureFile.getImageFile().toURI().toURL().toString(),100,100,true,true,true);
      this.imageView=new ImageView(image);
      this.pictureName=new Text(pictureFile.getImageName());
      this.setText(pictureFile.getImageName());
      this.setGraphic(imageView);
      pictureNode.setId("pictureNode");

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
             }
         }
     });
     this.setOnMouseEntered((MouseEvent e)-> {
         if(!selected.get()){
             this.setStyle("-fx-background-color:linear-gradient(to bottom,#3e4147 1%,  #a7a7a7 98%);");
         }
     });
     this.setOnMouseExited((MouseEvent e)->{
         if(!selected.get()){
             this.setStyle("-fx-background-color:transparent;");
         }
     });
     this.addEventHandler(MouseEvent.MOUSE_CLICKED,new MouseEventHandler(this,pictureFile));
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
  }//获取这个图片节点的上一级的文件

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

  public void openAction(){
      new OpenAction();
  }

}
