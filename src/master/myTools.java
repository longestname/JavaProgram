package master;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.List;

//我的工具类，用来继承
public class myTools {
    //可以接受的前缀
    public static List<String> FrontList = new ArrayList<>(){{
        add("Alt");  add("Shift");  add("Ctrl");
    }};
    //所有已有的ShortCut类
    public ObservableList<ShortCut> shortcutList = FXCollections.observableArrayList();
    //可以接受的操作
    public List<String> OpList = new ArrayList<>();

    myTools(){
        OpList.addAll(new connectDB().initOplist());
        shortcutList.addAll(new connectDB().initShortcut());
    }

    //判读是不是只是英文字母或数字
    public boolean isAccessibleStr(String str){
        String regex = "^[a-z0-9A-Z]+$";
        return str.matches(regex);
    }

    //弹出提示窗口
    public void showAlert(String title, String info){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.titleProperty().set(title);
        alert.headerTextProperty().set(info);
        alert.showAndWait();
    }

}
