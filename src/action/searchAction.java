package action;

import com.sun.javafx.scene.control.inputmap.InputMap;
import controller.MainUIController;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.stage.Stage;
import main.Main;
import model.PictureFile;
import model.PictureNode;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class searchAction {
    public MainUIController mainUIController;
    private TextField searchText;
    private Alert warningAlert;
    private Alert infoAlert;
    public searchAction(MainUIController mainUIController, TextField searchText) {
        this.mainUIController=mainUIController;
        this.searchText=searchText;
        if(searchText.getText()!=null&&!searchText.getText().isEmpty()){
            if(search()){
                mainUIController.getText().setText("查询到"+mainUIController.searchedPicture.size()+"张图片");
            }else{
                mainUIController.getText().setText("查询到"+mainUIController.searchedPicture.size()+"张图片");
                infoAlert=new Alert(Alert.AlertType.INFORMATION);
                infoAlert.setTitle("查询结果");
                infoAlert.setContentText("没有找到相应名称的图片");
                infoAlert.show();
                //查找失败提醒
            }
        }else{
            warningAlert=new Alert(Alert.AlertType.WARNING);
            warningAlert.setTitle("提醒");
            warningAlert.setContentText("没有填写要查找图片名称");
            warningAlert.show();
            //提醒没有填写要查找图片的字段
        }
    }

    private boolean search(){
        if(mainUIController.searchedPicture.size()>0){
            for(PictureNode pNode: mainUIController.searchedPicture){
                pNode.getImageView().setEffect(null);
                pNode.setSelected(false);
            }

            mainUIController.searchedPicture.clear();
        }
        boolean flag=false;
        String searchName=searchText.getText();
        int len1=searchName.length();
        ArrayList<PictureNode> pictures= mainUIController.pictures;
        for(PictureNode picture : pictures){
            String name=picture.getPictureName();
            int len2=name.length();
            boolean flag1=false;
            for(int i=0;i<len1;++i){
                for(int j=0;j<len2;++j){
                    if(searchName.charAt(i)==name.charAt(j)){
                        flag=true;
                        flag1=true;
                        picture.getImageView().setEffect(new ColorAdjust(0,0,0.5,0));
                        picture.setSelected(true);
                        mainUIController.searchedPicture.add(picture);
                        break;
                    }
                }
                if(flag1)
                    break;
            }
        }
        return flag;
    }
}
