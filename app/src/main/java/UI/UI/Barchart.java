package UI.UI;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;

import javax.swing.*;
import java.awt.*;

public class Barchart {
    MainUI main;
    public Barchart(){
        main = MainUI.getInstance();
    }
    protected ChartPanel createBar(JPanel west) {

        CategoryPlot plot = new CategoryPlot();
        plot.setDataset(0, main.dataHandler.datasetD());
        plot.setRenderer(0,  new BarRenderer());
        CategoryAxis domainAxis = new CategoryAxis("Year");
        plot.setDomainAxis(domainAxis);
        plot.setRangeAxis(new NumberAxis("NHPI"));


        plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
        plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis

        JFreeChart barChart = new JFreeChart("NHPI of Cities Over Time",
                new Font("Serif", Font.BOLD, 18), plot, true);


        ChartPanel barChartPanel = new ChartPanel(barChart);
        barChartPanel.setPreferredSize(new Dimension(400, 300));
        barChartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        barChartPanel.setBackground(Color.white);
        return barChartPanel;
    }
}
