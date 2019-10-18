import javafx.scene.control.TableView;
import java.util.*;
public class ProcessList implements Runnable{

    Integer key;
    Process value;
    Hashtable<Integer, Process> hashTable;

    ProcessList(Integer pid, Process process){
        this.key = pid;
        this.value = process;
        this.hashTable = new Hashtable<Integer, Process>();
    }

    synchronized void secureInsert(){
        try {
            wait();
        } catch (InterruptedException e ) {
        }
        this.hashTable.put(this.key, this.value);
        notify();
    }

    public  void show(TableView table) {
        Set<Integer> keys = this.hashTable.keySet();
        for(Integer key: keys){
            Process process = hashTable.get(key);
            ProcessInterface row = new ProcessInterface("iTunes",process.getPid(),"waiting",1234);
            table.getItems().add(row);
        }
    }

    public void run(){
        while(true){
            secureInsert();
        }
    }
}
