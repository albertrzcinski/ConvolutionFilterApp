package ui;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Controller {

    @FXML
    Button bButton;

    @FXML
    Button gbButton;

    @FXML
    Button sButton;

    @FXML
    Button lButton;

    @FXML
    Button oiButton;

    @FXML
    Button siButton;

    @FXML
    ImageView imageView;

    @FXML
    Pane pane;

    @FXML
    TextField tfX;

    @FXML
    TextField tfY;

    @FXML
    TextField tfWidth;

    @FXML
    TextField tfHeight;

    BufferedImage bufferedImage = Main.bufferedImage;
    int height , width;
    int fx=0 , fy=0;
    Image image = Main.image;
    Rectangle rectangle;


    @FXML
    public void resetImage()
    {
        if(imageView.getImage()!=null) {
            imageView.setImage(image);
            bufferedImage = SwingFXUtils.fromFXImage(image,null);
        }
    }
    @FXML
    public void openImage() {
        try {
            FileChooser fileChooser = new FileChooser();
            File input = fileChooser.showOpenDialog(Main.pStage);
            image = new Image(input.toURI().toString());
            bufferedImage = SwingFXUtils.fromFXImage(image, null);

            height = bufferedImage.getHeight();
            width = bufferedImage.getWidth();

            imageView.setFitWidth(800);
            imageView.setFitHeight(500);

            if (bufferedImage.getHeight() > imageView.getFitHeight()
                    || bufferedImage.getWidth() > imageView.getFitWidth()) {
                imageView.setImage(image);
            } else {
                imageView.setFitHeight(bufferedImage.getHeight());
                imageView.setFitWidth(bufferedImage.getWidth());
                imageView.setImage(image);
            }
        }catch (NullPointerException e) {}
    }

    @FXML
    public void saveImage() {
        if(imageView.getImage()!=null) {
            FileChooser fileChooser = new FileChooser();
            File input = fileChooser.showSaveDialog(Main.pStage);
            if (input != null) {
                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(imageView.getImage(), null), "png", input);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    @FXML
    public void blurFilter() {
        if(imageView.getImage()!=null) {
            double[][] filterTab = new double[][]{
                    {1, 1, 1},
                    {1, 1, 1},
                    {1, 1, 1}
            };

            Convolution.filterTab = filterTab;
            bufferedImage = Convolution.transform(bufferedImage,height,width,fy,fx);
            imageView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        }
    }

    @FXML
    public void gaussianBlurFilter() {
        if(imageView.getImage()!=null) {
            double[][] filterTab = new double[][]{
                    {0,1,0},
                    {1,4,1},
                    {0,1,0}
            };

            Convolution.filterTab = filterTab;
            bufferedImage = Convolution.transform(bufferedImage,height,width,fy,fx);
            imageView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        }
    }

    @FXML
    public void sharpenFilter() {
        if(imageView.getImage()!=null) {
                    double[][] filterTab = new double[][]{
                    {0,-1,0},
                    {-1,5,-1},
                    {0,-1,0}
                };

            Convolution.filterTab = filterTab;
            bufferedImage = Convolution.transform(bufferedImage,height,width,fy,fx);
            imageView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        }
    }

    @FXML
    public void laplaceFilter() {
        if(imageView.getImage()!=null) {
            double[][] filterTab = new double[][]{
                    {-1,-1,-1},
                    {-1,8,-1},
                    {-1,-1,-1}
            };

            Convolution.filterTab = filterTab;
            bufferedImage = Convolution.transform(bufferedImage,height,width,fy,fx);
            imageView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        }
    }

    @FXML
    public void loadMatrixView() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("matrixView.fxml"));
        Stage stage = new Stage();
        stage.setTitle("New matrix");
        stage.setScene(new Scene(root, 300, 300));
        stage.show();
    }

    @FXML
    public void applyMatrix(){
        if(imageView.getImage()!=null) {
            bufferedImage = Convolution.transform(bufferedImage,height,width,fy,fx);
            imageView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        }
    }

    @FXML
    public void showRectangle(){
        try {
            if (rectangle != null) {
                pane.getChildren().remove(rectangle);
                rectangle = new Rectangle(Integer.parseInt(tfX.getText()),
                        Integer.parseInt(tfY.getText()), Integer.parseInt(tfWidth.getText()),
                        Integer.parseInt(tfHeight.getText()));
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.RED);
                pane.getChildren().add(rectangle);
            } else {
                rectangle = new Rectangle(Integer.parseInt(tfX.getText()),
                        Integer.parseInt(tfY.getText()), Integer.parseInt(tfWidth.getText()),
                        Integer.parseInt(tfHeight.getText()));
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.RED);
                pane.getChildren().add(rectangle);
            }
        }catch (Exception e){
                new Alert(Alert.AlertType.ERROR, "Złe dane!").showAndWait();
        }
    }

    @FXML
    public void confirmRectangle(){
        showRectangle();
        if(checkSize()) {
            width= (int) rectangle.getWidth();
            height= (int) rectangle.getHeight();
            fx = (int) rectangle.getX();
            fy = (int) rectangle.getY();
        }else {
            new Alert(Alert.AlertType.ERROR, "Obraz nie wczytany lub obszar wewnatrz prostokata wykracza poza obraz!").showAndWait();
        }
    }

    boolean checkSize(){
        if(bufferedImage!=null)
            return !((rectangle.getX()+ rectangle.getWidth()) > bufferedImage.getWidth() ||
                    (rectangle.getY()+ rectangle.getHeight()) > bufferedImage.getHeight());
        else
            return false;
    }

    @FXML
    public void clearAll(){
        resetImage();
        if(rectangle!=null)
            pane.getChildren().remove(rectangle);
    }
}