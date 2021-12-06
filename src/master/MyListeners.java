package master;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;

public class MyListeners {

}

//这是监听数据插入是否正确，同时给出怎么判断
//监听描述（虽然没什么用）
class descriptionListener extends myTools implements VetoableChangeListener{
    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        String strDes = evt.getNewValue().toString();
        if(strDes.equals("just a test")){
            throw new PropertyVetoException("测试des",evt);
        }
    }
}

//监听前缀
class keyListener extends myTools implements VetoableChangeListener{
    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        String strKey = evt.getNewValue().toString();
        if(!FrontList.contains(strKey)){
            showAlert("信息","请检查前缀");
            throw new PropertyVetoException("测试key", evt);
        }
    }
}

//监听快捷键
class strsListener extends myTools implements VetoableChangeListener{
    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        String strStr = evt.getNewValue().toString();
        if(strStr.length() > 20){
            showAlert("信息","快捷键不能超过20");
            throw new PropertyVetoException("测试strs",evt);
        }
        if(!isAccessibleStr(strStr)){
            showAlert("信息","不能有除数字字母外的符号");
            throw new PropertyVetoException("测试strs",evt);
        }
    }
}

//监听对应的操作
class operationListener extends myTools implements VetoableChangeListener{
    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        String strOp = evt.getNewValue().toString();
        System.out.println(strOp);
        if(!OpList.contains(strOp)){
            showAlert("信息","该操作暂时不可实现");
            throw new PropertyVetoException("测试ops",evt);
        }
    }
}
