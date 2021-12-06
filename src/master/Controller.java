package master;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.beans.PropertyVetoException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller extends myTools implements Initializable {

    @FXML
    private TableView<ShortCut> tableview;
    @FXML
    private TableColumn<ShortCut, String> DescriptionCol;
    @FXML
    private TableColumn<ShortCut, String> KeyCol;
    @FXML
    private TableColumn<ShortCut, String> StrsCol;
    @FXML
    private TableColumn<ShortCut, String> OperationCol;
    @FXML
    private TextField addDescription;
    @FXML
    private TextField addKey;
    @FXML
    private TextField addStrs;
    @FXML
    private TextField addOperation;

    Action action = new Action();   //对即将的操作
    ShortCut shortCut, temp;        //shortCut为监听插入的对象，temp为复制对象
    KeyString str = new KeyString();//获取的键盘按键

    //关于键盘的操作
    private boolean flag = false;   //判断是否敲到的特定前缀，如Alt
    private String tempKey;         //暂时记录前缀
    private int cnt = 0;            //判断获取键盘键的个数，大于20时清零

    public Controller() throws UnknownHostException {
    }


    @Override
    //桌面初始化
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        KeyCol.setCellValueFactory(new PropertyValueFactory<>("Key"));
        StrsCol.setCellValueFactory(new PropertyValueFactory<>("Strs"));
        OperationCol.setCellValueFactory(new PropertyValueFactory<>("Operation"));


        //当键盘获取的字符更新时
        str.strProperty().addListener((observableValue, s, t1) -> {
            //这里不用anymatch是因为要顺便获取对应的操作
            //感觉这里不能用监听，因为可能有一样的动作
            for(ShortCut ss : shortcutList.stream().filter(t -> t.getKey().equals(tempKey)).toList())
                if(ss.getStrs().equals(str.getStr())){
                    action.setOps(ss.getOperation(),ss.getDescription());
                    System.out.println("yes");
                    str.setStr(""); cnt = 0; flag = false;
                }
            cnt++;
        });

        //对插入的值加入约束条件
        shortCut = new ShortCut();
        shortCut.addChangeDesListener(new descriptionListener());
        shortCut.addChangeKeyListener(new keyListener());
        shortCut.addChangeStrsListener(new strsListener());
        shortCut.addChangeOpListener(new operationListener());
    }

    //插入新的条目
    public void clickAddButton(){
        try {
            if(Objects.equals(addDescription.getText(), "") || Objects.equals(addKey.getText(), "")
                    || Objects.equals(addStrs.getText(), "") || Objects.equals(addOperation.getText(), ""))
            {showAlert("错误信息","不能留空");}
            else if(shortcutList.stream().filter(s -> s.getKey().equals(addKey.getText()))
                    .anyMatch(s -> s.getStrs().equals(addStrs.getText().toUpperCase())))
            {showAlert("错误信息","不能有重复的");}
            else{
                shortCut.setDescription(addDescription.getText());
                shortCut.setKey(addKey.getText());
                shortCut.setStrs(addStrs.getText().toUpperCase());
                shortCut.setOperation(addOperation.getText());
                temp = (ShortCut) shortCut.clone();
                shortcutList.add(temp);
                new connectDB().insert(temp);
                addDescription.clear();
                addKey.clear();
                addStrs.clear();
                addOperation.clear();
            }
        } catch (CloneNotSupportedException | PropertyVetoException e) {
            System.out.println("有错误");
        }
    }

    //删除所选中的项目
    public void remove() {
        int index = tableview.getSelectionModel().getSelectedIndex();
        if (index != -1) {
            connectDB db = new connectDB();
            db.delete(shortcutList.get(index).getKey(),shortcutList.get(index).getStrs());
            shortcutList.remove(index);
        }
    }

    //按下按键时检测
    public void pressKey(KeyEvent event) {
        if(flag) {
            System.out.println("Press=");
            System.out.println(event.getCode().getName());
            str.setStr(str.getStr() + event.getCode().getName());
            System.out.println("现在是" + str.getStr());
        }
        if(FrontList.contains(event.getCode().getName())){//如果存在
            str.setStr(""); cnt = 0; flag = true;
            tempKey = event.getCode().getName();
        }
        if(cnt == 20){
            str.setStr(""); cnt = 0; flag = false;
        }
    }


    public void setMain() {
        //主体
        tableview.setItems(shortcutList);
        tableview.getSelectionModel().select(0);
    }

}
