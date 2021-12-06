package master;

import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;

//一个快捷键的类
//可能要一直监听就只能复制一个在加入列表
public class ShortCut implements Cloneable{

    private String Description="";  //描述
    private String Key="";          //前缀
    private String Strs ="";        //快捷键
    private String Operation="";    //对应的操作
    private final VetoableChangeSupport desListener = new VetoableChangeSupport(this.Description);
    private final VetoableChangeSupport keyListener = new VetoableChangeSupport(this.Key);
    private final VetoableChangeSupport strsListener = new VetoableChangeSupport(this.Strs);
    private final VetoableChangeSupport opListener = new VetoableChangeSupport(this.Operation);

    ShortCut(){
    }

    ShortCut(String des, String key, String strs, String op){
        this.Description = des;     this.Key = key;
        this.Strs = strs;           this.Operation = op;
    }


    //添加约束
    public void addChangeDesListener(VetoableChangeListener listener){
        desListener.addVetoableChangeListener(listener);
    }
    public void addChangeKeyListener(VetoableChangeListener listener){
        keyListener.addVetoableChangeListener(listener);
    }
    public void addChangeStrsListener(VetoableChangeListener listener){
        strsListener.addVetoableChangeListener(listener);
    }
    public void addChangeOpListener(VetoableChangeListener listener){
        opListener.addVetoableChangeListener(listener);
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) throws PropertyVetoException{
        desListener.fireVetoableChange("Description",this.Description,description);
        this.Description = description;
    }

    //Getter and Setter
    public String getKey() {
        return Key;
    }

    public void setKey(String key) throws PropertyVetoException{
        keyListener.fireVetoableChange("Key",this.Key,key);
        this.Key = key;
    }

    public String getStrs() {
        return Strs;
    }

    public void setStrs(String strs) throws PropertyVetoException{
        strsListener.fireVetoableChange("Strs",this.Strs, strs);
        this.Strs = strs;
    }

    public String getOperation() {
        return Operation;
    }

    public void setOperation(String operation) throws PropertyVetoException{
        opListener.fireVetoableChange("Operation",this.Operation,operation);
        this.Operation = operation;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
