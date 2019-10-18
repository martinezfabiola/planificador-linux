package Interface;

import javafx.scene.chart.PieChart;

public class CakeChart {
    public static PieChart create(){

        PieChart pieChart = new PieChart();
        pieChart.setMaxWidth(450);
        pieChart.setTitle("Use of Cores");

        return pieChart;
    }
}
