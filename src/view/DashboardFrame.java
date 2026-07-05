package view;

import javax.swing.*;

public class DashboardFrame extends JFrame {

    private JPanel mainPanel;

    private JLabel lblTitle;
    private JLabel lblSubtitle;
    private JLabel lblWelcome;

    private JButton btnMember;
    private JButton btnMembership;
    private JButton btnLogout;

    public DashboardFrame() {

        setTitle("Dashboard");
        setContentPane(mainPanel);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        lblTitle.setText("GYM MEMBERSHIP MANAGEMENT");
        lblSubtitle.setText("Dashboard");
        lblWelcome.setText("Welcome, Admin!");

        btnMember.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Halaman Data Member akan dibuat oleh Khaliz.")
        );

        btnMembership.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Halaman Membership akan dibuat oleh Luthfi.")
        );

        btnLogout.addActionListener(e -> {

            int pilih = JOptionPane.showConfirmDialog(
                    this,
                    "Yakin ingin logout?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION
            );

            if (pilih == JOptionPane.YES_OPTION) {
                new LoginFrame().setVisible(true);
                dispose();
            }

        });
    }
}
