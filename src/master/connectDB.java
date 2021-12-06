package master;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;


public class connectDB {

    //数据库所处的位置
    private String Location = "jdbc:sqlite:D:\\shortcuts.db";
    //存放快捷键信息的表(Description,Key,Strs,Operation)
    private String DataTable = "first";
    //存放对应操作信息的表(ops)
    private String OpTable = "oplist";

    //插入数据
    public void insert(ShortCut s) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(Location);
            Statement statement = connection.createStatement();
            String sql="insert into "+DataTable+"(Description,Key,Strs,Operation) " +
                    "values('"+s.getDescription()+"','"+s.getKey()+"','"+s.getStrs()+"','"+s.getOperation()+"')";
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    //删除（通过查找前缀和快捷键）
    public void delete(String Key, String Strs){
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(Location);
            Statement statement = connection.createStatement();
            statement.executeQuery("delete from first where Key = '"+Key+"' and Strs = '"+Strs+"'");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
            }
        }
    }

    //初始化时把原有的数据导出
    public List<ShortCut> initShortcut(){
        List<ShortCut> shortCuts = new ArrayList<>();
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(Location);
            Statement statement = connection.createStatement();
            //这里顺便排序
            ResultSet rs = statement.executeQuery("SELECT Description,Key,Strs,Operation FROM "+DataTable+" order by Key,Strs");
            while(rs.next()){
                shortCuts.add(new ShortCut(rs.getString("Description"),rs.getString("Key")
                        ,rs.getString("Strs"),rs.getString("Operation")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        return shortCuts;
    }

    //初始化时把操作的导出
    public List<String> initOplist(){
        List<String> strings = new ArrayList<>();
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(Location);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT ops FROM "+OpTable);
            while(rs.next()){
                strings.add(rs.getString("ops"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        return strings;
    }

}
