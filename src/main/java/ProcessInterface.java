import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ProcessInterface {

    private StringProperty name;
    private IntegerProperty pid;
    private StringProperty state;
    private IntegerProperty cpu;

    ProcessInterface(String c1, Integer c2, String c3, Integer c4) {
        this.name = new SimpleStringProperty(c1);
        this.pid = new SimpleIntegerProperty(c2);
        this.state = new SimpleStringProperty(c3);
        this.cpu = new SimpleIntegerProperty(c4);
    }

    public StringProperty nameProperty() { return name; }

    public IntegerProperty pidProperty() { return pid; }

    public StringProperty stateProperty() { return state; }

    public IntegerProperty cpuProperty() { return cpu; }
}
