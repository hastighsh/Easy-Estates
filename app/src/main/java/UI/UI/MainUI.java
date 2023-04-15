package UI.UI;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Vector;
import javax.swing.*;
import javax.swing.text.Utilities;

import LogicAndComparsion.*;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import static org.apache.commons.math3.util.Precision.round;

public class MainUI extends JFrame {
    public static class Node implements Comparable {
        private String location;
        ArrayList<Double> data;
        protected Node(String location,ArrayList<Double> data){
            this.location =location;
            this.data = data;
        }
        protected ArrayList<Double> getData(){
            return data;
        }
        private void setData(ArrayList<Double> data){
            this.data = data;
        }

        protected String getLocation() {
            return location;
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
    private static ArrayList<Charts> visuals = new ArrayList<>();
    private static ArrayList<String> times = new ArrayList<>();
    Forcasting forcasting = new Forcasting();
    private  ArrayList<Node> result = new ArrayList<>();

    DataHandler dataHandler;
    Logic log = new Logic();
    private static int counter = 0;
    JComboBox<String> fromList, toList,methodsList;
    JPanel west, east;
    ChartPanel barChart;
    JTextArea report,tableF;
    JScrollPane outputScrollPane;
    String startTime= "";
    String endTime = "";
    String table = "report";
    Linechart line;
    Barchart bar;
    Scatterchart scatterChart;
    TimeSeriseChart timeSeriseChart;
    Charts barC,lineC,scatterC,timeC;

    public static MainUI getInstance() {
        if (instance == null) {
            instance = new MainUI();
        }

        return instance;
    }

    private MainUI() {
        // Set window title
        super("Easy Estates");
        init();
    }
    private void init(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        dataHandler = new DataHandler(startTime,endTime,locations,result);
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
                table = "report";
                updateView(west);
                SwingUtilities.updateComponentTreeUI(west);

            }
        });


        descriptiveDataButton.addActionListener(e -> {
            if(descriptiveDataButton.isSelected()){

                table = "descriptive";
                updateView(west);
                SwingUtilities.updateComponentTreeUI(west);

            }
        });

        // location drop-down menu
        String[] arr = {
                "Atlantic Region","Quebec","Ontario","Prairie Region","British Columbia","Newfoundland and Labrador",
                "Prince Edward Island","Nova Scotia","New Brunswick","St. John's, Newfoundland and Labrador",
                "Charlottetown, Prince Edward Island","Halifax, Nova Scotia","Saint John, Fredericton, and Moncton, New Brunswick",
                "Québec, Quebec","Sherbrooke, Quebec","Trois-Rivières, Quebec","Montréal, Quebec","Ottawa-Gatineau, Quebec part, Ontario/Quebec",
                "Ottawa-Gatineau, Ontario part, Ontario/Quebec","Oshawa, Ontario","Toronto, Ontario","Hamilton, Ontario",
                "St. Catharines-Niagara, Ontario","Kitchener-Cambridge-Waterloo, Ontario","Guelph, Ontario","London, Ontario",
                "Windsor, Ontario","Greater Sudbury, Ontario","Manitoba","Saskatchewan","Alberta","Winnipeg, Manitoba",
                "Regina, Saskatchewan","Saskatoon, Saskatchewan","Calgary, Alberta","Edmonton, Alberta","Kelowna, British Columbia",
                "Vancouver, British Columbia","Victoria, British Columbia","Canada"};
        JLabel chooseCountryLabel = new JLabel("Choose a Location: "); // setting a text
        Vector<String> countriesNames = new Vector<>(Arrays.asList(arr)); // setting a drop-down menu vector
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

        Vector<String> methodsNames = new Vector<>();
        methodsNames.add("Linear Regression Module");
        methodsNames.add("SMO Regression Module");
        methodsList = new JComboBox<>(methodsNames);
        JButton recalculate = new JButton("Forecast");
        recalculate.addActionListener(forcasting);

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

