package view;

import controller.AuthController;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final AuthController authController;
    private final JTextField tfUsername = new JTextField(16);
    private final JPasswordField pfPassword = new JPasswordField(16);
    private final JButton btnLogin = new JButton("Login");

    public LoginFrame() {
        this(new AuthController());
    }

    LoginFrame(AuthController authController) {
        this.authController = authController;
        setTitle("Gym Membership Management");
        setContentPane(createContentPanel(tfUsername, pfPassword, btnLogin));
        setSize(420, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(btnLogin);

        btnLogin.addActionListener(e -> login());
    }

    static JPanel createContentPanel(JTextField username, JPasswordField password, JButton loginButton) {
        JPanel content = new JPanel(new GridBagLayout());
        content.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(5, 5, 5, 5);

        JLabel title = new JLabel("Gym Membership System");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        content.add(title, constraints);

        constraints.gridy++;
        content.add(new JLabel("Login Admin"), constraints);

        constraints.gridwidth = 1;
        constraints.gridy++;
        constraints.anchor = GridBagConstraints.WEST;
        content.add(new JLabel("Username"), constraints);
        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        content.add(username, constraints);

        constraints.gridx = 0;
        constraints.gridy++;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        content.add(new JLabel("Password"), constraints);
        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        content.add(password, constraints);

        constraints.gridy++;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.EAST;
        content.add(loginButton, constraints);
        return content;
    }

    private void login() {
        String username = tfUsername.getText();
        String password = new String(pfPassword.getPassword());

        if (username.isBlank() || password.isBlank()) {
            JOptionPane.showMessageDialog(this, "Username dan password wajib diisi!");
        } else if (authenticate(authController, username.trim(), password)) {
            JOptionPane.showMessageDialog(this, "Login berhasil!");
            new DashboardFrame().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Username atau password salah!");
            pfPassword.setText("");
            tfUsername.requestFocus();
        }
    }

    static boolean authenticate(AuthController authController, String username, String password) {
        return authController.login(username, password);
    }
}

