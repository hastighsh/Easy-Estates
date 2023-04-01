package UI.UI;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicBorders;

import LogicAndComparsion.Location;
import LogicAndComparsion.Logic;
import LogicAndComparsion.StatsComparison;
import LogicAndComparsion.Time;
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
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class MainUI extends JFrame {
    private class Node implements Comparable {
        private String location;
        ArrayList<Double> data;
        protected Node(String location,ArrayList<Double> data){
            this.location =location;
            this.data = data;
        }
        private ArrayList<Double> getData(){
            return data;
        }
        private void setData(ArrayList<Double> data){
            this.data = data;
        }

        private String getLocation() {
            return location;
        }

        private void setLocation(String location) {
            this.location = location;
        }

        @Override
        public int compareTo(Object o) {
            Node node = (Node) o;
            if(this.getLocation().equals(node.getLocation())){
                return 0;
            }
            else{
                return 1;
            }
        }

    }
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static MainUI instance;
    private static ArrayList<String> locations = new ArrayList<>();
    private static ArrayList<String> visuals = new ArrayList<>();
    private static ArrayList<String> times = new ArrayList<>();
    private  ArrayList<Node> result = new ArrayList<>();
    private static int counter = 0;
    JComboBox<String> fromList, toList;
    JPanel west;
    JFreeChart chart,barChart;
    ChartPanel chartPanel, chartTimeSeriesPanel,barChartPanel, scatterTimeSeriesPanel;
    JScrollPane outputScrollPane;
    Logic log = new Logic();
    String startTime= "";
    String endTime = "";
    int visualThreshold = 0;
    int flag = 0;
    double resultForIncrse;

    public static MainUI getInstance() {
        if (instance == null)
            instance = new MainUI();

        return instance;
    }

    private MainUI() {
        // Set window title
        super("Easy Estates");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Set top bar ---------------->

        // radio buttons for table switching
        JRadioButton rawDataButton = new JRadioButton("Raw Data");
        rawDataButton.setSelected(true);
        rawDataButton.setActionCommand("Raw Data Table");
        JRadioButton descriptiveDataButton = new JRadioButton("Descriptive Data");

        ButtonGroup dataTableButtons = new ButtonGroup();
        dataTableButtons.add(rawDataButton);
        dataTableButtons.add(descriptiveDataButton);

        // location drop-down menu
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


        JLabel test1 = new JLabel("" );


        addLocation.addActionListener(e -> {
            if(e.getSource() == addLocation){
                if(locations.contains((String)countriesList.getSelectedItem())){

                    String boxName = "Location List";
                    String dialog = "This location has already been selected. Please add another location.";
                    makeDialogBox(dialog);

                } else if(countriesNames.size() == locations.size()){

                    String boxName = "Location List";
                    String dialog = "No more location to add.";
                    makeDialogBox(dialog);

                } else {

                    locations.add((String)countriesList.getSelectedItem());
                    counter++;

                    //update the view with the new data
                    updateView(west);
                    SwingUtilities.updateComponentTreeUI(west);
                }
            }
        });
        System.out.println(locations);


        JButton removeLocation = new JButton("-");

        removeLocation.addActionListener(e->{
            if(e.getSource()==removeLocation){

                if (locations.size() == 0){ // prompting the user to add locations if the location list is empty

                    String dialog = "No location has been added to the list; please add a location.";
                    makeDialogBox(dialog);

                } else if (locations.contains((String)countriesList.getSelectedItem())){

                    for(Node node: result){
                        if(node.getLocation().equals(countriesList.getSelectedItem())){
                            result.remove(node);
                            break;
                        }
                    }

                    locations.remove((String)countriesList.getSelectedItem());
                    counter--;

                    //update the view with the new data
                    updateView(west);
                    SwingUtilities.updateComponentTreeUI(west);

                } else {

                    String dialog = "Select a location that exists in the list.";
                    makeDialogBox(dialog);

                }
            }
        });

        JLabel from = new JLabel("From");
        JLabel to = new JLabel("To");

        Vector<String> years = new Vector<String>();
        for (int i = 2022; i >= 1981; i--) {
                years.add("" + i);
        }
        fromList = new JComboBox<String>(years);
        toList = new JComboBox<String>(years);

        //load button
        JButton loadData = new JButton("Load Data");

        loadData.addActionListener (e->{

            if(e.getSource()==loadData){

                startTime = fromList.getSelectedItem().toString();
                endTime = toList.getSelectedItem().toString();

                if(!startTime.equals(endTime)) {
                    // !!!!! throw exception or error window to choose two different time !!!!!!!!!!
                    int i = 0;
                    for (String str : locations) {
                        Location location = new Location(locations.get(i));
                        Time startTime = new Time(fromList.getSelectedItem().toString());

                        Time endTime = new Time(toList.getSelectedItem().toString());
                        i++;

                        try {
                            System.out.println("fetchData");
                            ArrayList<Double> get = log.fetchData(location, startTime, endTime);
                            System.out.println(get);
                            if(result.size()==0){ // if the result array is empty add one node to it
                                result.add(new Node(location.getName(),get));
                            }else{
                                boolean check = true;
                                for(int j = 0; j < result.size(); j++){
                                    if(result.get(j).getLocation().equals(str)){
                                        result.get(j).setData(get);
                                        check = false;
                                        break;
                                    }
                                }
                                if(check){
                                    result.add(new Node(location.getName(),get));
                                }
                            }
                            // update the view with the new data
                            updateView(west);
                            SwingUtilities.updateComponentTreeUI(west);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }

            }

        });

        JPanel north = new JPanel(); //making a panel (top)
        north.add(rawDataButton);
        north.add(descriptiveDataButton);
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
        JButton recalculate = new JButton("Calculate");

        recalculate.addActionListener(e->{
            JDialog optionPane = new JDialog(this);
            optionPane.setLocationRelativeTo(null);
            JComboBox<String> item = new JComboBox<>();
            JTextField month = new JTextField();
            JButton button = new JButton("submit");
            optionPane.setPreferredSize(new Dimension(400,100));
            for(Node node:result){
                item.addItem(node.getLocation());
            }
            item.setPreferredSize(new Dimension(120,20));
            month.setPreferredSize(new Dimension(80,20));
            JLabel label1 = new JLabel("City: ");
            JLabel label2 = new JLabel("Month: ");
            optionPane.add(label1);
            optionPane.add(item);
            optionPane.add(label2);
            optionPane.add(month);
            optionPane.add(button);
            optionPane.setLayout(new FlowLayout());
            optionPane.pack();
            optionPane.setVisible(true);
            button.addActionListener(f->{
                String city = "";
                optionPane.dispose();
                int month1 = 0;
                if(!month.getText().equals("")){
                    month1 = Integer.parseInt(month.getText());
                }
                ArrayList<Double> temp = new ArrayList<>();
                for(Node node:result){
                    if(node.getLocation().equals(item.getSelectedItem())){
                        temp = merge(node);
                        city = node.getLocation();
                    }
                }
                if(month1<=0){
                    makeDialogBox("Month must be positive");
                }
                else{
                    Logic logic = new Logic();
                    forForecasting(logic.forecast(temp,month1),city);
                }
            });

        });

        JLabel viewsLabel = new JLabel("Available Views: ");


        // Available views
        Vector<String> viewsNames = new Vector<String>();
        viewsNames.add("Line Chart");
        viewsNames.add("Time Series Chart");
        viewsNames.add("Bar Chart");
        viewsNames.add("Scatter Chart");

        JComboBox<String> viewsList = new JComboBox<String>(viewsNames);
        JButton addView = new JButton("+");
        addView.addActionListener(e->{
            if(checkThershold()){
                if(!visuals.contains((String)viewsList.getSelectedItem())){
                    visuals.add((String)viewsList.getSelectedItem());
                    updateView(west);
                    SwingUtilities.updateComponentTreeUI(west);
                }
                else{
                    makeDialogBox("Your choice already executed");
                }
            }
           else{
                makeDialogBox("Your charts selection out of bound 3");
            }
        });

        JButton removeView = new JButton("-");
        removeView.addActionListener(e->{
            if(visuals.size()<=0){
                makeDialogBox("Chart is empty");
            }
            else if(!visuals.contains((String)viewsList.getSelectedItem())){
                makeDialogBox("Your choice does not exist");
            }
            else{
                visuals.remove((String)viewsList.getSelectedItem());
                updateView(west);
                SwingUtilities.updateComponentTreeUI(west);
            }
        });


        // forecasting drop-down menu, button, ...
        JLabel methodLabel = new JLabel("        Choose Forecasting method: ");

        Vector<String> methodsNames = new Vector<String>();
        methodsNames.add("Linear Regression Module");


        // comparing drop-down menu, button, ...
        JButton statsBtn = new JButton("Compare by T-test");
        statsBtn.addActionListener(e->{
            Location location = new Location("Hamilton, Ontario");
            Time start1 = new Time ("1981");		// user gives this (comes as parameter from UI call, logic.AddTimeSeries(place, startTime, endTime);
            Time end1 = new Time ("1998");
            Time start2 = new Time ("1991");
            Time end2 = new Time ("1999");
            try{
                ArrayList<Double> data1 = log.fetchData(location,start1,end1);//  fetchData(place, startTime, endTime) returns data1, data2
                System.out.println(data1);
                ArrayList<Double> data2 =log.fetchData(location,start2,end2);
                System.out.println(data2);
                LogicAndComparsion.TimeSeries tseries1 = new LogicAndComparsion.TimeSeries(data1, start1, end1);
                LogicAndComparsion.TimeSeries tseries2 = new LogicAndComparsion.TimeSeries(data2, start2, end2);
                StatsComparison sc = log.compareTimeSeries(tseries1, tseries2);
                System.out.println(sc.getPValue());
                System.out.println(sc.getConclusion());
            }catch (SQLException ex){
                throw new RuntimeException(ex);
            }

        });

        JComboBox<String> methodsList = new JComboBox<String>(methodsNames);

        // making the bottom pane
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
        createTimeSeries(west); // Hasti
        createBar(west); // Lee
        createScatter(west); //

    }

    private void createReport(JPanel west) {
        JTextArea report = new JTextArea();
        report.setEditable(false);
        report.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        report.setBackground(Color.white);
//        String reportMessage, reportMessage2;

        //raw data table
        int i = 0;
        String reportMessage = "City NHPI Over Time Raw Data\n" + "==============================\n";
        if (locations.size() > 0) {
//                for (Node node : result) {
            for(int j = 0;j<result.size();j++) {
                String dataInfo = result.get(j).getLocation() + "\n" + "\tStart Date _ End Date: " + startTime + " / " + endTime + "\n"
                        + "\tNHPI: " + getAverage(result.get(j)) + "\n";
//                    String dataInfo = locations.get(i) + "\n";
                reportMessage = reportMessage.concat(dataInfo);
                i++;
            }
//                }
//            } else {
//                for (Node node : result) {
////                String dataInfo = locations.get(i) + "\n" + "\tStart Date _ End Date: " + startTime + " / " + endTime + "\n"
////                        + "\tNHPI: 0564846" + "\n";
//                    String dataInfo = locations.get(i) + "\n";
//                    reportMessage = reportMessage.concat(dataInfo);
//                    i++;
//
//                }
            }

        //stats table
//        String statsMessage = "Statistic Results of City NHPI Over Time\n" + "==========================\n";
//        for (String location: locations) {
//            String dataInfo = locations.get(i) + "\n" + "\tStart Date _ End Date: 1991-5 / 1995-6\n"
//                    + "\tNHPI: 0564846" + "\n";
//            statsMessage = statsMessage.concat(dataInfo);
//        }


        // =========== putting the radio buttons for switching between raw data and the stats
        report.setText(reportMessage);
        outputScrollPane = new JScrollPane(report);
        outputScrollPane.setPreferredSize(new Dimension(400, 300));
        west.add(outputScrollPane);
    }

    private void createScatter(JPanel west) {
//        TimeSeries series1 = new TimeSeries("Mortality/1000 births");
//        series1.add(new Year(2018), 5.6);
//        series1.add(new Year(2017), 5.7);
//        series1.add(new Year(2016), 5.8);
//        series1.add(new Year(2015), 5.8);
//        series1.add(new Year(2014), 5.9);
//        series1.add(new Year(2013), 6.0);
//        series1.add(new Year(2012), 6.1);
//        series1.add(new Year(2011), 6.2);
//        series1.add(new Year(2010), 6.4);
//
//        TimeSeries series2 = new TimeSeries("Health Expenditure per Capita");
//        series2.add(new Year(2018), 10624);
//        series2.add(new Year(2017), 10209);
//        series2.add(new Year(2016), 9877);
//        series2.add(new Year(2015), 9491);
//        series2.add(new Year(2014), 9023);
//        series2.add(new Year(2013), 8599);
//        series2.add(new Year(2012), 8399);
//        series2.add(new Year(2011), 8130);
//        series2.add(new Year(2010), 7930);
//        TimeSeriesCollection dataset2 = new TimeSeriesCollection();
//        dataset2.addSeries(series2);
//
//        TimeSeries series3 = new TimeSeries("Hospital Beds/1000 people");
//        series3.add(new Year(2018), 2.92);
//        series3.add(new Year(2017), 2.87);
//        series3.add(new Year(2016), 2.77);
//        series3.add(new Year(2015), 2.8);
//        series3.add(new Year(2014), 2.83);
//        series3.add(new Year(2013), 2.89);
//        series3.add(new Year(2012), 2.93);
//        series3.add(new Year(2011), 2.97);
//        series3.add(new Year(2010), 3.05);
//
//        TimeSeriesCollection dataset = new TimeSeriesCollection();
////        dataset.addSeries(series1);
////        dataset.addSeries(series3);
//
//        XYPlot plot = new XYPlot();
//        XYItemRenderer itemrenderer1 = new XYLineAndShapeRenderer(false, true);
//        XYItemRenderer itemrenderer2 = new XYLineAndShapeRenderer(false, true);
//
//        plot.setDataset(0, dataset);
//        plot.setRenderer(0, itemrenderer1);
//        DateAxis domainAxis = new DateAxis("Year");
//        plot.setDomainAxis(domainAxis);
//        plot.setRangeAxis(new NumberAxis(""));
//
//        plot.setDataset(1, dataset2);
//        plot.setRenderer(1, itemrenderer2);
//        plot.setRangeAxis(1, new NumberAxis("US$"));
//
//        plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
//        plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis
//
//        JFreeChart scatterChart = new JFreeChart("Mortality vs Expenses & Hospital Beds",
//                new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

        TimeSeriesCollection dataset = new TimeSeriesCollection();
//        dataset.addSeries(series1);
//        dataset.addSeries(series3);

        XYPlot plot = new XYPlot();
        XYItemRenderer itemrenderer1 = new XYLineAndShapeRenderer(false, true);
        XYItemRenderer itemrenderer2 = new XYLineAndShapeRenderer(false, true);

        plot.setDataset(0, dataset());
        plot.setRenderer(0, itemrenderer1);
        DateAxis domainAxis = new DateAxis("Year");
        plot.setDomainAxis(domainAxis);
        plot.setRangeAxis(new NumberAxis("NHPI"));

//        plot.setDataset(1, dataset2);
        plot.setRenderer(1, itemrenderer2);
        plot.setRangeAxis(1, new NumberAxis("?"));

        plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
        plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis

        JFreeChart scatterChart = new JFreeChart("NHPI of Cities Over Time",
                new Font("Serif", java.awt.Font.BOLD, 18), plot, true);


//        Vector<JFreeChart> multiChart = new Vector<JFreeChart>();
//        multiChart.add(scatterChart);
//        multiChart.add(scatterChart);

//        JScrollPane scroll = new JScrollPane(scatterChart);
        scatterTimeSeriesPanel = new ChartPanel(scatterChart);
//        chartPanel.setPreferredSize(new Dimension(350, 300));
//        JScrollPane scroll = new JScrollPane(chartPanel);
        scatterTimeSeriesPanel.setPreferredSize(new Dimension(400, 300));
        scatterTimeSeriesPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        scatterTimeSeriesPanel.setBackground(Color.white);
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
    }

    private void createBar(JPanel west) {
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        dataset.setValue(5.6, "Mortality/1000 births", "2018");
//        dataset.setValue(5.7, "Mortality/1000 births", "2017");
//        dataset.setValue(5.8, "Mortality/1000 births", "2016");
//        dataset.setValue(5.8, "Mortality/1000 births", "2015");
//        dataset.setValue(5.9, "Mortality/1000 births", "2014");
//        dataset.setValue(6, "Mortality/1000 births", "2013");
//        dataset.setValue(6.1, "Mortality/1000 births", "2012");
//        dataset.setValue(6.2, "Mortality/1000 births", "2011");
//        dataset.setValue(6.4, "Mortality/1000 births", "2010");
//
//        dataset.setValue(2.92, "Hospital beds/1000 people", "2018");
//        dataset.setValue(2.87, "Hospital beds/1000 people", "2017");
//        dataset.setValue(2.77, "Hospital beds/1000 people", "2016");
//        dataset.setValue(2.8, "Hospital beds/1000 people", "2015");
//        dataset.setValue(2.83, "Hospital beds/1000 people", "2014");
//        dataset.setValue(2.89, "Hospital beds/1000 people", "2013");
//        dataset.setValue(2.93, "Hospital beds/1000 people", "2012");
//        dataset.setValue(2.97, "Hospital beds/1000 people", "2011");
//        dataset.setValue(3.05, "Hospital beds/1000 people", "2010");
//
//        DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();
//
//        dataset2.setValue(10623, "Health Expenditure per Capita", "2018");
//        dataset2.setValue(10209, "Health Expenditure per Capita", "2017");
//        dataset2.setValue(9877, "Health Expenditure per Capita", "2016");
//        dataset2.setValue(9491, "Health Expenditure per Capita", "2015");
//        dataset2.setValue(9023, "Health Expenditure per Capita", "2014");
//        dataset2.setValue(8599, "Health Expenditure per Capita", "2013");
//        dataset2.setValue(8399, "Health Expenditure per Capita", "2012");
//        dataset2.setValue(8130, "Health Expenditure per Capita", "2011");
//        dataset2.setValue(7930, "Health Expenditure per Capita", "2010");

        CategoryPlot plot = new CategoryPlot();
        plot.setDataset(0, datasetD());
        plot.setRenderer(0,  new BarRenderer());
        CategoryAxis domainAxis = new CategoryAxis("Year");
        plot.setDomainAxis(domainAxis);
        plot.setRangeAxis(new NumberAxis("NHPI"));


        plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
        plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis

        barChart = new JFreeChart("NHPI of Cities Over Time",
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

        barChartPanel = new ChartPanel(barChart);
        barChartPanel.setPreferredSize(new Dimension(400, 300));
        barChartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        barChartPanel.setBackground(Color.white);
    }

    private void createLine(JPanel west) {
//

        chart = ChartFactory.createXYLineChart("NHPI of Cities Over Time", "Year", "NHPI",  dataset(),
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

    }


    private void createTimeSeries(JPanel west) {
//        TimeSeries series1 = new TimeSeries("Mortality/1000 births");
//        series1.add(new Year(2018), 5.6);
//        series1.add(new Year(2017), 5.7);
//        series1.add(new Year(2016), 5.8);
//        series1.add(new Year(2015), 5.8);
//        series1.add(new Year(2014), 5.9);
//        series1.add(new Year(2013), 6.0);
//        series1.add(new Year(2012), 6.1);
//        series1.add(new Year(2011), 6.2);
//        series1.add(new Year(2010), 6.4);

//        TimeSeries series2 = new TimeSeries("Health Expenditure per Capita");
//        series2.add(new Year(2018), 10624);
//        series2.add(new Year(2017), 10209);
//        series2.add(new Year(2016), 9877);
//        series2.add(new Year(2015), 9491);
//        series2.add(new Year(2014), 9023);
//        series2.add(new Year(2013), 8599);
//        series2.add(new Year(2012), 8399);
//        series2.add(new Year(2011), 8130);
//        series2.add(new Year(2010), 7930);
//        TimeSeriesCollection dataset2 = new TimeSeriesCollection();
//        dataset2.addSeries(series2);

//        TimeSeries series3 = new TimeSeries("Hospital Beds/1000 people");
//        series3.add(new Year(2018), 2.92);
//        series3.add(new Year(2017), 2.87);
//        series3.add(new Year(2016), 2.77);
//        series3.add(new Year(2015), 2.8);
//        series3.add(new Year(2014), 2.83);
//        series3.add(new Year(2013), 2.89);
//        series3.add(new Year(2012), 2.93);
//        series3.add(new Year(2011), 2.97);
//        series3.add(new Year(2010), 3.05);

        TimeSeriesCollection dataset = new TimeSeriesCollection();
//        dataset.addSeries(series1);
//        dataset.addSeries(series3);

        XYPlot plot = new XYPlot();
        XYSplineRenderer splinerenderer1 = new XYSplineRenderer();
        XYSplineRenderer splinerenderer2 = new XYSplineRenderer();

        plot.setDataset(0, dataset());
        plot.setRenderer(0, splinerenderer1);
        DateAxis domainAxis = new DateAxis("Year");
        plot.setDomainAxis(domainAxis);
        plot.setRangeAxis(new NumberAxis("NHPI"));

//        plot.setDataset(1, dataset2);
        plot.setRenderer(1, splinerenderer2);
        plot.setRangeAxis(1, new NumberAxis("?"));

        plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
        plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis

        JFreeChart chart = new JFreeChart("NHPI of Cities Over Time",
                new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

//        chartTimeSerisePanel = new ChartPanel(chart);
//        chartPanel.setPreferredSize(new Dimension(400, 300));
//        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
//        chartPanel.setBackground(Color.white);
//        west.add(chartPanel);

        chartTimeSeriesPanel = new ChartPanel(chart);
        chartTimeSeriesPanel.setPreferredSize(new Dimension(400, 300));
        chartTimeSeriesPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartTimeSeriesPanel.setBackground(Color.white);

    }

    private ArrayList<Node> getInfo(){
        return result;
    }


    private XYSeriesCollection dataset(){
        double year = 0;
        double month = 0;
        XYSeriesCollection dataset = new XYSeriesCollection();
        for(String location: locations){
            XYSeries xy = new XYSeries(location);
            dataset.addSeries(xy);
            for(Node node : result){
                if(location.equals(node.getLocation())){
                    int startYear = Integer.parseInt(startTime.substring(0,4));
                    ArrayList<Double> temp = merge(node);
                    for(int i = 0 ;i<temp.size();i++){
                        xy.add(startYear+i,temp.get(i));
                    }
                }
            }
        }
        return dataset;
    }
    private DefaultCategoryDataset datasetD(){
        DefaultCategoryDataset data = new DefaultCategoryDataset();
        if(!(locations ==null)) {
            for (String loc : locations) {
                for (Node node : result) {
                    if(loc.equals(node.getLocation())) {
                        int startYear = Integer.parseInt(startTime);
                        ArrayList<Double> temp = merge(node);
                        System.out.println(temp);
                        for (double value : temp) {
                            data.addValue(value, loc, "" + startYear);
                            startYear++;
                        }
                    }
                }
            }
        }
        return data;
    }
    private ArrayList<Double> merge(Node node){
        ArrayList<Double> result = new ArrayList<>();
        double temp = 0;
        double counter = 1;
        int startYear = Integer.parseInt(startTime);
        int startMonth = Integer.parseInt(startTime.substring(startTime.length()-2));
        int endYear = Integer.parseInt(endTime.substring(0,4));
        int endMonth = Integer.parseInt(endTime.substring(endTime.length()-2));
        int i = 0;

        while((startYear*100+startMonth)!=(endYear*100+endMonth+1)&&i<node.getData().size()){
            if(startMonth>13){
                startYear++;
                startMonth = 1;
                temp = temp/counter;
                result.add(temp);
                counter=1;
            }
            temp += node.getData().get(i);
            i+=3;
            counter++;
            startMonth++;
        }
        return result;


    }
    private double getAverage(Node node){
        ArrayList<Double> temp = merge(node);
        double temp1 = 0;
        double counter = 0;
        for(int i = 0;i<temp.size();i++){
            temp1 += temp.get(i);
            counter++;
            if(i==temp.size()-1){
                temp1 /= counter;
            }
        }
        return temp1;
    }

    public void updateView(JPanel west){

        west.removeAll();
        createReport(west);
        for(String panel:visuals){
            if(panel.equals("Line Chart")){
                createLine(west);
                west.add(chartPanel);
            }
            else if(panel.equals("Time Series Chart")){
                createTimeSeries(west);
                west.add(chartTimeSeriesPanel);
            }
            else if(panel.equals("Bar Chart")){
                createBar(west);
                west.add(barChartPanel);
            }
            else if(panel.equals("Scatter Chart")){
                createScatter(west);
                west.add(scatterTimeSeriesPanel);
            }
        }

    }
    private void forForecasting(ArrayList<Double> data,String city){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int end = Integer.parseInt(endTime);
        int i = 1;
        for(double dataTemp : data){
            if(i >=13){
                i = 1;
                end++;
            }
            dataset.addValue(dataTemp,city,end+"-"+i);
            i++;
        }
        JFreeChart barchart = ChartFactory.createBarChart(
                "Forecasting",
                "Time",
                "NHPI",
                dataset);
        ChartPanel bar = new ChartPanel(barchart);
        bar.setPreferredSize(new Dimension(400,300));
        bar.setBackground(Color.WHITE);
        bar.setVisible(true);
        west.add(bar);
        SwingUtilities.updateComponentTreeUI(west);

    }


    public void makeDialogBox(String dialog){
        JDialog locationFrame = new JDialog(this);
        locationFrame.setDefaultCloseOperation(HIDE_ON_CLOSE);
        JLabel message = new JLabel(dialog, SwingConstants.CENTER);
        locationFrame.getContentPane().add(message, BorderLayout.CENTER);
        locationFrame.pack();
        locationFrame.setSize(500, 150);
        locationFrame.setLocationRelativeTo(null);
        locationFrame.setVisible(true);
    }
    private boolean checkThershold(){
        if(visuals.size()>2){
            return false;
        }
        else{
            return true;
        }
    }



    public static void main(String[] args) {

        JFrame frame = MainUI.getInstance();
        frame.setSize(900, 600);
        frame.pack();
        frame.setVisible(true);
    }

}