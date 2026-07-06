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
        setTitle("Akali Fit - Login");
        JPanel content = createContentPanel(tfUsername, pfPassword, btnLogin);
        AkaliFitTheme.apply(content);
        setContentPane(content);
        setSize(460, 340);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getRootPane().setDefaultButton(btnLogin);

        btnLogin.addActionListener(e -> login());
    }

    static JPanel createContentPanel(JTextField username, JPasswordField password, JButton loginButton) {
        JPanel content = new JPanel(new BorderLayout(16, 16));
        content.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));
        content.setBackground(Color.WHITE);

        JPanel heading = new JPanel();
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));
        heading.setBackground(Color.WHITE);

        JLabel title = new JLabel("AKALI FIT");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Login Admin");
        subtitle.setForeground(AkaliFitTheme.MUTED_TEXT);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        heading.add(title);
        heading.add(Box.createVerticalStrut(6));
        heading.add(subtitle);
        content.add(heading, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createTitledBorder("Masuk ke Sistem"));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(6, 10, 6, 10);
        form.add(new JLabel("Username"), constraints);

        constraints.gridx = 1;
        constraints.weightx = 1;
        username.setPreferredSize(new Dimension(210, 30));
        form.add(username, constraints);

        constraints.gridx = 0;
        constraints.gridy++;
        constraints.weightx = 0;
        form.add(new JLabel("Password"), constraints);

        constraints.gridx = 1;
        constraints.weightx = 1;
        password.setPreferredSize(new Dimension(210, 30));
        form.add(password, constraints);

        constraints.gridy++;
        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.fill = GridBagConstraints.NONE;
        loginButton.setPreferredSize(new Dimension(120, 32));
        form.add(loginButton, constraints);

        content.add(form, BorderLayout.CENTER);
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

