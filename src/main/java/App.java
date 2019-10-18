import Interface.*;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.scene.control.TableView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.*;

import javafx.application.Platform;
import javafx.application.Application;

import java.util.*;
import java.util.List;

public class App extends Application {

    final int WINDOW_SIZE = 10; //numero de elementos en el grafico
    private ScheduledExecutorService scheduledExecutorService;

    public static Integer tiempo = 0;
    public static Integer delay = 1;
    public static Hashtable<Integer, Process> hashTable = new Hashtable<Integer, Process>();
    public static CPU cpu;

    public static void main(String[] args) {

        // Parameters
        Integer coreQty = Integer.parseInt(args[0]);  // core quantity
        Integer processQty = Integer.parseInt(args[1]);  // process quantity (0 for infinity process)
        Integer ioRange = Integer.parseInt(args[2]);  // io quantity per process
        Integer quantum =  Integer.parseInt(args[3]);  // time quantum
        Integer sleepTime = Integer.parseInt(args[4]);  // speed
        Boolean loop = false;

        if (processQty == 0) // infity process
            loop = true;

        Timer timer = new Timer(sleepTime);

        RBTree<Integer> tree = new RBTree<>();

        cpu = new CPU(quantum, coreQty, tree, timer);

        ProcessGenerator processGenerator = new ProcessGenerator(tree, timer, ioRange, loop, processQty, hashTable);
        Thread thread1 = new Thread(processGenerator);
        thread1.start();

        Dispatcher dispatcher = new Dispatcher(cpu, tree);
        Thread thread2 = new Thread(dispatcher);
        thread2.start();

        TimerThread timerThread = new TimerThread(timer);
        Thread thread3 = new Thread(timerThread);
        thread3.start();

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Planificador Linux");

        // Create Interface.Table
        TableView table = new Table().create();

        // Create Line Interface.Chart
        final LineChart lineChart = new Chart().create();
        XYChart.Series<String, Number> runSerie = new XYChart.Series<>();
        runSerie.setName("Process Running");
        XYChart.Series<String, Number> waitSerie = new XYChart.Series<>();
        waitSerie.setName("Process Waiting");
        XYChart.Series<String, Number> readySerie = new XYChart.Series<>();
        readySerie.setName("Process Ready");
        lineChart.getData().addAll(runSerie, waitSerie, readySerie);

        // List for process series
        List<Process> waitingL = new ArrayList<Process>();
        List<Process> readyL = new ArrayList<Process>();
        List<Process> finishedL = new ArrayList<Process>();

        // Create Cake Interface.Chart
        PieChart pieChart = new CakeChart().create();
        PieChart.Data used = new PieChart.Data("Used", 0);
        PieChart.Data unused = new PieChart.Data("Unused"  , 0);
        pieChart.getData().addAll(used,unused);

        // Split window in two parts
        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(table,pieChart);

        // Buttons
        HBox button = new CloseButton().create();

        // Create window
        VBox vbox = new VBox();
        VBox.setVgrow(lineChart, Priority.ALWAYS);//Make line chart always grow vertically
        vbox.getChildren().addAll(splitPane,lineChart,button);

        // setup scene
        Scene scene = new Scene(vbox, 1045, 1000);
        primaryStage.setScene(scene);

        // show the stage
        primaryStage.show();

        // setup a scheduled executor to periodically put data into the chart
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        // Add data to chart
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            
            Platform.runLater(() -> {

                table.getItems().clear();
                waitingL.clear();
                readyL.clear();
                finishedL.clear();

                // Add data to table
                Set<Integer> keys = hashTable.keySet();
                int n_waiting = 0;
                int n_ready = 0;
                for(Integer key: keys){
                    Process process = hashTable.get(key);

                    if (process.state.equals("Waiting")){
                        n_waiting++;
                        waitingL.add(process);
                    }
                    else if (process.state.equals("Running")) {
                        ProcessInterface row = new ProcessInterface("P0" + process.getPid() ,process.getPid(),process.state,process.runtime, process.vruntime, process.priority, process.iosHandled);
                        table.getItems().add(row);
                    }
                    else if (process.state.equals("Ready")) {
                        n_ready++;
                        readyL.add(process);
                    }
                    else if (process.state.equals("Finished")) {
                        finishedL.add(process);
                    }
                }

                for(int i = 0; i < waitingL.size(); i++){
                    Process process = waitingL.get(i);
                    ProcessInterface row = new ProcessInterface("P0" + process.getPid() ,process.getPid(),process.state,process.runtime, process.vruntime, process.priority, process.iosHandled);
                    table.getItems().add(row);
                }
                for(int i = 0; i < readyL.size(); i++){
                    Process process = readyL.get(i);
                    ProcessInterface row = new ProcessInterface("P0" + process.getPid() ,process.getPid(),process.state,process.runtime, process.vruntime, process.priority, process.iosHandled);
                    table.getItems().add(row);
                }
                for(int i = 0; i < finishedL.size(); i++){
                    Process process = finishedL.get(i);
                    ProcessInterface row = new ProcessInterface("P0" + process.getPid() ,process.getPid(),process.state,process.runtime, process.vruntime, process.priority, process.iosHandled);
                    table.getItems().add(row);
                }

                // Add data to pie chart
                int n_used = Collections.frequency(cpu.coresWorker.cores, true);
                used.setPieValue(n_used);
                int n_unused = Collections.frequency(cpu.coresWorker.cores, false);
                unused.setPieValue(n_unused);

                // Add data to line chart
                runSerie.getData().add(new XYChart.Data<>(tiempo.toString(), n_used));
                waitSerie.getData().add(new XYChart.Data<>(tiempo.toString(), n_waiting));
                readySerie.getData().add(new XYChart.Data<>(tiempo.toString(), n_ready));

                tiempo++;

                if (runSerie.getData().size() > WINDOW_SIZE)
                    runSerie.getData().remove(0); // quita el primer elemento del grafico si se llena
                if (waitSerie.getData().size() > WINDOW_SIZE)
                    waitSerie.getData().remove(0); // quita el primer elemento del grafico si se llena
                if (readySerie.getData().size() > WINDOW_SIZE)
                    readySerie.getData().remove(0); // quita el primer elemento del grafico si se llena

            });
        }, 0, delay, TimeUnit.SECONDS);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        scheduledExecutorService.shutdownNow();
    }
}
