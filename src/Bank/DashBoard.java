package Bank;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashBoard {
    private JPanel DashBoard;
    private JLabel idTeller;
    private JTable Tellertable;
    private JButton showTellerButton;
    private DefaultTableModel model = new DefaultTableModel();

    public DashBoard(Teller tel) {
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Username");
        Tellertable.setModel(model);

        showTellerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.getDataVector().removeAllElements();
                model.fireTableDataChanged();
                Object[] obj = new Object[4];
                obj[0] = tel.id;
                obj[1] = tel.name;
                obj[2] = tel.username;
                model.addRow(obj);

            }
        });
    }
    public static void open(Teller tel){
        JFrame frame = new JFrame("BANK");
        frame.setContentPane(new DashBoard(tel).DashBoard);
        frame.setJMenuBar(MenuBarTemplate(tel));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500,500));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    static public JMenuBar MenuBarTemplate(Teller tel){
        JMenuBar menu = new JMenuBar();
        //Main Menu
        JMenu MainMenu = new JMenu("Menu");
        JMenuItem DasBod = new JMenuItem("DashBoard");
        JMenuItem transaksi = new JMenuItem("Transaksi");
        JMenuItem membukaRek = new JMenuItem("Membuka Rekening");
        JMenuItem bukuTabungan = new JMenuItem("Buku Tabungan");
        DasBod.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("DashBoard");
                Bank.DashBoard.open(tel);
            }
        });
        transaksi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Transaksi");
                Transaksi trans = new Transaksi(tel);
                Transaksi.open(tel);
            }
        });
        membukaRek.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Membuka Rekening");
                MembukaRekening.open(tel);
            }
        });
        bukuTabungan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Membuka Buku Tabungan");
                BukuTabungan.open(tel);
            }
        });
        MainMenu.add(bukuTabungan);
        MainMenu.add(membukaRek);
        MainMenu.add(transaksi);
        MainMenu.add(DasBod);

        //Setting
        JMenu Setting = new JMenu("Setting");
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        Setting.add(exit);



        menu.add(MainMenu);
        menu.add(Setting);
        return menu;
    }
}
