package UI.UI;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class Barchart implements Charts{
    ChartPanel chart;
    public Barchart(XYSeriesCollection dataset){
       chart = create(dataset);
    }
    protected ChartPanel create(XYSeriesCollection dataset) {
//        JFreeChart barChart = ChartFactory.createBarChart("NHPI of Cities Over Time", "Year", "NHPI", (CategoryDataset) dataset,
//                PlotOrientation.VERTICAL, true, true, false);

//        CategoryPlot plot = new CategoryPlot();
//        plot.setDataset(0, dataset);
//        plot.setRenderer(0,  new BarRenderer());
//        CategoryAxis domainAxis = new CategoryAxis("Year");
//        plot.setDomainAxis(domainAxis);
//        plot.setRangeAxis(new NumberAxis("NHPI"));
//
//
//        plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
//        plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis
//
//        JFreeChart barChart = new JFreeChart("NHPI of Cities Over Time",
//                new Font("Serif", Font.BOLD, 18), plot, true);
        NumberAxis xAxis = new NumberAxis("Category");
        NumberAxis yAxis = new NumberAxis("Value");
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // Create the renderer
        XYBarRenderer renderer = new XYBarRenderer();
        renderer.setMargin(0.1);

        // Create the chart using XYPlot
        XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer);
        plot.setOrientation(PlotOrientation.VERTICAL);
        JFreeChart chart = new JFreeChart("Bar Chart", JFreeChart.DEFAULT_TITLE_FONT, plot, true);

        // Add the chart to a ChartPanel and display it
//        ChartPanel chartPanel = new ChartPanel(chart);
//        chartPanel.setPreferredSize(new Dimension(800, 600));



        ChartPanel barChartPanel = new ChartPanel(chart);
        barChartPanel.setPreferredSize(new Dimension(400, 300));
        barChartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        barChartPanel.setBackground(Color.white);
        return barChartPanel;
    }
    public String toString(){
        return "Bar Chart";
    }
    public ChartPanel getChart(){
        return chart;
    }

    @Override
    public void update(XYSeriesCollection dataset) {
        chart = create(dataset);
    }
}
