package UI.UI;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

import javax.swing.*;
import java.awt.*;

public class Scatterchart {
    MainUI main;
    public Scatterchart(){
        main = MainUI.getInstance();
    }
    protected ChartPanel createScatter(JPanel west) {
        JFreeChart scatterChart = ChartFactory.createScatterPlot("NHPI of Cities Over Time", "Year", "NHPI",  main.dataHandler.dataset(),
                PlotOrientation.VERTICAL, true, true, false);


        XYPlot plot = scatterChart.getXYPlot();
        XYItemRenderer itemrenderer2 = new XYLineAndShapeRenderer(false, true);

        plot.setRenderer(1, itemrenderer2);
        plot.setRangeAxis(1, new NumberAxis(""));

        plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
        plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis


        ChartPanel scatterTimeSeriesPanel = new ChartPanel(scatterChart);
        scatterTimeSeriesPanel.setPreferredSize(new Dimension(400, 300));
        scatterTimeSeriesPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        scatterTimeSeriesPanel.setBackground(Color.white);
        return scatterTimeSeriesPanel;
    }
}
