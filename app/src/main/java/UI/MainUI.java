package UI;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.util.TableOrder;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class MainUI extends JFrame {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static MainUI instance;
    private static ArrayList<String> locations = new ArrayList();
    private static ArrayList<String> times = new ArrayList();
    private static int counter = 0;
    JPanel west;
    JFreeChart chart;
    ChartPanel chartPanel;
    public static MainUI getInstance() {
        if (instance == null)
            instance = new MainUI();

        return instance;
    }

    private MainUI() {
        // Set window title
        super("Easy Estates");

        // Set top bar
        JLabel chooseCountryLabel = new JLabel("Choose a Location: "); // setting a text
        Vector<String> countriesNames = new Vector<String>(); // setting a drop-down menu vector
        countriesNames.add("Atlantic Region"); // adding instances to the menu
        countriesNames.add("Quebec");
        countriesNames.add("Ontario");
        countriesNames.add("Prairie Region");
        countriesNames.add("British Columbia");
        countriesNames.add("Newfoundland and Labrador");
        countriesNames.add("Prince Edward Island");
        countriesNames.add("Nova Scotia");
        countriesNames.add("New Brunswick");
        countriesNames.add("St. John's, Newfoundland and Labrador");
        countriesNames.add("Charlottetown, Prince Edward Island");
        countriesNames.add("Halifax, Nova Scotia");
        countriesNames.add("Saint John, Fredericton, and Moncton, New Brunswick");
        countriesNames.add("Québec, Quebec");
        countriesNames.add("Sherbrooke, Quebec");
        countriesNames.add("Trois-Rivières, Quebec");
        countriesNames.add("Montréal, Quebec");
        countriesNames.add("Ottawa-Gatineau, Quebec part, Ontario/Quebec");
        countriesNames.add("Ottawa-Gatineau, Ontario part, Ontario/Quebec");
        countriesNames.add("Oshawa, Ontario");
        countriesNames.add("Toronto, Ontario");
        countriesNames.add("Hamilton, Ontario");
        countriesNames.add("St. Catharines-Niagara, Ontario");
        countriesNames.add("Kitchener-Cambridge-Waterloo, Ontario");
        countriesNames.add("Guelph, Ontario");
        countriesNames.add("London, Ontario");
        countriesNames.add("Windsor, Ontario");
        countriesNames.add("Greater Sudbury, Ontario");
        countriesNames.add("Manitoba");
        countriesNames.add("Saskatchewan");
        countriesNames.add("Alberta");
        countriesNames.add("Winnipeg, Manitoba");
        countriesNames.add("Regina, Saskatchewan");
        countriesNames.add("Saskatoon, Saskatchewan");
        countriesNames.add("Calgary, Alberta");
        countriesNames.add("Edmonton, Alberta");
        countriesNames.add("Kelowna, British Columbia");
        countriesNames.add("Vancouver, British Columbia");
        countriesNames.add("Victoria, British Columbia");
        countriesNames.add("Canada");

        countriesNames.sort(null);
        JComboBox<String> countriesList = new JComboBox<String>(countriesNames); // making the vector into a drop-down menu

        countriesList.getSelectedItem();
//        System.out.println( countriesList.getSelectedItem());
//        UI.MainUI m = new UI.MainUI();
//        countriesList.addActionListener(m);
        JButton addLocation = new JButton("+"); // adding button

        JLabel test = new JLabel("added: " );
        JLabel test1 = new JLabel("" );


        addLocation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == addLocation){

                    locations.add((String)countriesList.getSelectedItem());
                    counter++;
                    west.remove(chartPanel);
                    createLine(west);
                    SwingUtilities.updateComponentTreeUI(west);

                }

            }
        });
        System.out.println(locations);


        JButton removeLocation = new JButton("-");

        JLabel from = new JLabel("From");
        JLabel to = new JLabel("To");
        Vector<String> years = new Vector<String>();
        for (int i = 2022; i >= 1981; i--) {
            for(int j = 12; j >= 1; j--) {
                years.add("" + i + "-" + j);
            }
        }
        JComboBox<String> fromList = new JComboBox<String>(years);
        JComboBox<String> toList = new JComboBox<String>(years);

        JButton loadData = new JButton("Load Data");

        JPanel north = new JPanel(); //making a panel (top)
        north.add(test);
        north.add(test1);
        north.add(chooseCountryLabel);
        north.add(countriesList);
        north.add(addLocation);
        north.add(removeLocation);
        north.add(from);
        north.add(fromList);
        north.add(to);
        north.add(toList);
        north.add(loadData);

        // Set bottom bar
        JButton recalculate = new JButton("Recalculate");

        JLabel viewsLabel = new JLabel("Available Views: ");

        Vector<String> viewsNames = new Vector<String>();
        viewsNames.add("Line Chart");
