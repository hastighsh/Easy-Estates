package backend;


import LogicAndComparsion.Logic;
import LogicAndComparsion.TimeSeries;
import LogicAndComparsion.Location;
import com.mysql.jdbc.*;
import java.sql.*;
import java.util.ArrayList;

public class MySql implements DataBase {
    private String city;
    private String time;
    private Statement stmt;
    ResultSet rs;
    ArrayList<Double> arr2;

    public MySql() throws SQLException  {
        city = "Canada";
        time = "1981";
        execute();

        }
        public MySql(Location location,TimeSeries time) throws SQLException {
            arr2 = new ArrayList<>();
            city = location.getName();
            this.time = time.getTempStart();
            execute();
            while (!time.increasement()){
                exec();
                this.time = time.getTempStart();
            }
            this.time = time.getTempStart();
            exec();
        }
        private void execute() throws SQLException {
            String connection = "jdbc:mysql://localhost:3306/EECS3311";
            String username = "root";
            String password = "01240124Lwz";
            Connection con = DriverManager.getConnection(connection,username,password);
            System.out.println("driver successfully loaded");
            stmt = con.createStatement();
            System.out.println("statement created");
            exec();
    }
    private void exec() throws SQLException {
        rs = stmt.executeQuery(queryInfo());

        while (rs.next()){
            arr2.add(rs.getDouble("VALUE"));
        }
    }

    private String queryInfo(){
        return  "SELECT GEO,REF_DATE,VALUE " +
                "FROM EECS3311.18100205 " +
                "WHERE GEO = '" + city +"' AND REF_DATE LIKE '" + time + "%'";
    }

    public void setCity(String city){
        this.city = city;
    }


    public void setTime(String time){
        this.time = time;
    }

    @Override

    public ResultSet getData() {
        return rs;
    }

    @Override
    public ArrayList<Double> getIndex() {
        return arr2;
    }
}
