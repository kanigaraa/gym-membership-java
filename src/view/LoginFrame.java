package view;

import javax.swing.*;

public class LoginFrame extends JFrame {
    private JPanel panelMain;
    private JLabel lblTitle;
    private JLabel lblSubtitle;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JButton btnLogin;

    public LoginFrame() {
        setTitle("Login");
        setContentPane(panelMain);
        setSize(420, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        btnLogin.addActionListener(e -> login());
    }

    private void login() {
        String username = tfUsername.getText();
        String password = new String(pfPassword.getPassword());

        if (username.equals("admin") && password.equals("admin123")) {
            JOptionPane.showMessageDialog(this, "Login berhasil!");
            new DashboardFrame().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Username atau password salah!");
            pfPassword.setText("");
            tfUsername.requestFocus();
        }
    }
}