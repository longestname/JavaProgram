package master;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class KeyString {//这个是获取的键盘按键的类

    private StringProperty str;

    public final String getStr() {
        return strProperty().get();
    }

    public final StringProperty strProperty() {
        if(str == null)
            str = new SimpleStringProperty("");
        return str;
    }

    public final void setStr(String str) {
        this.strProperty().set(str);
    }
}
