package action;

import controller.MainUIController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.PictureFile;
import model.PictureNode;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class RenameAction {

    private VBox root;
    private MainUIController mainUIController;//控制器
    private boolean single;//是否对单张图片进行重命名
    private Stage anotherStage;//创建新的舞台
    private Label msg;//提醒用户重命名失败
    private Button submit;//确认按钮
    private GridPane gridPane;
    final private TextField name=new TextField();//用户填写新的名字
    final private TextField startNum=new TextField();//多张图片进行重命名时，用户填写第一张图片开始的标号
    final private TextField bitNum=new TextField();//填写重命名数字位数,说实话这个也要美化一下
    public RenameAction(MainUIController mainUIController){
        this.mainUIController = mainUIController;
        if(mainUIController.getSelectedPictures().size()==1){
            single=true;
        }else {
            single=false;
        }
        anotherStage=new Stage();
        gridPane=new GridPane();
        msg=new Label();
        submit=new Button("完成");
        setStage();//布置这个舞台
    }

    private void setStage(){
        if(single){
            setSingleStage();
        }else{
            setMultipleStage();
        }

        submit.setOnAction(new EventHandler<ActionEvent>() {//当用户确认输入后
            @Override
            public void handle(ActionEvent actionEvent) {
                if(single){//单张图片重命名
                    if(name.getText()!=null&&!name.getText().isEmpty()){
                        if(renameSingle()){
                            anotherStage.close();
                        }else{
                            msg.setText("重命名失败");
                        }
                    }else{//当用户没有输入任何信息时进行提醒
                        msg.setText("请填写完整信息");
                    }
                }else{//多张图片重命名
                    if((name.getText()!=null&&!name.getText().isEmpty())
                        &&(startNum.getText()!=null&&!startNum.getText().isEmpty())
                        &&(bitNum.getText()!=null&&!bitNum.getText().isEmpty())){
                        if(renameMultiple()){
                            anotherStage.close();
                        }else{
                            msg.setText("重命名失败");
                        }
                    }else{
                        msg.setText("请填写完整信息");
                    }
                }
            }
        });
        Scene scene=new Scene(root);
        anotherStage.setTitle("重命名");
        anotherStage.setScene(scene);
        anotherStage.setResizable(false);
        anotherStage.show();
    }

    private void setMultipleStage() {//生成多张图片重命名的窗口
        root=new VBox();
        root.setPadding(new Insets(10,10,10,10));
        name.setPromptText("请输入新的名字");
        name.setPrefColumnCount(15);
        name.getText();
        Label l1=new Label("名称");
        HBox hBox1=new HBox(35,l1,name);
        hBox1.setPadding(new Insets(5,5,8,0));

        Label l2=new Label("起始编号");
        startNum.setPromptText("请输入起始编号");
        startNum.setPrefColumnCount(15);
        startNum.getText();
        HBox hBox2=new HBox(10,l2,startNum);
        hBox2.setPadding(new Insets(5,5,8,0));

        Label l3=new Label("编号位数");
        bitNum.setPromptText("请输入编号位数");
        bitNum.setPrefColumnCount(15);
        bitNum.getText();
        HBox hBox3=new HBox(10,l3,bitNum);
        hBox3.setPadding(new Insets(5,5,8,0));

        msg.setPadding(new Insets(0,0,8,0));
        root.getChildren().addAll(hBox1,hBox2,hBox3,msg,submit);
    }

    private boolean renameMultiple(){
        File file;
        int id=Integer.valueOf(startNum.getText());//获取用户输入的图片结点起始编号
        int bit=Integer.valueOf(bitNum.getText());//获取用户输入的图片结点数字编号的位数
        if(id<0||(id+mainUIController.getSelectedPictures().size())>=(int)Math.pow(10,bit))
            return false;//当输入的起始编号小于0或者数字编号位数小于最后一张图片结点的编号位数时，重命名失败
        ArrayList<PictureNode> oldList=new ArrayList<PictureNode>();
        ArrayList<PictureNode> newList=new ArrayList<PictureNode>();
        for(PictureNode picture :mainUIController.getSelectedPictures()){
            file=picture.getImageFile();
            String pre=file.getParent();
            String[] strings=file.getName().split("\\.");
            String suf=strings[strings.length-1];
            String newName=createName(id,bit);//生成新的名称
            File tmp=new File(pre+"\\"+newName+"."+suf);
            //生成重命名后的新文件
            if(!file.renameTo(tmp)){
                tmp.delete();
                return false;
            }//如果重命名失败，删除生成的新文件
            oldList.add(picture);
            try {
                PictureNode newPicture=new PictureNode(mainUIController,new PictureFile(tmp));
                //为该新图片文件生成图片结点
                newList.add(newPicture);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            ++id;//同时增加之后的图片结点的数字编号
        }
        for(int i=0;i<oldList.size();++i){
            oldList.get(i).setSelected(false);//设置旧的图片结点为为选中状态
            mainUIController.removePictures(oldList.get(i));//并且把这些图片结点删除
            newList.get(i).setSelected(true);//添加新的重命名好的图片结点
            mainUIController.addPictures(newList.get(i));
        }
        mainUIController.showPicture();//刷新图片预览部分的图片结点
        return true;
    }

    private String createName(int id,int bit){
        String newName =name.getText();
        int tt=id;
        int cnt=0;
        while(tt!=0){
            ++cnt;
            tt/=10;
        }
        if(id==0)
            cnt++;
        while(bit>cnt){
            newName+=0;
            cnt++;
        }
        newName+=id;
        return newName;
    }//用于生成新名字后的数字编号

    private void setSingleStage(){//生成单张图片重命名的窗口
        root=new VBox();
        root.setPadding(new Insets(10,10,10,10));
        Label label1=new Label("名称");
        name.setPromptText("请输入新的名字");
        name.setPrefColumnCount(15);
        name.getText();
        HBox hBox1=new HBox(10,label1,name);
        HBox hBox2=new HBox();
        hBox2.setPadding(new Insets(5,5,8,0));
        hBox2.getChildren().add(msg);
        HBox hBox3=new HBox();
        hBox3.setPadding(new Insets(5,5,0,0));
        root.getChildren().addAll(hBox1,hBox2,hBox3,msg,submit);

    }
    private boolean renameSingle(){//单张图片的重命名功能
        PictureNode pNode=mainUIController.getSelectedPictures().get(0);
        File file=pNode.getImageFile();
        String pre=file.getParent();
        String[] strings=file.getName().split("\\.");
        String suf=strings[strings.length-1];
        File tmp=new File(pre+"\\"+name.getText()+"."+suf);
        if(!file.renameTo(tmp)){
            tmp.delete();
            return false;
        }
        pNode.setSelected(false);
        mainUIController.removePictures(pNode);
        try {
            PictureNode tNode=new PictureNode(mainUIController,new PictureFile(tmp));
            tNode.setSelected(true);
            mainUIController.getPictures().add(tNode);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        mainUIController.showPicture();
        return true;
    }


}