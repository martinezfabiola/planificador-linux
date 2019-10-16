import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;
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

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class App extends Application {
    final int WINDOW_SIZE = 10; //numero de elementos en el grafico
    private ScheduledExecutorService scheduledExecutorService;
    public static Integer tiempo = 0;
    public static Integer delay = 1;

    public static void main(String[] args) {
        launch(args);
    }

    public static class Proceso {
        private StringProperty nombre;
        private StringProperty pid;
        private StringProperty estado;
        private StringProperty tCPU;

        private Proceso(String c1, String c2, String c3, String c4) {
            this.nombre = new SimpleStringProperty(c1);
            this.pid = new SimpleStringProperty(c2);
            this.estado = new SimpleStringProperty(c3);
            this.tCPU = new SimpleStringProperty(c4);
        }

        public StringProperty nombreProperty() { return nombre; }
        public StringProperty pidProperty() { return pid; }
        public StringProperty estadoProperty() { return estado; }
        public StringProperty tCPUProperty() { return tCPU; }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Planificador Linux");

        // Create table
        TableColumn nombreCol = new TableColumn();
        nombreCol.setText("Nombre");
        nombreCol.setCellValueFactory(new PropertyValueFactory("nombre"));
        TableColumn pidCol = new TableColumn();
        pidCol.setText("PID");
        pidCol.setCellValueFactory(new PropertyValueFactory("pid"));
        TableColumn estadoCol = new TableColumn();
        estadoCol.setText("Estado");
        estadoCol.setCellValueFactory(new PropertyValueFactory("estado"));
        TableColumn tCPUCol = new TableColumn();
        tCPUCol.setText("Tiempo de CPU");
        tCPUCol.setCellValueFactory(new PropertyValueFactory("tCPU"));


        TableView tableView = new TableView();
        tableView.setMaxWidth(500);
        for (int j = 0; j < 20; j++) {
            Proceso row = new Proceso("Jacob",     "Smith",    "jacob.smith@example.com", "ä" );
            tableView.getItems().add(row);
        }

        tableView.getColumns().addAll(nombreCol, pidCol, estadoCol, tCPUCol);

        // Cake Chart
        PieChart pieChart = new PieChart();
        pieChart.setMaxWidth(500);


        // Axis
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Tiempo recorrido");
        yAxis.setLabel("Cantidad de procesos");

        // Chart
        final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Cantidad de Procesos corriendo");
        lineChart.setAnimated(false); // cancelar animacion, se descuadra el grafico :/

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Proceso corriendo");

        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("Proceso en espera");

        lineChart.getData().addAll(series, series2);

        // Poner el grafico en un contenedor
        StackPane spLineChart = new StackPane();
        spLineChart.getChildren().add(lineChart);

        // Buttons
        Button buttonL = new Button("Disminuir");
        buttonL.setOnMouseClicked((event)->{
            if (delay > 1) {
                delay--;
            }
            System.out.println(delay);
        });

        Button buttonP = new Button("Aumentar");
        buttonP.setOnMouseClicked((event)->{
            delay++;
            System.out.println(delay);
        });

        // agregar botones al otro contenedor
        TilePane spButton = new TilePane();
        spButton.setAlignment(buttonP, Pos.CENTER);
        spButton.setAlignment(buttonL, Pos.CENTER);
        spButton.getChildren().add(buttonP);
        spButton.getChildren().add(buttonL);
        
        // Create window
        VBox vbox = new VBox();
        VBox.setVgrow(spLineChart, Priority.ALWAYS);//Make line chart always grow vertically
        vbox.getChildren().addAll(tableView, pieChart, spLineChart, spButton);

        // setup scene
        Scene scene = new Scene(vbox, 1000, 500);
        primaryStage.setScene(scene);

        TextField txtFld= new TextField();

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
                series.getData().add(new XYChart.Data<>(tiempo.toString(), random));
                series2.getData().add(new XYChart.Data<>(tiempo.toString(), random2));
                PieChart.Data slice1 = new PieChart.Data("En uso", random3);
                PieChart.Data slice2 = new PieChart.Data("Ocioso"  , 100-random3);


                pieChart.getData().add(slice1);
                pieChart.getData().add(slice2);
                tiempo++;
                if (series.getData().size() > WINDOW_SIZE)
                    series.getData().remove(0); // quita el primer elemento del grafico si se llena
                if (series2.getData().size() > WINDOW_SIZE)
                    series2.getData().remove(0); // quita el primer elemento del grafico si se llena
            });
        }, 0, delay, TimeUnit.SECONDS);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        scheduledExecutorService.shutdownNow();
    }
}