//        viewsNames.add("Pie Chart");
//        viewsNames.add("Bar Chart");
//        viewsNames.add("Scatter Chart");
//        viewsNames.add("Report");
        JComboBox<String> viewsList = new JComboBox<String>(viewsNames);
        JButton addView = new JButton("+");
        JButton removeView = new JButton("-");

        JLabel methodLabel = new JLabel("        Choose Forecasting method: ");

        Vector<String> methodsNames = new Vector<String>();
        methodsNames.add("Method #1");
//        methodsNames.add("Mortality vs Expenses");
//        methodsNames.add("Mortality vs Expenses & Hospital Beds");
//        methodsNames.add("Mortality vs GDP");
//        methodsNames.add("Unemployment vs GDP");
//        methodsNames.add("Unemployment");

        JButton statsBtn = new JButton("Compare by T-test");

        JComboBox<String> methodsList = new JComboBox<String>(methodsNames);

        JPanel south = new JPanel();
        south.add(viewsLabel);
        south.add(viewsList);
        south.add(addView);
        south.add(removeView);

        south.add(methodLabel);
        south.add(methodsList);
        south.add(recalculate);
        south.add(statsBtn);

        JPanel east = new JPanel();

        // Set charts region
        west = new JPanel();
        west.setLayout(new GridLayout(2, 0));
        createCharts(west);

        getContentPane().add(north, BorderLayout.NORTH);
        getContentPane().add(east, BorderLayout.EAST);
        getContentPane().add(south, BorderLayout.SOUTH);
        getContentPane().add(west, BorderLayout.WEST);
    }

    private void createCharts(JPanel west) {
        createReport(west);
        createLine(west);
//        createTimeSeries(west);
//        createBar(west);
//        createPie(west);
//        createScatter(west);

    }

    private void createReport(JPanel west) {
        JTextArea report = new JTextArea();
        report.setEditable(false);
        report.setPreferredSize(new Dimension(400, 300));
        report.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        report.setBackground(Color.white);
//        String reportMessage, reportMessage2;

        int parNum = 2;

        //raw data table
        String reportMessage = "City NHPI Over Time Raw Data\n" + "==============================\n";
        for (int i = 1; i <= parNum; i++) {
            String dataInfo = "Toronto:\n" + "\tStart Date _ End Date: 1991-5 / 1995-6\n"
                    + "\tNHPI: 0564846" + "\n";
            reportMessage = reportMessage.concat(dataInfo);
        }

        //stats table
        String statsMessage = "Statistic Results of City NHPI Over Time\n" + "==========================\n";
        for (int i = 1; i <= parNum; i++) {
            String dataInfo = "Toronto:\n" + "\tStart Date _ End Date: 1991-5 / 1995-6\n"
                    + "\tNHPI: 0564846" + "\n";
            statsMessage = statsMessage.concat(dataInfo);
        }


        // =========== putting the radio buttons for switching between raw data and the stats
        report.setText(reportMessage);
        JScrollPane outputScrollPane = new JScrollPane(report);
        west.add(outputScrollPane);
    }

    private void createScatter(JPanel west) {
        TimeSeries series1 = new TimeSeries("Mortality/1000 births");
        series1.add(new Year(2018), 5.6);
        series1.add(new Year(2017), 5.7);
        series1.add(new Year(2016), 5.8);
        series1.add(new Year(2015), 5.8);
        series1.add(new Year(2014), 5.9);
        series1.add(new Year(2013), 6.0);
        series1.add(new Year(2012), 6.1);
        series1.add(new Year(2011), 6.2);
        series1.add(new Year(2010), 6.4);

        TimeSeries series2 = new TimeSeries("Health Expenditure per Capita");
        series2.add(new Year(2018), 10624);
        series2.add(new Year(2017), 10209);
        series2.add(new Year(2016), 9877);
        series2.add(new Year(2015), 9491);
        series2.add(new Year(2014), 9023);
        series2.add(new Year(2013), 8599);
        series2.add(new Year(2012), 8399);
        series2.add(new Year(2011), 8130);
        series2.add(new Year(2010), 7930);
        TimeSeriesCollection dataset2 = new TimeSeriesCollection();
        dataset2.addSeries(series2);

        TimeSeries series3 = new TimeSeries("Hospital Beds/1000 people");
        series3.add(new Year(2018), 2.92);
        series3.add(new Year(2017), 2.87);
        series3.add(new Year(2016), 2.77);
        series3.add(new Year(2015), 2.8);
        series3.add(new Year(2014), 2.83);
        series3.add(new Year(2013), 2.89);
        series3.add(new Year(2012), 2.93);
        series3.add(new Year(2011), 2.97);
        series3.add(new Year(2010), 3.05);

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series3);

        XYPlot plot = new XYPlot();
        XYItemRenderer itemrenderer1 = new XYLineAndShapeRenderer(false, true);
        XYItemRenderer itemrenderer2 = new XYLineAndShapeRenderer(false, true);

        plot.setDataset(0, dataset);
        plot.setRenderer(0, itemrenderer1);
        DateAxis domainAxis = new DateAxis("Year");
        plot.setDomainAxis(domainAxis);
        plot.setRangeAxis(new NumberAxis(""));

        plot.setDataset(1, dataset2);
        plot.setRenderer(1, itemrenderer2);
        plot.setRangeAxis(1, new NumberAxis("US$"));

        plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
        plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis

        JFreeChart scatterChart = new JFreeChart("Mortality vs Expenses & Hospital Beds",
                new Font("Serif", java.awt.Font.BOLD, 18), plot, true);


