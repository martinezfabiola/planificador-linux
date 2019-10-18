import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ProcessInterface {

    private StringProperty name;
    private IntegerProperty pid;
    private StringProperty state;
    private IntegerProperty cpu;
    private IntegerProperty priority;
    private IntegerProperty vruntime;
    private IntegerProperty ios;

    ProcessInterface(String c1, Integer c2, String c3, Integer c4, Integer c5, Integer c6, Integer c7) {
        this.name = new SimpleStringProperty(c1);
        this.pid = new SimpleIntegerProperty(c2);
        this.state = new SimpleStringProperty(c3);
        this.cpu = new SimpleIntegerProperty(c4);
        this.vruntime = new SimpleIntegerProperty(c5);
        this.priority = new SimpleIntegerProperty(c6);
        this.ios = new SimpleIntegerProperty(c7);
    }

    public StringProperty nameProperty() { return name; }

    public IntegerProperty pidProperty() { return pid; }

    public StringProperty stateProperty() { return state; }

    public IntegerProperty cpuProperty() { return cpu; }

    public IntegerProperty priorityProperty() { return priority; }

    public IntegerProperty vruntimeProperty() { return vruntime; }

    public IntegerProperty iosProperty() { return ios; }
}
