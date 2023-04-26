package service;

import controller.MainUIController;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.PictureFile;
import model.PictureNode;

public class MouseEventHandler implements EventHandler<MouseEvent> {

    Node node=null;
    PictureFile pictureFile;
    public MainUIController mainUIController;
    public MouseEventHandler(){
    }
    public MouseEventHandler(Node node,PictureFile pictureFile,MainUIController mainUIController){
        this.node=node;
        this.pictureFile=pictureFile;
        this.mainUIController=mainUIController;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if(node instanceof PictureNode){//如果当前鼠标是在图片结点处进行活动
            if(mouseEvent.isControlDown()==false){//当没有检测到CTRL键按下，即为单选图片结点
                if(mouseEvent.getButton()!= MouseButton.SECONDARY|| !((PictureNode)node).selected.getValue()){
                    mainUIController.clearSelected();
                }//清除当前所有被选中的图片结点
                ((PictureNode)node).setSelected(true);//并且设置这个图片结点为选中状态
                if(mouseEvent.getClickCount()>=2&&mouseEvent.getButton()==MouseButton.PRIMARY){
                    //鼠标左键按两次
                    ((PictureNode)node).setSelected(true);
                    ((PictureNode)node).openAction();//打开图片查看界面
                }
            }
            if(mouseEvent.isControlDown()&&mouseEvent.getButton()==MouseButton.PRIMARY){
                ((PictureNode) node).setSelected( !((PictureNode)node).selected.get() );
            }//如果按下CTRL键的同时鼠标在图片结点活动时，若当前图片结点是未选中状态则变为选中状态，否则变为未选中状态
        }

    }
}
