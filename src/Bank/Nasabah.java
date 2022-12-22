package Bank;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Nasabah extends Person {
    String no_rekening;
    String no_telp;
    String jenis;
    int saldo;

    Nasabah(String id, String name, String username, String password){
        super.name = name;
        super.username = username;
        setPassword(password);
    }
    Nasabah(String id, String name, String username, String password, String no_rekening, String no_telp, String jenis, int saldo){
        super.name = name;
        super.username = username;
        setPassword(password);
        this.no_rekening = no_rekening;
        this.no_telp = no_telp;
        this.jenis = jenis;
        this.saldo = saldo;
    }

    Nasabah (String no_rekening){
        this.no_rekening = no_rekening;
        try{
            Connection con = Connector.getConnection();
            Statement stat = con.createStatement();
            String sql = "select name, username, password, no_telp, jenis, saldo from teller where username = '"+no_rekening+"'";
            ResultSet resultSet = stat.executeQuery(sql);
            while (resultSet.next()){
                this.name = resultSet.getString("name");
                this.username = resultSet.getString("username");
                setPassword(resultSet.getString("password"));
                this.no_telp = resultSet.getString("no_telp");
                this.jenis = resultSet.getString("jenis");
                this.saldo = resultSet.getInt("saldo");
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }
    }


}
