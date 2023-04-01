package controller;
import action.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Window;
import main.Main;
import model.MyContextMenu;
import model.PictureFile;
import model.FileTree;
import model.PictureNode;
import service.ChangeService;
import service.DragSelect;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

//主页面FXML的控制器
public class MainUIController implements Initializable {
    private MainUIController mainUIController;
    private ArrayList<PictureNode> pictures;
    private ArrayList<File> files;
    public static String theFilePath;
    @FXML
    private Button uploadImageBtn;
    @FXML
    private Button searchBtn;
    @FXML
    private ToolBar toolBar;

    @FXML
    private ToolBar topToolBar;
    @FXML
    private HBox bottomText;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox pictureBox;
    @FXML
    private TreeView<PictureFile> treeView;
    @FXML
    private Button copyBtn;
    @FXML
    private FlowPane flowPane;
    @FXML
    private Button PPt;
    @FXML
    private Button openBtn;
    @FXML
    private Button pasteBtn;
    @FXML
    private Text text;
    @FXML
    private Button deleteBtn;

    @FXML
    void openBtnAction(ActionEvent event) {
        new OpenAction();
    }

    @FXML
    void copyBtnAction(ActionEvent event) {
        new CopyAction();
    }

    @FXML
    void pasteBtnAction(ActionEvent event) {
        new PasteAction(this);
    }
    @FXML
    void deleteBtnAction(ActionEvent event) {
        new DeleteAction(this);
    }
    @FXML
    void PPTAction(ActionEvent event) {
        new PPTAction();
    }

    public MainUIController(){
        mainUIController=this;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initData();
    }
    private void initData(){
        pictures=new ArrayList<>();
        treeView=new FileTree(mainUIController,treeView).getTreeView();
        new DragSelect(flowPane,this);
        new MyContextMenu(flowPane,this,false);
        ScreenAdaptation();
        openBtn.setTooltip(new Tooltip("打开"));
        copyBtn.setTooltip(new Tooltip("复制"));
        pasteBtn.setTooltip(new Tooltip("粘贴"));
        deleteBtn.setTooltip(new Tooltip("删除"));
        PPt.setTooltip(new Tooltip("幻灯片播放"));
    }


    /*
        getter;
    */
    @FXML
    private ScrollPane picturePane;
    public ArrayList<PictureNode> getPictures() {
        return pictures;
    }

    public FlowPane getFlowPane() {
        return flowPane;
    }

    public Text getText() {
        return text;
    }

    public ObservableList<Node> getFlowPaneChildren(){
        return flowPane.getChildren();
    }

    public void addPictures(PictureNode pNode){
        pictures.add(pNode);
    }

    public void showPicture(){
        flowPane.getChildren().remove(0,flowPane.getChildren().size());
        for(PictureNode pictureNode:pictures){
            flowPane.getChildren().add(pictureNode);
        }

        files=new ArrayList<File>();
        for (int i=0;i<pictures.size();++i){
            files.add(pictures.get(i).getImageFile());
        }
        ChangeService.files=files;
    }

    public void clearPictures(){
        flowPane.getChildren().remove(0,flowPane.getChildren().size());
        pictures.clear();
    }

    public void removePictures(PictureNode pNode){
        for(PictureNode pictureNode: pictures){
            if(pictureNode.equals(pNode)){
                pictures.remove(pNode);
                break;
            }
        }
    }

   /*private void flowPaneAddListener(){
        flowPane.addEventHandler(MouseEvent.MOUSE_CLICKED,new MouseEventHandler());
    }*/

    private void ScreenAdaptation(){
        Window mainStage= Main.mainStage;
        mainStage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                double width=t1.doubleValue();
                pictureBox.setPrefWidth(width-treeView.getPrefWidth());
            }
        });
        mainStage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                double height=t1.doubleValue();
                scrollPane.setPrefHeight(height-bottomText.getPrefHeight()-toolBar.getPrefHeight()-topToolBar.getPrefHeight());
            }
        });
    }

}
