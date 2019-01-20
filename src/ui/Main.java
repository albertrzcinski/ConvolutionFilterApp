package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;

public class Main extends Application {

    public static Stage pStage;
    public static BufferedImage bufferedImage;
    public static Image image;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        pStage=primaryStage;
        pStage.setTitle("Convolution filter");
        pStage.setScene(new Scene(root, 920, 550));
        pStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
