package UI.UI;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.ArrayList;
import java.util.Objects;

public class DataHandler {
    private String startTime;
    private String endTime;
    private ArrayList<String> locations;
    private ArrayList<MainUI.Node> results;
    public DataHandler(String startTime, String endTime, ArrayList<String> locations, ArrayList<MainUI.Node> results){
        this.startTime = startTime;
        this.results = results;
        this.locations = locations;
        this.endTime = endTime;
    }
    protected XYSeriesCollection dataset(){
        double year = 0;
        double month = 0;
        XYSeriesCollection dataset = new XYSeriesCollection();
        for(String location: locations){
            XYSeries xy = new XYSeries(location);
            dataset.addSeries(xy);
            for(MainUI.Node node : results){
                if(location.equals(node.getLocation())){
                    int startYear = Integer.parseInt(Objects.requireNonNull(startTime));
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
        if(!(locations ==null)) {
            for (String loc : locations) {
                for (MainUI.Node node : results) {
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
    protected ArrayList<Double> merge(MainUI.Node node){
        ArrayList<Double> result = new ArrayList<>();
        double month = 1;
        int startYear = Integer.parseInt( Objects.requireNonNull(startTime));
        int endYear = Integer.parseInt( Objects.requireNonNull(endTime));
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
