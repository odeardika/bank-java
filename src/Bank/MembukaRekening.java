package Bank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MembukaRekening {
    private JPanel DashBoard;
    private JLabel Judul;
    private JButton simpanButton;
    private JTextField txtUsername;
    private JTextField txtSaldo;
    private JComboBox JRekening;
    private JTextField txtNama;
    private JTextField txtPassword;
    private JTextField txtNomber;
    private JPanel MembukaRekening;
public MembukaRekening() {
        simpanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nama = txtNama.getText();
                String username = txtUsername.getText();
                String password = txtPassword.getText();
                String number = txtNomber.getText();
                String jenis;
                if (JRekening.getSelectedIndex() == 0){
                    jenis = "Biasa";
                }else {
                    jenis = "Bisnis";
                }
                int saldo = Integer.parseInt(txtSaldo.getText());
                if (jenis == "Bisnis" && saldo < 500000){
                    JOptionPane.showMessageDialog(null,"Saldo kurang untuk membuka rekening bisnis baru");
                } else if (jenis == "Biasa" && saldo < 100000) {
                    JOptionPane.showMessageDialog(null,"Saldo kurang untuk membuka rekening baru");
                } else{
                    try{
                        Connection con = Connector.getConnection();
                        String sql = "INSERT INTO NASABAH (nama, username, pass, saldo, jenis, no_telp) " +
                                "VALUES (?, ?, ?, ?, ?, ?)";
                        PreparedStatement prep = con.prepareStatement(sql);
                        prep.setString(1, nama);
                        prep.setString(2, username);
                        prep.setString(3, password);
                        prep.setInt(4, saldo);
                        prep.setString(5, jenis);
                        prep.setString(6, number);
                        prep.executeUpdate();
                        prep.close();
                        JOptionPane.showMessageDialog(null, "Pembuatan Rekening Berhasil");
                    }catch (SQLException exception){
                        exception.printStackTrace();
                    }
                }
            }
        });
    }
    public static void open(Teller tel){
        Bank.DashBoard dsb = new DashBoard(tel);

        JFrame frame = new JFrame("BANK");
        frame.setContentPane(new MembukaRekening().MembukaRekening);
        frame.setJMenuBar(dsb.MenuBarTemplate(tel));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500,500));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
