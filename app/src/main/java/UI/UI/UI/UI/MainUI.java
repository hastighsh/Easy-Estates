package UI.UI;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Vector;

import javax.swing.*;

import LogicAndComparsion.Location;
import LogicAndComparsion.Logic;
import LogicAndComparsion.StatsComparison;
import LogicAndComparsion.Time;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
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

import static org.apache.commons.math3.util.Precision.round;

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
    JPanel west, east;
    JFreeChart chart,barChart;
    ChartPanel chartPanel, chartTimeSeriesPanel,barChartPanel, scatterTimeSeriesPanel;
    JTextArea report;
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

        rawDataButton.addActionListener(e -> {
            if(rawDataButton.isSelected()){
                west.remove(outputScrollPane);
                createReport(west);
                SwingUtilities.updateComponentTreeUI(west);

            }
        });

        descriptiveDataButton.addActionListener(e -> {
            if(descriptiveDataButton.isSelected()){
                west.remove(outputScrollPane);
                createDescriptiveReport(west);
                SwingUtilities.updateComponentTreeUI(west);

            }
        });

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
        JComboBox<String> countriesList = new JComboBox<>(countriesNames); // making the vector into a drop-down menu
        countriesList.setPreferredSize(new Dimension(300,30));
        countriesList.getSelectedItem();
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

        // setting the time drop down menus and labels
        JLabel from = new JLabel("From");
        JLabel to = new JLabel("To");

        Vector<String> years = new Vector<String>();
        for (int i = 2022; i >= 1981; i--) {
                years.add("" + i);
        }
        fromList = new JComboBox<>(years);
        toList = new JComboBox<>(years);


        //load button for fetching data based on the inputted values
        JButton loadData = new JButton("Load Data");

        loadData.addActionListener (e->{

            if(e.getSource()==loadData){
                east.removeAll();

                startTime = fromList.getSelectedItem().toString();
                endTime = toList.getSelectedItem().toString();
                int start = Integer.parseInt(startTime);
                int end = Integer.parseInt(endTime);

                if(!startTime.equals(endTime)) {

                    int i = 0;
                    for (String str : locations) {
                        Location location = new Location(locations.get(i));
                        Time startTime = new Time(fromList.getSelectedItem().toString());
                        Time endTime = new Time(toList.getSelectedItem().toString());
                        i++;

                        try {
                            System.out.println("fetchData");
                            ArrayList<Double> get = log.fetchData(location, startTime, endTime);
                            if(result.size()==0){ // if the result array is empty add one node to it
                                result.add(new Node(location.getName(),get));
                            }else{
                                //check the repeat node in the result
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
                else{
                    makeDialogBox("please select correct time series");
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
        // finished top bar ---------------->


        // Set bottom bar ------------------>
        JButton recalculate = new JButton("Calculate");

        // button for forecasting
        recalculate.addActionListener(e->{
            JDialog optionPane = new JDialog(this); // making a dialog box for input of month and choosing the location
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

            button.addActionListener(f->{ // action listener for submit button
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
                    makeDialogBox("Month must be a positive number.");
                }
                else{
                    east.remove(barChartPanel);
                    Logic logic = new Logic();
                    //east.remove(bar);
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
        addView.addActionListener(e->{ // adding different views to the screen
            if(checkThreshold()){ // check the threshold ( <=3 )
                if(!visuals.contains((String)viewsList.getSelectedItem())){
                    visuals.add((String)viewsList.getSelectedItem());
                    updateView(west);
                    SwingUtilities.updateComponentTreeUI(west);
                }
                else{
                    makeDialogBox("Your choice has already executed");
                }
            }
           else{
                makeDialogBox("You can only view up to 3 visualizations");
            }
        });

        JButton removeView = new JButton("-");
        removeView.addActionListener(e->{ // remove the views from screen
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
            if(result.size()==0){
                String dia = "add a city first";
                makeDialogBox(dia);
            }
            else {
                JDialog window = new JDialog(this);
                window.setLocationRelativeTo(null);
                window.setPreferredSize(new Dimension(600, 100));
                JComboBox<String> city1= new JComboBox<>();
                JComboBox<String> city2 = new JComboBox<>();
                for (Node node : result) {
                    city1.addItem(node.getLocation());
                    city2.addItem(node.getLocation());
                }
                JLabel city1Label = new JLabel("City");
                JLabel city2Label = new JLabel("City");
                JButton submit = new JButton("submit");
                window.add(city1Label);
                window.add(city1);
                window.add(city2Label);
                window.add(city2);
                window.add(submit);
                window.setLayout(new FlowLayout());
                window.pack();
                window.setVisible(true);
                submit.addActionListener(c -> {
                    try {
                        Location location1 = new Location((String)city1.getSelectedItem());
                        Location location2 = new Location((String)city2.getSelectedItem());
                        Time start1 = new Time((String) fromList.getSelectedItem());        // user gives this (comes as parameter from UI call, logic.AddTimeSeries(place, startTime, endTime);
                        Time end1 = new Time((String) toList.getSelectedItem());

                        if(location1.getName().equals(location2.getName())){
                            makeDialogBox("please select two different cities");
                        }
                        else{
                            window.dispose();
                            ArrayList<Double> data1 = log.fetchData(location1, start1, end1);//  fetchData(place, startTime, endTime) returns data1, data2
                            System.out.println(data1);
                            ArrayList<Double> data2 = log.fetchData(location2, start1, end1);
                            System.out.println(data2);
                            LogicAndComparsion.TimeSeries tseries1 = new LogicAndComparsion.TimeSeries(data1, start1, end1);
                            LogicAndComparsion.TimeSeries tseries2 = new LogicAndComparsion.TimeSeries(data2, start1, end1);
                            StatsComparison sc = log.compareTimeSeries(tseries1, tseries2);
                            System.out.println(sc.getPValue());
                            System.out.println(sc.getConclusion());
                            east.remove(outputScrollPane);
                            createCompareFrame(sc.getPValue(),(String) city1.getSelectedItem(),(String) city2.getSelectedItem(),sc.getConclusion(),east);
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                });
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

        east = new JPanel();
        east.setLayout(new GridLayout(2, 0));
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
        createTimeSeries(west);
        createBar(west);
        createScatter(west);

    }

    // create the raw data table
    private void createReport(JPanel west) {
        report = new JTextArea();
        report.setEditable(false);
        report.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        report.setBackground(Color.white);

        //raw data table
        int i = 0;
        String reportMessage = "City NHPI Over Time Raw Data\n" + "==============================\n";
        if (locations.size() > 0) {
            for(int j = 0;j<result.size();j++) {
                String dataInfo = result.get(j).getLocation() + "\n" + "\tStart Date _ End Date: " + startTime + " / " + endTime + "\n"
                        + "\tNHPI: " + String.format("%.2f",getAverage(result.get(j))) + "\n";
                reportMessage = reportMessage.concat(dataInfo);
                i++;
            }
        }

        report.setText(reportMessage);
        outputScrollPane = new JScrollPane(report);
        outputScrollPane.setPreferredSize(new Dimension(400, 300));
        west.add(outputScrollPane);
    }

    // create the descriptive data table
    private void createDescriptiveReport(JPanel west){
        report = new JTextArea();
        report.setEditable(false);
        report.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        report.setBackground(Color.white);


        int i = 0;
        String reportMessage = "City NHPI Over Time Descriptive Data\n" + "==============================\n";
        if (locations.size() > 0) {
                String dataInfo = "The descriptive data for the locations: \n";
                reportMessage = reportMessage.concat(dataInfo);

            SummaryStatistics summaryObject;
            for (Node data: result){
               summaryObject = log.getSummary(data.getData());
                dataInfo ="( " + data.getLocation() + " )\n" +
                        " Mean NHPI: " + round(summaryObject.getMean(), 2) + "\n" +
                        " Max: " + round(summaryObject.getMax(), 2) + "\n" +
                        " Min: " + round(summaryObject.getMin(), 2) + "\n" +
                        " Standard Deviation: " + round(summaryObject.getStandardDeviation(), 2) + "\n" +
                        " Variance: " + round(summaryObject.getVariance(), 2) + "\n" +
                        "--------------------------------\n";
                reportMessage = reportMessage.concat(dataInfo);

            }
        }
        report.setText(reportMessage);
        outputScrollPane = new JScrollPane(report);
        outputScrollPane.setPreferredSize(new Dimension(400, 300));
        west.add(outputScrollPane);
    }

    // create scatter chart
    private void createScatter(JPanel west) {
        JFreeChart scatterChart = ChartFactory.createScatterPlot("NHPI of Cities Over Time", "Year", "NHPI",  dataset(),
                PlotOrientation.VERTICAL, true, true, false);


        XYPlot plot = scatterChart.getXYPlot();
        XYItemRenderer itemrenderer1 = new XYLineAndShapeRenderer(false, true);
        XYItemRenderer itemrenderer2 = new XYLineAndShapeRenderer(false, true);

        plot.setRenderer(1, itemrenderer2);
        plot.setRangeAxis(1, new NumberAxis(""));

        plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
        plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis


        scatterTimeSeriesPanel = new ChartPanel(scatterChart);
        scatterTimeSeriesPanel.setPreferredSize(new Dimension(400, 300));
        scatterTimeSeriesPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        scatterTimeSeriesPanel.setBackground(Color.white);
    }

    // create bar chart
    private void createBar(JPanel west) {

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



        barChartPanel = new ChartPanel(barChart);
        barChartPanel.setPreferredSize(new Dimension(400, 300));
        barChartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        barChartPanel.setBackground(Color.white);
    }

    // create linear chart
    private void createLine(JPanel west) {

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

    // create time series chart
    private void createTimeSeries(JPanel west) {

        JFreeChart chart = ChartFactory.createXYLineChart("NHPI of Cities Over Time", "Year", "NHPI",  dataset(),
                PlotOrientation.VERTICAL, true, true, false);
        XYPlot plot = chart.getXYPlot();
        XYSplineRenderer splinerenderer1 = new XYSplineRenderer();
        XYSplineRenderer splinerenderer2 = new XYSplineRenderer();

        plot.setDataset(0, dataset());
        plot.setRenderer(0, splinerenderer1);


        plot.setRenderer(1, splinerenderer2);
        plot.setRangeAxis(1, new NumberAxis(""));

        plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
        plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis


        chartTimeSeriesPanel = new ChartPanel(chart);
        chartTimeSeriesPanel.setPreferredSize(new Dimension(400, 300));
        chartTimeSeriesPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartTimeSeriesPanel.setBackground(Color.white);

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
                    int startYear = Integer.parseInt((String) Objects.requireNonNull(fromList.getSelectedItem()));
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

    // merging the data of one city over a year
    private ArrayList<Double> merge(Node node){
        ArrayList<Double> result = new ArrayList<>();
        double month = 1;
        int startYear = Integer.parseInt((String) Objects.requireNonNull(fromList.getSelectedItem()));
        int endYear = Integer.parseInt((String) Objects.requireNonNull(toList.getSelectedItem()));
        double total = 0;
        for(double data:node.getData()){
            total += data;
            if(startYear>endYear){
                break;
            }
            if(month==12){
                total = total/12.0;
                result.add(total);
                month = 1;
                startYear++;
                total = 0;
            }
            else{
                month++;
            }
        }
        return result;
    }

    // getting the average of the data based on time for one location
    private double getAverage(Node node){
        ArrayList<Double> temp = merge(node);
        double result = 0.0;
        int counter = 0;
        for(double data:temp){
            result += data;
            counter++;
        }
        result /= counter;
        return result;

    }

    // based on the threshold and the user choice update the view
    public void updateView(JPanel west){

        west.removeAll();
        createReport(west);
        updateView(west);
    }
    private void forDiscriptive(JPanel west){
        west.removeAll();
        createDescriptiveReport(west);
        updateView(west);
    }
    public void updateView(JPanel west){

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

    //making a barchart for forecasting method
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
        barChartPanel = new ChartPanel(barchart);
        barChartPanel.setPreferredSize(new Dimension(400,300));
        barChartPanel.setBackground(Color.WHITE);
        barChartPanel.setVisible(true);
        east.add(barChartPanel);
        SwingUtilities.updateComponentTreeUI(east);

    }


    // making a simple dialog box for prompting the user
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

    // check threshold <= 3
    private boolean checkThreshold(){
        if(visuals.size()>2){
            return false;
        }
        else{
            return true;
        }
    }

    // making the compare table based on the inputted values
    private void createCompareFrame(Double data,String city1,String city2,String conclusion,JPanel east){
        report = new JTextArea();
        report.setEditable(false);
        report.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        report.setBackground(Color.white);
        String reportMessage = String.format("Comparsion result :\n" +
                "(%s) vs (%s)\n" +
                "P_Value: %.4f\n" +
                "%s",city1,city2,data,conclusion);
        report.setText(reportMessage);
        outputScrollPane = new JScrollPane(report);
        outputScrollPane.setPreferredSize(new Dimension(400, 300));
        east.add(outputScrollPane);
        SwingUtilities.updateComponentTreeUI(east);
    }



    public static void main(String[] args) {

        JFrame frame = MainUI.getInstance();
        frame.setPreferredSize(new Dimension(1200, 700));
        frame.pack();
        frame.setVisible(true);
    }

}