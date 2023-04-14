package UI.UI;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;

import javax.swing.*;
import java.awt.*;

public class TimeSerise {
    MainUI main;
    public TimeSerise(){
        main = MainUI.getInstance();
    }

    protected ChartPanel createTimeSeries(JPanel west) {

        JFreeChart chart = ChartFactory.createXYLineChart("NHPI of Cities Over Time", "Year", "NHPI",  main.dataHandler.dataset(),
                PlotOrientation.VERTICAL, true, true, false);
        XYPlot plot = chart.getXYPlot();
        XYSplineRenderer splinerenderer1 = new XYSplineRenderer();
        XYSplineRenderer splinerenderer2 = new XYSplineRenderer();

        plot.setDataset(0, main.dataHandler.dataset());
        plot.setRenderer(0, splinerenderer1);


        plot.setRenderer(1, splinerenderer2);
        plot.setRangeAxis(1, new NumberAxis(""));

        plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
        plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis


        ChartPanel chartTimeSeriesPanel = new ChartPanel(chart);
        chartTimeSeriesPanel.setPreferredSize(new Dimension(400, 300));
        chartTimeSeriesPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartTimeSeriesPanel.setBackground(Color.white);
        return chartTimeSeriesPanel;
    }
}
