package Bank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.sql.*;

public class Transaksi {
    private JPanel Transaksi;
    private JTextField txtNo_Rek;
    private JComboBox txtMenarikUang;
    private JTextField txtBanyak;
    private JButton jalankanButton;

    public Transaksi(Teller tel) {
        jalankanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String no_rekening = txtNo_Rek.getText();
                int banyak = Integer.parseInt(txtBanyak.getText());
                String jenis;
                String jRek = "";
                String username = "";
                int saldoSekarang = 0;
                try {
                    Connection con = Connector.getConnection();
                    Statement stat = con.createStatement();
                    String sql = "select saldo, jenis, username from nasabah where no_rekening = " + no_rekening + "";
                    ResultSet resultSet = stat.executeQuery(sql);
                    while (resultSet.next()) {
                        saldoSekarang = resultSet.getInt(1);
                        jRek = resultSet.getString(2);
                        username = resultSet.getString(3);
                    }

                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
                if (txtMenarikUang.getSelectedIndex() == 0) {
                    jenis = "Penarikan";
                    System.out.println(jenis);
                    if (new String(jRek).equals("Biasa")) {
                        if (saldoSekarang <= 150000) {
                            JOptionPane.showMessageDialog(null, "Saldo kurang untuk transaksi");
                        } else if (banyak > 5000000) {
                            JOptionPane.showMessageDialog(null, "Jumlah enarikan melebihi maksimum penarikan harian");
                        } else {
                            prosesTransaksi(jenis, banyak, no_rekening);
                            inputDatakeDB(no_rekening, saldoSekarang, saldoSekarang-banyak, banyak, jenis, tel.id, username );
                            JOptionPane.showMessageDialog(null,"Transaksi Sukses");
                        }
                    } else if (new String(jRek).equals("Bisnis")) {
                        if (saldoSekarang <= 200000) {
                            JOptionPane.showMessageDialog(null, "Saldo kurang untuk transaksi");
                        } else if (banyak > 50000000) {
                            JOptionPane.showMessageDialog(null, "Jumlah enarikan melebihi maksimum penarikan harian");
                        } else {
                            prosesTransaksi(jenis, banyak, no_rekening);
                            inputDatakeDB(no_rekening, saldoSekarang, saldoSekarang-banyak, banyak, jenis, tel.id , username);
                            JOptionPane.showMessageDialog(null,"Transaksi Sukses");
                        }
                    }

                } else {
                    jenis = "Penyetoran";
                    prosesTransaksi(jenis, banyak, no_rekening);
                    inputDatakeDB(no_rekening, saldoSekarang, saldoSekarang+banyak, banyak, jenis, tel.id, username );
                    JOptionPane.showMessageDialog(null,"Transaksi Sukses");
                }

            }
        });
    }

    public static void open(Teller tel) {
        Bank.DashBoard dsb = new DashBoard(tel);
        JFrame frame = new JFrame("Transaksi");
        frame.setContentPane(new Transaksi(tel).Transaksi);
        frame.setJMenuBar(dsb.MenuBarTemplate(tel));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 500));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    void inputDatakeDB(String rekening, int saldo_awal, int saldo_berubah, int selisih_saldo, String jenis, String teller, String username){
        try{
            Connection con = Connector.getConnection();
            String sql = "insert into transaksi (rekening, saldo_awal, saldo_berubah, selish_saldo, jenis_transaksi, teller, username)" +
                         "Values (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement prep = con.prepareStatement(sql);
            prep.setInt(1, Integer.parseInt(rekening));
            prep.setInt(2,saldo_awal);
            prep.setInt(3,saldo_berubah);
            prep.setInt(4,selisih_saldo);
            prep.setString(5,jenis);
            prep.setString(6, teller);
            prep.setString(7, username);
            prep.executeUpdate();
            prep.close();
        }catch (SQLException exception){
            exception.printStackTrace();
        }
    }
    public void prosesTransaksi(String jenis, int banyak, String no_rekening) {
        if (jenis == "Penarikan") {
            try {
                Connection con = Connector.getConnection();
                String sql = "update nasabah set saldo = saldo - ? where no_rekening = ?";
                PreparedStatement prep = con.prepareStatement(sql);
                prep.setInt(1, banyak);
                prep.setInt(2, Integer.parseInt(no_rekening));
                prep.executeUpdate();
                prep.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        } else if (jenis == "Penyetoran") {
            try {
                Connection con = Connector.getConnection();
                String sql = "update nasabah set saldo = saldo + ? where no_rekening = ?";
                PreparedStatement prep = con.prepareStatement(sql);
                prep.setInt(1, banyak);
                prep.setInt(2, Integer.parseInt(no_rekening));
                prep.executeUpdate();
                prep.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }
}
