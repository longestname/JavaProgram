package master;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

//根据给定的操作进行相应的操作
//增加操作只要把相应名字加入数据库并在这里实现相关操作即可
public class Action extends myTools {
    private InetAddress address;

    Action() throws UnknownHostException {
        address = InetAddress.getLocalHost();
    }

    //开始操作
    //一开始没打算写浏览功能，表中少了一个数据列，现在只能把描述当成浏览的地址了
    public void setOps(String ops, String des) {
        int index = OpList.indexOf(ops);
        switch (index){
            case 0:showTime();break;
            case 1:showIp();break;
            case 2:showHostName();break;
            case 3:browseWeb(des);break;
            case 4:openLocal(des);break;
        }
    }


    //显示时间
    public void showTime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        showAlert("信息窗口","当前的时间为"+df.format(new Date()));
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
    }

    //显示ip地址
    public void showIp(){
        showAlert("信息窗口","IP地址为"+address.getHostAddress());
    }

    //显示用户名
    public void showHostName(){
        showAlert("信息窗口","本地名称为"+address.getHostName());
    }

    //用浏览器打开网页
    //重写catch了错误
    public void browseWeb(String s){
        try{
            URI uri = URI.create(s);
            Desktop dp = Desktop.getDesktop();
            dp.browse(uri);
        } catch (UnsupportedOperationException a){
            showAlert("错误信息","此电脑不支持此功能");
            a.printStackTrace();
        } catch (IOException e) {
            showAlert("错误信息","有其他问题");
            e.printStackTrace();
        } catch (IllegalArgumentException t){
            showAlert("错误信息","网址格式错误");
            t.printStackTrace();
        }
    }


    //打开电脑的特定文件
    public void openLocal(String s){
        try{
            Desktop dp = Desktop.getDesktop();
            dp.open(new File(s));
        } catch (UnsupportedOperationException a){
            showAlert("错误信息","此电脑不支持此功能");
            a.printStackTrace();
        } catch (IOException e) {
            showAlert("错误信息","文件有问题");
            e.printStackTrace();
        } catch (IllegalArgumentException t){
            showAlert("错误信息","找不到此文件");
            t.printStackTrace();
        }
    }
}
