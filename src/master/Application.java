package master;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        mainWindow();
    }

    //显示主窗体
    public void mainWindow() {

        try {
            //装载视图
            FXMLLoader loader = new FXMLLoader(
                    Application.class.getResource("/resources/sample.fxml"));
            AnchorPane root = loader.load();

            //获取控制器引用
            Controller controller=loader.getController();
            //将主类对象引用传入控制器
            controller.setMain();

            //构建场景和舞台
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Java项目");
            primaryStage.show();
        } catch (IOException e) {
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
