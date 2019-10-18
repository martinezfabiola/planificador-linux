import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.geometry.Insets;

public class Table {

    public static TableView create(){

        TableView tableView = new TableView();
        tableView.setPrefSize( 100, 500 );
        tableView.setMaxWidth(750);
        tableView.setPadding(new Insets(10));

        TableColumn nameCol = new TableColumn();
        nameCol.setText("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));

        TableColumn pidCol = new TableColumn();
        pidCol.setText("pid");
        pidCol.setCellValueFactory(new PropertyValueFactory("pid"));

        TableColumn stateCol = new TableColumn();
        stateCol.setText("State");
        stateCol.setCellValueFactory(new PropertyValueFactory("state"));

        TableColumn cpuTimeCol = new TableColumn();
        cpuTimeCol.setText("CPU Time");
        cpuTimeCol.setCellValueFactory(new PropertyValueFactory("cpu"));

        TableColumn priorityCol = new TableColumn();
        priorityCol.setText("Priority");
        priorityCol.setCellValueFactory(new PropertyValueFactory("priority"));

        TableColumn vruntimeCol = new TableColumn();
        vruntimeCol.setText("Vruntime");
        vruntimeCol.setCellValueFactory(new PropertyValueFactory("vruntime"));

        TableColumn iosCol = new TableColumn();
        iosCol.setText("IO Qty");
        iosCol.setCellValueFactory(new PropertyValueFactory("ios"));

        tableView.getColumns().addAll(nameCol, pidCol, stateCol, cpuTimeCol, vruntimeCol, priorityCol, iosCol);

        return tableView;
    }
}
