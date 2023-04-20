package model;

import action.*;

import controller.MainUIController;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

import java.io.File;
import java.util.List;

//
public class MyContextMenu {
    MainUIController mainUIController;
    ContextMenu contextMenu;

    public MyContextMenu(Node node,MainUIController mainUIController,boolean choice){
        this.mainUIController = mainUIController;
        this.contextMenu = new ContextMenu();
        if(choice){
            PictureMenu(node);
        }else if(node!=null){
            nullMenu(node);
        }
    }

    private void PictureMenu(Node node){
        contextMenu=new ContextMenu();
        MenuItem open=new MenuItem("打开");
        MenuItem copy=new MenuItem("复制");
        MenuItem cut=new MenuItem("剪切");
        MenuItem delete=new MenuItem("删除");
        MenuItem rename=new MenuItem("重命名");
        contextMenu.getItems().addAll(open,copy,cut,delete,rename);

        open.setOnAction(e-> new OpenAction(0));

        copy.setOnAction(e-> new CopyAction(mainUIController));

        cut.setOnAction(e-> new CutAction(mainUIController));

        delete.setOnAction(e-> new DeleteAction(this.mainUIController));


        rename.setOnAction(e-> new RenameAction(this.mainUIController));
        node.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            if(e.getButton()==MouseButton.SECONDARY){
                contextMenu.show(node,e.getScreenX(),e.getScreenY());
            }else{
                if(contextMenu.isShowing())
                    contextMenu.hide();
            }
        });
    }

    private void nullMenu(Node node){
        ContextMenu mouseRightClickMenu=new ContextMenu();
        MenuItem paste=new MenuItem("粘贴");
        MenuItem all=new MenuItem("全选");
        mouseRightClickMenu.getItems().add(paste);
        mouseRightClickMenu.getItems().add(all);

        paste.setOnAction(e-> new PasteAction(this.mainUIController));

        all.setOnAction(e->{
            for(Node childrenNode: mainUIController.getFlowPane().getChildren()){
                if(childrenNode instanceof PictureNode){
                    ((PictureNode)childrenNode).setSelected(true);
                }
            }
        });
        node.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            Node clickNode = e.getPickResult().getIntersectedNode();
            if (clickNode instanceof FlowPane){// && !(clickNode instanceof PictureNode) && !(clickNode instanceof Text)){//&&mainUIController.getPictures().size()>0) {// 鼠标点击非图片节点
                mainUIController.clearSelected();// 如果选中的是空白区域清空已选
                //System.out.println("asd");
                if(mainUIController.getPictures().size()>0){
                    for(PictureNode pNode: mainUIController.searchedPicture)
                        pNode.getImageView().setEffect(null);
                    mainUIController.searchedPicture.clear();
                }
                if (e.getButton() == MouseButton.SECONDARY) {// 鼠标右键
                    Clipboard clipboard = Clipboard.getSystemClipboard();
                    List<File> files = (List<File>) (clipboard.getContent(DataFormat.FILES));
                    paste.setDisable(files.size() <= 0);
                    mouseRightClickMenu.show(node, e.getScreenX(), e.getScreenY());
                } else {
                    if (mouseRightClickMenu.isShowing()) {
                        mouseRightClickMenu.hide();
                    }
                }
            } else {
                if (mouseRightClickMenu.isShowing()) {
                    mouseRightClickMenu.hide();
                }
            }
        });
    }

}