//        Vector<JFreeChart> multiChart = new Vector<JFreeChart>();
//        multiChart.add(scatterChart);
//        multiChart.add(scatterChart);

//        JScrollPane scroll = new JScrollPane(scatterChart);
        ChartPanel chartPanel = new ChartPanel(scatterChart);
        chartPanel.setPreferredSize(new Dimension(350, 300));
        JScrollPane scroll = new JScrollPane(chartPanel);
        scroll.setPreferredSize(new Dimension(400, 300));
        scroll.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        scroll.setBackground(Color.white);
        west.add(scroll);
    }

    private void createPie(JPanel west) {
        // Different way to create pie chart
        /*
         * var dataset = new DefaultPieDataset(); dataset.setValue("Unemployed", 3.837);
         * dataset.setValue("Employed", 96.163);
         *
         * JFreeChart pieChart = ChartFactory.createPieChart("Women's Unemployment",
         * dataset, true, true, false);
         */

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(3.946, "Unemployed", "Men");
        dataset.addValue(96.054, "Employed", "Men");
        dataset.addValue(3.837, "Unemployed", "Women");
        dataset.addValue(96.163, "Employed", "Women");

        JFreeChart pieChart = ChartFactory.createMultiplePieChart("Unemployment: Men vs Women", dataset,
                TableOrder.BY_COLUMN, true, true, false);

        ChartPanel chartPanel = new ChartPanel(pieChart);
        chartPanel.setPreferredSize(new Dimension(400, 300));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        west.add(chartPanel);
    }

    private void createBar(JPanel west) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(5.6, "Mortality/1000 births", "2018");
        dataset.setValue(5.7, "Mortality/1000 births", "2017");
        dataset.setValue(5.8, "Mortality/1000 births", "2016");
        dataset.setValue(5.8, "Mortality/1000 births", "2015");
        dataset.setValue(5.9, "Mortality/1000 births", "2014");
        dataset.setValue(6, "Mortality/1000 births", "2013");
        dataset.setValue(6.1, "Mortality/1000 births", "2012");
        dataset.setValue(6.2, "Mortality/1000 births", "2011");
        dataset.setValue(6.4, "Mortality/1000 births", "2010");

        dataset.setValue(2.92, "Hospital beds/1000 people", "2018");
        dataset.setValue(2.87, "Hospital beds/1000 people", "2017");
        dataset.setValue(2.77, "Hospital beds/1000 people", "2016");
        dataset.setValue(2.8, "Hospital beds/1000 people", "2015");
        dataset.setValue(2.83, "Hospital beds/1000 people", "2014");
        dataset.setValue(2.89, "Hospital beds/1000 people", "2013");
        dataset.setValue(2.93, "Hospital beds/1000 people", "2012");
        dataset.setValue(2.97, "Hospital beds/1000 people", "2011");
        dataset.setValue(3.05, "Hospital beds/1000 people", "2010");

        DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();

        dataset2.setValue(10623, "Health Expenditure per Capita", "2018");
        dataset2.setValue(10209, "Health Expenditure per Capita", "2017");
        dataset2.setValue(9877, "Health Expenditure per Capita", "2016");
        dataset2.setValue(9491, "Health Expenditure per Capita", "2015");
        dataset2.setValue(9023, "Health Expenditure per Capita", "2014");
        dataset2.setValue(8599, "Health Expenditure per Capita", "2013");
        dataset2.setValue(8399, "Health Expenditure per Capita", "2012");
        dataset2.setValue(8130, "Health Expenditure per Capita", "2011");
        dataset2.setValue(7930, "Health Expenditure per Capita", "2010");

        CategoryPlot plot = new CategoryPlot();
        BarRenderer barrenderer1 = new BarRenderer();
        BarRenderer barrenderer2 = new BarRenderer();

        plot.setDataset(0, dataset);
        plot.setRenderer(0, barrenderer1);
        CategoryAxis domainAxis = new CategoryAxis("Year");
        plot.setDomainAxis(domainAxis);
        plot.setRangeAxis(new NumberAxis(""));

        plot.setDataset(1, dataset2);
        plot.setRenderer(1, barrenderer2);
        plot.setRangeAxis(1, new NumberAxis("US$"));

        plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
        plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis

        JFreeChart barChart = new JFreeChart("Mortality vs Expenses & Hospital Beds",
                new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

        // Different way to create bar chart
        /*
         * dataset = new DefaultCategoryDataset();
         *
         * dataset.addValue(3.946, "Unemployed", "Men"); dataset.addValue(96.054,
         * "Employed", "Men"); dataset.addValue(3.837, "Unemployed", "Women");
         * dataset.addValue(96.163, "Employed", "Women"); barChart =
         * ChartFactory.createBarChart("Unemployment: Men vs Women", "Gender",
         * "Percentage", dataset, PlotOrientation.VERTICAL, true, true, false);
         */

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(400, 300));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        west.add(chartPanel);
    }

    private void createLine(JPanel west) {
        ArrayList<XYSeries> arr = new ArrayList<>(10);
        System.out.println(locations.size());
        int i = 0;
        int j = 100;
        if(locations.size()>0){
            for(String location: locations){
                arr.add(new XYSeries(locations.get(i)));
                arr.get(i).add(2018, 500);
                arr.get(i).add(2017, 534);
                arr.get(i).add(2016, 643);
                arr.get(i).add(2015, 903);
                arr.get(i).add(2014, i+100);
                arr.get(i).add(2013, 934);
                arr.get(i).add(2012, 834);
                arr.get(i).add(2011, 924);
                arr.get(i).add(2010, 1465);
//                j += 100 + i;
                i ++;
            }
        }

//        XYSeries series1 = new XYSeries("Toronto");
//        series1.add(2018, 500);
//        series1.add(2017, 534);
//        series1.add(2016, 643);
//        series1.add(2015, 903);
//        series1.add(2014, 1000);
//        series1.add(2013, 934);
//        series1.add(2012, 834);
//        series1.add(2011, 924);
//        series1.add(2010, 1465);



//        XYSeries series2 = new XYSeries("Montreal");
//        series2.add(2018, 10624);
//        series2.add(2017, 10209);
//        series2.add(2016, 9877);
//        series2.add(2015, 9491);
//        series2.add(2014, 9023);
//        series2.add(2013, 8599);
//        series2.add(2012, 8399);
//        series2.add(2011, 8130);
//        series2.add(2010, 7930);



//        XYSeries series3 = new XYSeries("Hospital Beds/1000 people");
//        series3.add(2018, 2.92);
//        series3.add(2017, 2.87);
//        series3.add(2016, 2.77);
//        series3.add(2015, 2.8);
//        series3.add(2014, 2.83);
//        series3.add(2013, 2.89);
//        series3.add(2012, 2.93);
//        series3.add(2011, 2.97);
//        series3.add(2010, 3.05);

        XYSeriesCollection dataset = new XYSeriesCollection();
        for(int k = 0;k<counter;k++){
            dataset.addSeries(arr.get(k));
        }
//        dataset.addSeries(series3);

        chart = ChartFactory.createXYLineChart("NHPI of Cities Over Time", "Year", "NHPI", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(
                new TextTitle("NHPI of Cities Over Time", new Font("Serif", java.awt.Font.BOLD, 18)));

        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(400, 300));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        west.add(chartPanel);

    }


    private void createTimeSeries(JPanel west) {
        TimeSeries series1 = new TimeSeries("Mortality/1000 births");
        series1.add(new Year(2018), 5.6);
        series1.add(new Year(2017), 5.7);
        series1.add(new Year(2016), 5.8);
        series1.add(new Year(2015), 5.8);
        series1.add(new Year(2014), 5.9);
        series1.add(new Year(2013), 6.0);
        series1.add(new Year(2012), 6.1);
        series1.add(new Year(2011), 6.2);
        series1.add(new Year(2010), 6.4);

        TimeSeries series2 = new TimeSeries("Health Expenditure per Capita");
        series2.add(new Year(2018), 10624);
        series2.add(new Year(2017), 10209);
        series2.add(new Year(2016), 9877);
        series2.add(new Year(2015), 9491);
        series2.add(new Year(2014), 9023);
        series2.add(new Year(2013), 8599);
        series2.add(new Year(2012), 8399);
        series2.add(new Year(2011), 8130);
        series2.add(new Year(2010), 7930);
        TimeSeriesCollection dataset2 = new TimeSeriesCollection();
        dataset2.addSeries(series2);

        TimeSeries series3 = new TimeSeries("Hospital Beds/1000 people");
        series3.add(new Year(2018), 2.92);
        series3.add(new Year(2017), 2.87);
        series3.add(new Year(2016), 2.77);
        series3.add(new Year(2015), 2.8);
        series3.add(new Year(2014), 2.83);
        series3.add(new Year(2013), 2.89);
        series3.add(new Year(2012), 2.93);
        series3.add(new Year(2011), 2.97);
        series3.add(new Year(2010), 3.05);

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series3);

        XYPlot plot = new XYPlot();
        XYSplineRenderer splinerenderer1 = new XYSplineRenderer();
        XYSplineRenderer splinerenderer2 = new XYSplineRenderer();

        plot.setDataset(0, dataset);
        plot.setRenderer(0, splinerenderer1);
        DateAxis domainAxis = new DateAxis("Year");
        plot.setDomainAxis(domainAxis);
        plot.setRangeAxis(new NumberAxis(""));

        plot.setDataset(1, dataset2);
        plot.setRenderer(1, splinerenderer2);
        plot.setRangeAxis(1, new NumberAxis("US$"));

        plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
        plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis

        JFreeChart chart = new JFreeChart("Mortality vs Expenses & Hospital Beds",
                new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(400, 300));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        west.add(chartPanel);

    }

    private void addParameter(){}

    public static void main(String[] args) {

        JFrame frame = MainUI.getInstance();
        frame.setSize(900, 600);
        frame.pack();
        frame.setVisible(true);
    }

}