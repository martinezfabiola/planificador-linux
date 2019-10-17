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

        // Create table
        TableView table = new Table().create();
        //add data to table
        for (int j = 0; j < 20; j++) {
            ProcessInterface row = new ProcessInterface("iTunes",1234,"waiting",1234);
            table.getItems().add(row);
        }

        // Line Chart
        LineChart lineChart = new Chart().create();
        XYChart.Series<Number, Number> runSerie = new XYChart.Series<>();
        runSerie.setName("Proceso corriendo");
        XYChart.Series<Number, Number> waitSerie = new XYChart.Series<>();
        waitSerie.setName("Proceso en espera");
        lineChart.getData().addAll(runSerie, waitSerie);

        // Cake Chart
        PieChart pieChart = new CakeChart().create();
        PieChart.Data used = new PieChart.Data("Used", 0);
        PieChart.Data unused = new PieChart.Data("Unused"  , 0);
        pieChart.getData().add(used);
        pieChart.getData().add(unused);

        // Buttons
//        Button buttonL = new Button("Disminuir");
//        buttonL.setOnMouseClicked((event)->{
//            if (delay > 1) {
//                delay--;
//            }
//            System.out.println(delay);
//        });
//
//        Button buttonP = new Button("Aumentar");
//        buttonP.setOnMouseClicked((event)->{
//            delay++;
//            System.out.println(delay);
//        });
//
//        // agregar botones al otro contenedor
//        TilePane spButton = new TilePane();
//        spButton.setAlignment(buttonP, Pos.CENTER);
//        spButton.setAlignment(buttonL, Pos.CENTER);
//        spButton.getChildren().add(buttonP);
//        spButton.getChildren().add(buttonL);
        
        // Create window
        VBox vbox = new VBox();
        //VBox.setVgrow(spLineChart, Priority.ALWAYS);//Make line chart always grow vertically
        vbox.getChildren().addAll(table, pieChart, lineChart);

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
                // Add data to line chart
                runSerie.getData().add(new XYChart.Data<>(tiempo, random));
                waitSerie.getData().add(new XYChart.Data<>(tiempo, random2));

                // Add data to pie chart
                used.setPieValue(random);
                unused.setPieValue(random2);
                tiempo++;
//                if (series.getData().size() > WINDOW_SIZE)
//                    series.getData().remove(0); // quita el primer elemento del grafico si se llena
//                if (series2.getData().size() > WINDOW_SIZE)
//                    series2.getData().remove(0); // quita el primer elemento del grafico si se llena
            });
        }, 0, delay, TimeUnit.SECONDS);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        scheduledExecutorService.shutdownNow();
    }
}
