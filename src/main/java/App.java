import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;

import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.SplitPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class App extends Application {

    final int WINDOW_SIZE = 10; //numero de elementos en el grafico
    private ScheduledExecutorService scheduledExecutorService;

    public static Integer tiempo = 0;
    public static Integer delay = 1;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Planificador Linux");

        // Create Table
        TableView table = new Table().create();

        // Create Line Chart
        LineChart lineChart = new Chart().create();
        XYChart.Series<Number, Number> runSerie = new XYChart.Series<>();
        runSerie.setName("Process Running");
        XYChart.Series<Number, Number> waitSerie = new XYChart.Series<>();
        waitSerie.setName("Process Waiting");
        lineChart.getData().addAll(runSerie, waitSerie);

        // Create Cake Chart
        PieChart pieChart = new CakeChart().create();
        PieChart.Data used = new PieChart.Data("Used", 0);
        PieChart.Data unused = new PieChart.Data("Unused"  , 0);
        pieChart.getData().addAll(used,unused);

        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(table,pieChart);

        // Buttons
        HBox button = new CloseButton().create();

        // Create window
        VBox vbox = new VBox();
        //VBox.setVgrow(spLineChart, Priority.ALWAYS);//Make line chart always grow vertically
        vbox.getChildren().addAll(splitPane,lineChart,button);

        // setup scene
        Scene scene = new Scene(vbox, 1000, 1000);
        primaryStage.setScene(scene);

        //TextField txtFld= new TextField();

        // show the stage
        primaryStage.show();

        // setup a scheduled executor to periodically put data into the chart
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        // Add data to chart
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            // Cantidad de procesos corriendo
            Integer random = ThreadLocalRandom.current().nextInt(10);
            Integer random2 = ThreadLocalRandom.current().nextInt(10);
            Integer random3 = ThreadLocalRandom.current().nextInt(100);

            Platform.runLater(() -> {

                // Add data to table
                ProcessInterface row = new ProcessInterface("iTunes",1234,"waiting",1234);
                table.getItems().add(row);

                // Add data to line chart
                runSerie.getData().add(new XYChart.Data<>(tiempo, random));
                waitSerie.getData().add(new XYChart.Data<>(tiempo, random2));

                // Add data to pie chart
                used.setPieValue(random);
                unused.setPieValue(random2);

                tiempo++;

                if (runSerie.getData().size() > WINDOW_SIZE)
                    runSerie.getData().remove(0); // quita el primer elemento del grafico si se llena
                if (waitSerie.getData().size() > WINDOW_SIZE)
                    waitSerie.getData().remove(0); // quita el primer elemento del grafico si se llena

            });
        }, 0, delay, TimeUnit.SECONDS);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        scheduledExecutorService.shutdownNow();
    }
}
