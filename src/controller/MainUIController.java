package controller;
import action.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import action.DragImageAction;
import action.DragSelectAction;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.scene.control.TreeItem.valueChangedEvent;

//主页面FXML的控制器
public class MainUIController implements Initializable {

    public ArrayList<PictureNode> selectedPictures = new ArrayList<PictureNode>();//用来存放现在选中的图片节点，在图片被选中的时候添加到这个数组中
    public ArrayList<File> selectedPictureFiles = new ArrayList<>();//用来放置被选择的图片文件，用于放入系统复制粘贴的面板中，这个是一个临时的存放图片节点文件的数组，每次用完要清空
    public ArrayList<PictureNode> cutedPictures=new ArrayList<>();//用来存放被进行剪切操作的图片
    public ArrayList<PictureNode> searchedPicture;//被搜索到的图片
    private MainUIController mainUIController;
    public ArrayList<PictureNode> pictures;
    private ArrayList<File> files;
    //private ArrayList<PictureNode>
    public static String theFilePath;
    @FXML
    private TextField findPictureField;
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
    private TextField searchText;

    @FXML
    private void searchBtnAction(ActionEvent event) {
        new SearchAction(this.mainUIController,searchText);
    }
    @FXML
    private void uploadImage(ActionEvent event) {
        new UploadImageAction(this.mainUIController);
    }
    @FXML
    private void openBtnAction(ActionEvent event) {
        new OpenAction(0);
    }

    @FXML
    private void copyBtnAction(ActionEvent event) {
        new CopyAction(mainUIController);
    }

    @FXML
    private void pasteBtnAction(ActionEvent event) {
        new PasteAction(this);
    }
    @FXML
    private void deleteBtnAction(ActionEvent event) {
        new DeleteAction(this);
    }
    @FXML
    private void PPTAction(ActionEvent event) {
        new PPTAction(0,false);
    }

    public MainUIController(){
        mainUIController=this;
    }

    public void FindPictureAction(TreeView<PictureFile> leafView){
        findPictureField.setOnAction(event -> {
            //System.out.println("Search");
            String fileType = "";
            String path = findPictureField.getText();
            fileType = path.substring(path.lastIndexOf(":")-1);
            new FindPictureAction(fileType,leafView);
        });
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initData();
    }
    private void initData(){
        pictures=new ArrayList<>();
        searchedPicture=new ArrayList<>();
        files=new ArrayList<>();
        treeView=new FileTree(mainUIController,treeView).getTreeView();
        FindPictureAction(treeView);
        new DragSelectAction(flowPane,this);
        new MyContextMenu(flowPane,this,false);
        ScreenAdaptation();
        openBtn.setTooltip(new Tooltip("打开"));
        copyBtn.setTooltip(new Tooltip("复制"));
        pasteBtn.setTooltip(new Tooltip("粘贴"));
        deleteBtn.setTooltip(new Tooltip("删除"));
        PPt.setTooltip(new Tooltip("幻灯片播放"));
        searchText.setPromptText("搜索图片");
        new DragImageAction(this.mainUIController);
        ImageViewController.index=0;
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
    }

    public void addPictureFiles(PictureNode pNode){
        files.add(pNode.getPictureFile().getImageFile());
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

    public ArrayList<File> getFiles(){
        return this.files;
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
                pictureBox.setPrefHeight(height-topToolBar.getPrefHeight()-toolBar.getPrefHeight());
                flowPane.setPrefHeight(pictureBox.getPrefHeight());
            }
        });
    }




    public ArrayList<File> getSelectedPictureFiles(){
        return selectedPictureFiles;
    }//获取被选中要复制的图片文件

    public ArrayList<PictureNode> getSelectedPictures(){
        return selectedPictures;
    }//获取被选中的图片结点
    public void clearSelected(){
        for(PictureNode pNode:selectedPictures){
            pNode.selected.set(false);
        }
        selectedPictures.removeAll(selectedPictures);
        this.getText().setText("已选中 "+selectedPictures.size()+"张图片");
    }//清除所有被选中的图片结点

    public void setCutedPictures(ArrayList<PictureNode> cutedPictures){
        this.cutedPictures = cutedPictures;
    }
    public void addCutedPictures(PictureNode pNode){
        cutedPictures.add(pNode);
    }
    public void clearCutedPictures(){
        cutedPictures.removeAll(cutedPictures);
    }

    public ArrayList<PictureNode> getCutedPictures(){
        return cutedPictures;
    }
}
