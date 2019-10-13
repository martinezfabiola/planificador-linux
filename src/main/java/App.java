import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

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

        // Ejes
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Tiempo recorrido");
        yAxis.setLabel("Cantidad de procesos");

        // Coordenadas 
        final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Cantidad de Procesos corriendo");
        lineChart.setAnimated(false); // cancelar animacion, se descuadra el grafico :/

        // Serie
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Proceso corriendo");

        // Agrega la serie al grafico
        lineChart.getData().add(series);

        // Poner el grafico en un contenedor
        StackPane spLineChart = new StackPane();
        spLineChart.getChildren().add(lineChart);

        // Crear botones
        

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
        
        // agregar todo al mismo contenedor
        VBox vbox = new VBox();
        VBox.setVgrow(spLineChart, Priority.ALWAYS);//Make line chart always grow vertically
        vbox.getChildren().addAll(spLineChart, spButton);

        // setup scene
        Scene scene = new Scene(vbox, 1000, 500);
        primaryStage.setScene(scene);

        TextField txtFld= new TextField();

        // show the stage
        primaryStage.show();

        // setup a scheduled executor to periodically put data into the chart
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        // Agrega y actualizar la data del grafico
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            // Cantidad de procesos corriendo
            Integer random = ThreadLocalRandom.current().nextInt(10);

            // Actualizar el grafico 
            Platform.runLater(() -> {
                series.getData().add(new XYChart.Data<>(tiempo.toString(), random));
                tiempo++;
                if (series.getData().size() > WINDOW_SIZE)
                    series.getData().remove(0); // quita el primer elemento del grafico si se llena
            });
        }, 0, delay, TimeUnit.SECONDS);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        scheduledExecutorService.shutdownNow();
    }
}
