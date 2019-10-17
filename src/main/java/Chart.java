import javafx.geometry.Insets;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class Chart {
    public static LineChart create(){

        // Axis
        NumberAxis processY = new NumberAxis();
        NumberAxis timeX = new NumberAxis();
        processY.setLabel("Process Quantity");
        timeX.setLabel("Time");

        // Chart
        LineChart<Number, Number> lineChart = new LineChart<>(timeX, processY);
        lineChart.setTitle("Process Activity");
        lineChart.setAnimated(false);
        lineChart.setPrefSize(500, 500);
        lineChart.setPadding(new Insets(20));

        return lineChart;
    }
}
