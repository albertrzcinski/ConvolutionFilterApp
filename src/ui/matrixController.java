package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class matrixController {

    @FXML
    TextField tF00;

    @FXML
    TextField tF01;

    @FXML
    TextField tF02;

    @FXML
    TextField tF10;

    @FXML
    TextField tF11;

    @FXML
    TextField tF12;

    @FXML
    TextField tF20;

    @FXML
    TextField tF21;

    @FXML
    TextField tF22;

    @FXML
    Button closeButton;

    @FXML
    public void cancel()
    {
        Stage stage = (Stage)closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void setMatrix()
    {
        try {
            double[][] filterTab = new double[][]{
                    {Double.parseDouble(tF00.getText()), Double.parseDouble(tF01.getText()), Double.parseDouble(tF02.getText())},
                    {Double.parseDouble(tF10.getText()), Double.parseDouble(tF11.getText()), Double.parseDouble(tF12.getText())},
                    {Double.parseDouble(tF20.getText()), Double.parseDouble(tF21.getText()), Double.parseDouble(tF22.getText())}
            };

            Convolution.filterTab = filterTab;
            ;

            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        }catch(Exception e){
            new Alert(Alert.AlertType.ERROR, "Złe dane! Wypełnij matrycę ponownie.").showAndWait();
        }
    }
}
