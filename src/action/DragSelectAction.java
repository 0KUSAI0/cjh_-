package action;

import controller.MainUIController;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import model.PictureNode;

import static java.lang.Math.abs;
import static java.lang.Math.min;


public class DragSelectAction {
    private Node node;
    private MainUIController mainUIController;
    private Rectangle selectRectangle;
    private boolean isDragged;

    public DragSelectAction(Node node, MainUIController mainUIController) {
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
        });//设置当前鼠标在该结点下按下的监听获取点击时的鼠标位置信息

        node.addEventHandler(MouseEvent.MOUSE_DRAGGED, (MouseEvent e) -> {
            this.isDragged = true;
            double nowX = e.getX();
            double nowY = e.getY();//当前鼠标位置信息
            double baseX = selectRectangle.getX();
            double baseY = selectRectangle.getY();//鼠标一开始点击时的位置信息

            selectRectangle.setX(min(baseX, nowX));
            selectRectangle.setY(min(baseY, nowY));
            selectRectangle.setHeight(abs(baseY - nowY));
            selectRectangle.setWidth(abs(baseX-nowX));
            //根据两个位置信息计算并生成不可见矩阵

            if(this.isDragged) {
                for(Node childrenNode:  mainUIController.getFlowPaneChildren()) {
                    if(childrenNode instanceof PictureNode) {
                        if(isRectOverlap((PictureNode)childrenNode)){
                            ((PictureNode)childrenNode).setSelected(true);
                        }else{
                            ((PictureNode)childrenNode).setSelected(false);
                        }
                    }
                }
            }//在拖拽的时候，不断判断有哪些图片结点在该矩阵内部
            selectRectangle.setX(baseX);
            selectRectangle.setY(baseY);
        });//设置当前鼠标在该结点下拖拽活动的监听以获取鼠标位置信息

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

            //图片和选择矩阵的判断
            if(this.isDragged) {
                mainUIController.clearSelected();
                for(Node childrenNode:  mainUIController.getFlowPaneChildren()) {
                    if(childrenNode instanceof PictureNode) {
                        if(isRectOverlap((PictureNode)childrenNode))
                            ((PictureNode)childrenNode).setSelected(true);
                    }
//
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
    }//当宽度高度有一半在矩阵范围内则设置这些图片结点被选中

}