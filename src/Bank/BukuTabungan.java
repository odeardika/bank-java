package Bank;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BukuTabungan {
    private JPanel BukuTabungan;
    private JTextField textField1;
    private JButton cekTabunganButton;
    private JTable table1;
    private DefaultTableModel model = new DefaultTableModel();


    public BukuTabungan(){

        model.addColumn("No. Rekening");
        model.addColumn("Username");
        model.addColumn("Teller");
        model.addColumn("Jenis Transaksi");
        model.addColumn("Jumlah Nominal Transaksi");
        model.addColumn("Tanggal");
        table1.setModel(model);


        cekTabunganButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String no_rek = textField1.getText();
                model.getDataVector().removeAllElements();
                model.fireTableDataChanged();

                try{
                    Connection connection = Connector.getConnection();
                    Statement statement = connection.createStatement();
                    String sql = "SELECT rekening, username, teller, jenis_transaksi, selish_saldo, tanggal_transaksi  FROM transaksi where rekening = "+ no_rek;
                    ResultSet resultSet = statement.executeQuery(sql);
                    while (resultSet.next()){
                        Object[] objects = new Object[6];
                        objects[0] = resultSet.getString("rekening");
                        objects[1] = resultSet.getString("username");
                        objects[2] = resultSet.getString("teller");
                        objects[3] = resultSet.getString("jenis_transaksi");
                        objects[4] = resultSet.getInt("selish_saldo");
                        objects[5] = resultSet.getDate("tanggal_transaksi");
                        model.addRow(objects);
                    }
                    resultSet.close();
                    statement.close();
                }catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }
    static void open(Teller tel){
        Bank.DashBoard dsb = new DashBoard(tel);
        JFrame frame = new JFrame("BukuTabungan");
        frame.setContentPane(new BukuTabungan().BukuTabungan);
        frame.setJMenuBar(dsb.MenuBarTemplate(tel));
        frame.setPreferredSize(new Dimension(500, 500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
