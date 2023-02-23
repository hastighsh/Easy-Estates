package backend;

import com.mysql.jdbc.*;
import java.sql.*;

public class MySql implements DataBase {
    private String city;
    private String time;
    ResultSet rs;

    public MySql() throws SQLException  {
        city = "Canada";
        time = "1981-01";
        execute();

        }
        private void execute() throws SQLException {
            String connection = "jdbc:mysql://localhost:3306/EECS3311";
            String username = "root";
            String password = "01240124Lwz";
            Connection con = DriverManager.getConnection(connection,username,password);
            System.out.println("driver successfully loaded");
            Statement stmt = con.createStatement();
            System.out.println("statement created");
            rs = stmt.executeQuery(queryInfo());
            while (rs.next()){
                String str0 = rs.getString("REF_DATE");
                String str1 = rs.getString("GEO");
                double value = rs.getFloat("VALUE");
                System.out.printf("%s %s %f\n",str0,str1,value);
        }
    }
    private String queryInfo(){
        return "SELECT GEO,REF_DATE,VALUE FROM EECS3311.18100205 WHERE GEO = '" + city +"' AND REF_DATE = '" + time + "'";
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
}
