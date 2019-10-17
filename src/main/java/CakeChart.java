import javafx.scene.chart.PieChart;

public class CakeChart {
    public static PieChart create(){

        PieChart pieChart = new PieChart();
        pieChart.setMaxWidth(500);
        pieChart.setTitle("Use of Cores");

        return pieChart;
    }
}
