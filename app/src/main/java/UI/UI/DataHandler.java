package UI.UI;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.ArrayList;
import java.util.Objects;

public class DataHandler {
    MainUI main;
    static DataHandler dataHandler;
    public static DataHandler getInstance(){
        if(dataHandler==null){
            dataHandler = new DataHandler();
        }
        return dataHandler;
    }
    public DataHandler(){
        main = MainUI.getInstance();
    }
    protected XYSeriesCollection dataset(){
        double year = 0;
        double month = 0;
        XYSeriesCollection dataset = new XYSeriesCollection();
        for(String location: main.getLocations()){
            XYSeries xy = new XYSeries(location);
            dataset.addSeries(xy);
            for(MainUI.Node node : main.getResult()){
                if(location.equals(node.getLocation())){
                    int startYear = Integer.parseInt(Objects.requireNonNull(main.getStartTime()));
                    ArrayList<Double> temp = merge(node);
                    for(int i = 0 ;i<temp.size();i++){
                        xy.add(startYear+i,temp.get(i));
                    }
                }
            }
        }
        return dataset;
    }

    protected DefaultCategoryDataset datasetD(){
        DefaultCategoryDataset data = new DefaultCategoryDataset();
        if(!(main.getLocations() ==null)) {
            for (String loc : main.getLocations()) {
                for (MainUI.Node node : main.getResult()) {
                    if(loc.equals(node.getLocation())) {
                        int startYear = Integer.parseInt(main.getStartTime());
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
    protected ArrayList<Double> merge(MainUI.Node node){
        ArrayList<Double> result = new ArrayList<>();
        double month = 1;
        int startYear = Integer.parseInt((String) Objects.requireNonNull(main.getStartTime()));
        int endYear = Integer.parseInt((String) Objects.requireNonNull(main.getEndTime()));
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
    protected double getAverage(MainUI.Node node){
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

}
