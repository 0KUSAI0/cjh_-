package service;

import controller.MainUIController;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Window;
import model.PictureNode;

import java.nio.channels.SelectableChannel;

import static java.lang.Math.abs;
import static java.lang.Math.min;


public class DragSelect {
    Node node;
    MainUIController mainUIController;
    private Rectangle selectRectangle;
    private boolean isDragged;

    public DragSelect(Node node, MainUIController mainUIController) {
        this.node = node;
        this.mainUIController = mainUIController;
        selectRectangle = new Rectangle();
        addListener();
    }
    private void addListener() {
        //鼠标按下，初始化选择矩阵的左上角点
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
            isDragged = false;
            double nowX = e.getX();
            double nowY = e.getY();
            selectRectangle.setX(nowX);
            selectRectangle.setY(nowY);
            selectRectangle.setHeight(0);
            selectRectangle.setWidth(0);
        });

        node.addEventHandler(MouseEvent.MOUSE_DRAGGED, (MouseEvent e) -> {
            this.isDragged = true;
            double nowX = e.getX();
            double nowY = e.getY();
            double baseX = selectRectangle.getX();
            double baseY = selectRectangle.getY();

            selectRectangle.setX(min(baseX, nowX));
            selectRectangle.setY(min(baseY, nowY));
            selectRectangle.setHeight(abs(baseY - nowY));
            selectRectangle.setWidth(abs(baseX-nowX));

            if(this.isDragged) {
                //PictureNode.clearSelected();
                for(Node childrenNode:  mainUIController.getFlowPaneChildren()) {
                    if(childrenNode instanceof PictureNode) {
                        if(isRectOverlap((PictureNode)childrenNode)){
                            ((PictureNode)childrenNode).setSelected(true);
                        }else{
                            ((PictureNode)childrenNode).setSelected(false);
                        }

                    }
                }
            }
            selectRectangle.setX(baseX);
            selectRectangle.setY(baseY);
        });

        //鼠标放开，更新选择矩阵的左上角点以及边长
        node.addEventHandler(MouseEvent.MOUSE_RELEASED, (MouseEvent e) -> {
            double nowX = e.getX();
            double nowY = e.getY();
            double baseX = selectRectangle.getX();
            double baseY = selectRectangle.getY();

            selectRectangle.setX(min(baseX, nowX));
            selectRectangle.setY(min(baseY, nowY));

            selectRectangle.setWidth(abs(baseX - nowX));
            selectRectangle.setHeight(abs(baseY - nowY));

//			System.out.println(selectRectangle);

            //图片和选择矩阵的判断
            if(this.isDragged) {
                mainUIController.clearSelected();
                for(Node childrenNode:  mainUIController.getFlowPaneChildren()) {
                    if(childrenNode instanceof PictureNode) {
                        if(isRectOverlap((PictureNode)childrenNode))
                            ((PictureNode)childrenNode).setSelected(true);
                    }
//					((PictureNode)childrenNode).setSelected(false);
                }
            }

        });
    }
    private boolean isRectOverlap(PictureNode  pictureNode) {
        double imageNodeCenterPointX = pictureNode.getLayoutX() + pictureNode.getWidth()/2.0;
        double imageNodeCenterPointY = pictureNode.getLayoutY() + pictureNode.getHeight()/2.0;
        double selectRectangleCenterPointX = selectRectangle.getX() + selectRectangle.getWidth()/2.0;
        double selectRectangleCenterPointY = selectRectangle.getY() + selectRectangle.getHeight()/2.0;
        return abs(imageNodeCenterPointX - selectRectangleCenterPointX) <= (pictureNode.getWidth()/2.0 + selectRectangle.getWidth()/2.0) &&
                abs(imageNodeCenterPointY - selectRectangleCenterPointY) <= (pictureNode.getHeight()/2.0 + selectRectangle.getHeight()/2.0);
    }

}