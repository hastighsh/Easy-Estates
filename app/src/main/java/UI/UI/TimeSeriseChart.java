package UI.UI;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class TimeSeriseChart implements Charts{
    ChartPanel chart;
    public TimeSeriseChart(XYSeriesCollection dataset){
        chart = create(dataset);
    }

    public ChartPanel create(XYSeriesCollection dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart("NHPI of Cities Over Time", "Year", "NHPI",  dataset,
                PlotOrientation.VERTICAL, true, true, false);
        XYPlot plot = chart.getXYPlot();
        XYSplineRenderer splinerenderer1 = new XYSplineRenderer();
        XYSplineRenderer splinerenderer2 = new XYSplineRenderer();

        plot.setDataset(0, dataset);
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
    public String toString(){
        return "Time Series Chart";
    }
    public ChartPanel getChart(){
        return chart;
    }

    @Override
    public void update(XYSeriesCollection dataset) {
        chart = create(dataset);
    }
}