                startTime = Objects.requireNonNull(fromList.getSelectedItem()).toString();
                endTime = Objects.requireNonNull(toList.getSelectedItem()).toString();
                int start = Integer.parseInt(startTime);
                int end = Integer.parseInt(endTime);

                if(start<end) {

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
                                result.add(new Node(location.getName(), get));
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
                                    result.add(new Node(location.getName(), get));
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
        JLabel methodLabel = new JLabel("Forecasting methods: ");


        JLabel viewsLabel = new JLabel("Available Views: ");


        // Available views
        Vector<Charts> viewsNames = new Vector<>();
        barC = new Barchart(dataHandler.dataset());
        lineC = new Linechart(dataHandler.dataset());
        scatterC = new Scatterchart(dataHandler.dataset());
        timeC = new TimeSeriseChart(dataHandler.dataset());
        viewsNames.add(barC);
        viewsNames.add(lineC);
        viewsNames.add(scatterC);
        viewsNames.add(timeC);

        JComboBox<Charts> viewsList = new JComboBox<>(viewsNames);
        JButton addView = new JButton("+");
        addView.addActionListener(e->{ // adding different views to the screen
            if(checkThreshold()){ // check the threshold ( <=3 )
                if(!visuals.contains((Charts) viewsList.getSelectedItem())){
                    visuals.add((Charts) viewsList.getSelectedItem());
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
            else if(!visuals.contains(viewsList.getSelectedItem())){
                makeDialogBox("Your choice does not exist");
            }
            else{
                visuals.remove(viewsList.getSelectedItem());
                updateView(west);
                SwingUtilities.updateComponentTreeUI(west);
            }
        });


        // forecasting drop-down menu, button, ...


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
                JComboBox<String> fromTime = new JComboBox<>(years);
                JComboBox<String> toTime = new JComboBox<>(years);
                for (Node node : result) {
                    city1.addItem(node.getLocation());
                }
                JLabel city1Label = new JLabel("City:");
                JLabel fromL = new JLabel("form:");
                JLabel toL = new JLabel("to: ");
                JButton submit = new JButton("submit");
                window.add(city1Label);
                window.add(city1);
                window.add(fromL);
                window.add(fromTime);
                window.add(toL);
                window.add(toTime);
                window.add(submit);
                window.setLayout(new FlowLayout());
                window.pack();
                window.setVisible(true);
                submit.addActionListener(c -> {
                    try {
                        Location location1 = new Location((String)city1.getSelectedItem());
                        Time start1 = new Time((String) fromList.getSelectedItem());        // user gives this (comes as parameter from UI call, logic.AddTimeSeries(place, startTime, endTime);
                        Time end1 = new Time((String) toList.getSelectedItem());
                        Time start2 = new Time((String) fromTime.getSelectedItem());
                        Time end2 = new Time((String) toTime.getSelectedItem());
                        int startTemp = Integer.parseInt(start2.getName());
                        int endTemp = Integer.parseInt(end2.getName());
                        if(start1.equals(start2)&&end1.equals(end2)){
                            makeDialogBox("please select two different time series");
                        }
                        else if(startTemp>=endTemp){
                            makeDialogBox("select correct time series please");
                        }

                        else{
                            window.dispose();
                            ArrayList<Double> data1 = log.fetchData(location1, start1, end1);//  fetchData(place, startTime, endTime) returns data1, data2
                            System.out.println(data1);
                            ArrayList<Double> data2 = log.fetchData(location1, start2, end2);
                            System.out.println(data2);
                            LogicAndComparsion.TimeSeries tseries1 = new LogicAndComparsion.TimeSeries(data1, start1, end1);
                            LogicAndComparsion.TimeSeries tseries2 = new LogicAndComparsion.TimeSeries(data2, start1, end1);
                            StatsComparison sc = log.compareTimeSeries(tseries1, tseries2);
                            System.out.println(sc.getPValue());
                            System.out.println(sc.getConclusion());
                            east.remove(outputScrollPane);
                            createCompareFrame(sc.getPValue(),(String) city1.getSelectedItem(),sc.getConclusion(),east);
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                });
            }
        });


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
        createReport(west,"report");
    }

    // create the raw data table
    private void createReport(JPanel west,String type) {
        report = new JTextArea();
        report.setEditable(false);
        report.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        report.setBackground(Color.white);
        String reportMessage = "";
        System.out.println(type);

        //raw data table
        if(type.equals("report")){
            int i = 0;
            reportMessage = "City NHPI Over Time Raw Data\n" + "==============================\n";
            if (locations.size() > 0) {
                for(int j = 0;j<result.size();j++) {
                    String dataInfo = result.get(j).getLocation() + "\n" + "\tStart Date _ End Date: " + startTime + " / " + endTime + "\n"
                            + "\tNHPI: " + String.format("%.2f",dataHandler.getAverage(result.get(j))) + "\n";
                    reportMessage = reportMessage.concat(dataInfo);
                    i++;
                }
            }
        }
        else if(type.equals("descriptive")){
            reportMessage = "City NHPI Over Time Descriptive Data\n" + "==============================\n";
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
        }

        //descriptive data table

        report.setText(reportMessage);
        outputScrollPane = new JScrollPane(report);
        outputScrollPane.setPreferredSize(new Dimension(400, 300));
        west.add(outputScrollPane);
    }

    // create the descriptive data table


    // based on the threshold and the user choice update the view
    public void updateView(JPanel west){
        dataHandler = new DataHandler(startTime,endTime,locations,result);
        for(Charts temp:visuals){
            temp.update(dataHandler.dataset());
        }
        west.removeAll();
        System.out.println(visuals.contains(barC));
        createReport(west,table);
        west.add(outputScrollPane);
        for(Charts panel:visuals){
           west.add(panel.getChart());
        }
        SwingUtilities.updateComponentTreeUI(west);
    }

    //making a barchart for forecasting method
    protected void forForecasting(ArrayList<Double> data,String city,String type){
        if(type.equals("chart")){
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
                    "Forecasting("+ methodsList.getSelectedItem() +")",
                    "Time",
                    "NHPI",
                    dataset);
            barChart = new ChartPanel(barchart);
            barChart.setPreferredSize(new Dimension(400,300));
            barChart.setBackground(Color.WHITE);
            barChart.setVisible(true);

            east.add(barChart);
        }
        else if(type.equals("table")){
            tableF = new JTextArea();
            tableF.setEditable(false);
            tableF.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            tableF.setBackground(Color.white);
            String text = String.format("Forecasting(%s)\n" +
                    "-------------------------------------\n" +
                    "%s: \n",methodsList.getSelectedItem(),city);
            text += log.getModule().stats();
            tableF.setText(text);
            tableF.setPreferredSize(new Dimension(400,300));

            east.add(tableF);
        }
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
    private void createCompareFrame(Double data,String city1,String conclusion,JPanel east){
        report = new JTextArea();
        report.setEditable(false);
        report.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        report.setBackground(Color.white);
        String reportMessage = String.format("Comparsion result :\n" +
                "(%s) :\n" +
                "P_Value: %.4f\n" +
                "%s",city1,data,conclusion);
        report.setText(reportMessage);
        outputScrollPane = new JScrollPane(report);
        outputScrollPane.setPreferredSize(new Dimension(400, 300));
        east.add(outputScrollPane);
        SwingUtilities.updateComponentTreeUI(east);
    }

    protected ArrayList<Node> getResult(){
        return result;
    }
    protected ArrayList<String> getLocations(){
        return locations;
    }
    protected String getStartTime(){
        return startTime;
    }
    protected String getEndTime(){
        return endTime;
    }

    public static void main(String[] args) {

        JFrame frame = MainUI.getInstance();
        frame.setPreferredSize(new Dimension(1200, 700));
        frame.pack();
        frame.setVisible(true);
    }

}