package model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Window;

import java.util.Optional;

//MyAlert类作为当用户需用确认是否进行某操作时的弹窗模型
public class MyAlert {
    public static boolean showAlert(String p_header, String p_message, Window stage){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION,p_message,new ButtonType("取消", ButtonBar.ButtonData.NO),
                new ButtonType("确定",ButtonBar.ButtonData.YES));
        alert.setTitle("提醒");
        alert.setHeaderText(p_header);
        alert.initOwner(stage);
        Optional<ButtonType> buttonType=alert.showAndWait();

        if (buttonType.get().getButtonData().equals(ButtonBar.ButtonData.YES)) {
            return true;
        }
        else {
            return false;
        }
    }

}
