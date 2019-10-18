package Interface;

import javafx.geometry.Insets;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class Chart {
    public static LineChart create(){

        // Axis
        final NumberAxis processY = new NumberAxis();
        final CategoryAxis timeX = new CategoryAxis();
        processY.setLabel("Process Quantity");
        timeX.setLabel("Time");

        // Interface.Chart
        final LineChart<String, Number> lineChart = new LineChart<>(timeX, processY);
        lineChart.setTitle("Process Activity");
        lineChart.setAnimated(false);
        lineChart.setPrefSize(500, 500);
        lineChart.setPadding(new Insets(20));

        return lineChart;
    }
}
