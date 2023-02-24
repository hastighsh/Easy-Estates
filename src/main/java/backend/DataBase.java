package backend;

import LogicAndComparsion.Location;
import LogicAndComparsion.TimeSeries;

import java.sql.ResultSet;
import java.util.ArrayList;

public interface DataBase {
    /**
     *
     * @param city a specific city that want to query
     */
    public void setCity(String city);
    /**
     *
     * @param time a specific time that want to query
     */
    public void setTime(String time);
    /**
     * @return a set of result will be returned
     */
    public ResultSet getData();
    public ArrayList<Double> getIndex();
}
