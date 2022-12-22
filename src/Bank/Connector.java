package Bank;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    private static Connection connection;
    public static Connection getConnection(){
        if (connection == null){
            try{
                String Url = "jdbc:mysql://localhost:3306/BANK";
                String username = "root";
                String pass = "125125";
                Driver driver = new com.mysql.cj.jdbc.Driver();
                DriverManager.registerDriver(driver);
                connection = DriverManager.getConnection(Url, username, pass);
                System.out.println("Success");
            }catch (SQLException exception){
                exception.printStackTrace();
            }
        }
        return connection;
    }
    void test(){
        Connection temp = getConnection();
    }

    public static void main(String[] args) {
        Connector db = new Connector();
        db.test();
    }
}

