import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.Group;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.Hashtable;
import java.util.Set;

public class CloseButton {
    public static HBox create(Hashtable<Integer, Process> hashTable){
        HBox buttonBox = new HBox();
        Button button = new Button("Finish");

            button.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {

                Set<Integer> keys = hashTable.keySet();
                Integer processFinished = 0;
                Integer ioTime = 0;
                Integer runtimeCPU = 0;

                for(Integer key: keys) {
                    Process process = hashTable.get(key);
                    ioTime = ioTime + process.iosHandled;
                    runtimeCPU = runtimeCPU + process.runtime;

                    if (process.state.equals("Finished")) {
                        processFinished ++;
                    }
                }

                Stage stage = (Stage) button.getScene().getWindow();
                stage.close();

                // Add line for each text
                Text firstLine = new Text();
                Text secondLine = new Text();
                Text thirdLine = new Text();

                // Add text to line
                firstLine.setText("Excuted Process: " + processFinished);
                secondLine.setText("IO Handled: " + ioTime);
                thirdLine.setText("CPU Time: " + runtimeCPU);

                firstLine.setX(50);
                firstLine.setY(50);
                secondLine.setX(50);
                secondLine.setY(80);
                thirdLine.setX(50);
                thirdLine.setY(110);

                Group root = new Group(firstLine, secondLine, thirdLine);

                // New window with Statistics
                Scene secondScene = new Scene(root, 400, 200);
                Stage newWindow = new Stage();
                newWindow.setTitle("Statistics");
                newWindow.setScene(secondScene);

                newWindow.show();

            }
        });
        buttonBox.getChildren().add(button);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setPadding(new Insets(20));

        return buttonBox;
    }
}
