import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CloseButton {
    public static HBox create(){
        HBox buttonBox = new HBox();
        Button button = new Button("Finish");
        button.setOnMouseClicked((event)->{
            Stage stage = (Stage) button.getScene().getWindow();
            stage.close();
        });
        buttonBox.getChildren().add(button);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setPadding(new Insets(20));

        return buttonBox;
    }
}
