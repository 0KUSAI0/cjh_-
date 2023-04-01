package service;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.PictureFile;
import model.PictureNode;

public class MouseEventHandler implements EventHandler<MouseEvent> {

    Node node=null;
    PictureFile pictureFile;
    public MouseEventHandler(){
    }
    public MouseEventHandler(Node node,PictureFile pictureFile){
        this.node=node;
        this.pictureFile=pictureFile;
    }




    @Override
    public void handle(MouseEvent mouseEvent) {
        if(node instanceof PictureNode){
            if(mouseEvent.isControlDown()==false){
                if(mouseEvent.getButton()!= MouseButton.SECONDARY|| !((PictureNode)node).selected.getValue()){
                    PictureNode.clearSelected();
                }
                ((PictureNode)node).setSelected(true);

                if(mouseEvent.getClickCount()>=2&&mouseEvent.getButton()==MouseButton.PRIMARY){
                    //鼠标左键按两次
                    ((PictureNode)node).setSelected(true);
                    ((PictureNode)node).openAction();
                }
            }
            if(mouseEvent.isControlDown()&&mouseEvent.getButton()==MouseButton.PRIMARY){
                ((PictureNode) node).setSelected( !((PictureNode)node).selected.get() );
            }
        }

    }
}