package Bank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login {
    private JTextField usernameInput;
    private JTextField passInput;
    private JPanel Login;
    private JButton loginButton;
    JFrame frame = new JFrame("Login");

    public Login() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameInput.getText();
                String password = passInput.getText();
                try{
                    Connection con = Connector.getConnection();
                    Statement stat = con.createStatement();
                    String sql = "select * from teller where username = '"+username+"'";
                    ResultSet resultSet = stat.executeQuery(sql);
                    while (resultSet.next()){
                        if(username.equals(resultSet.getString("username")) &&
                           password.equals(resultSet.getString("password"))){
                            String id = resultSet.getString("id");
                            String user = resultSet.getString("username");
                            String pass = resultSet.getString("password");
                            String name = resultSet.getString("name");
                            JOptionPane.showMessageDialog(null, "Berhasi Login");
                                frame.setVisible(false);
                                Teller tel = new Teller(id, name, username, password);
                                DashBoard.open(tel);
                        }else {
                            JOptionPane.showMessageDialog(null, "gagal");
                        }
                    }
                }catch (SQLException exception){
                    exception.printStackTrace();
                }
            }
        });
    }

    public JFrame open(){
        frame.setContentPane(new Login().Login);
        frame.setPreferredSize(new Dimension(300,300));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return frame;
    }
}
