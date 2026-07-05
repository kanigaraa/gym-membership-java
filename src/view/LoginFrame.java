package view;

import controller.AuthController;

import javax.swing.*;

public class LoginFrame extends JFrame {

    private JPanel mainPanel;
    private JLabel lblTitle;
    private JLabel lblSubtitle;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JButton btnLogin;

    private final AuthController authController;

    public LoginFrame() {
        authController = new AuthController();

        setTitle("Gym Membership Management");
        setContentPane(mainPanel);
        setSize(300, 240);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        btnLogin.addActionListener(e -> login());
    }

    private void login() {
        String username = tfUsername.getText().trim();
        String password = new String(pfPassword.getPassword());

        // Validasi input
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Username dan Password wajib diisi!",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Proses login
        if (authController.login(username, password)) {
            JOptionPane.showMessageDialog(
                    this,
                    "Login berhasil!",
                    "Informasi",
                    JOptionPane.INFORMATION_MESSAGE
            );

            DashboardFrame dashboard = new DashboardFrame();
            dashboard.setVisible(true);
            dispose();

        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Username atau Password salah!",
                    "Login Gagal",
                    JOptionPane.ERROR_MESSAGE
            );

            pfPassword.setText("");
            tfUsername.requestFocus();
        }
    }
}