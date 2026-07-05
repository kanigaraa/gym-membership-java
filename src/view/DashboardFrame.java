package view;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    private final JButton memberButton = new JButton("Data Member");
    private final JButton membershipButton = new JButton("Membership");
    private final JButton logoutButton = new JButton("Logout");

    public DashboardFrame() {
        setTitle("Dashboard");
        setContentPane(createContentPanel(memberButton, membershipButton, logoutButton));
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        memberButton.addActionListener(event -> {
            new MemberFrame().setVisible(true);
            dispose();
        });
        membershipButton.addActionListener(event -> JOptionPane.showMessageDialog(
                this, "Halaman Membership akan dibuat oleh Luthfi."));
        logoutButton.addActionListener(event -> logout());
    }

    static JPanel createContentPanel(JButton memberButton, JButton membershipButton, JButton logoutButton) {
        JPanel content = new JPanel(new BorderLayout(16, 16));
        content.setBorder(BorderFactory.createEmptyBorder(32, 48, 32, 48));

        JPanel heading = new JPanel();
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Gym Membership System");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel subtitle = new JLabel("Dashboard Admin");
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel welcome = new JLabel("Selamat datang, Admin");
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        heading.add(title);
        heading.add(Box.createVerticalStrut(8));
        heading.add(subtitle);
        heading.add(Box.createVerticalStrut(4));
        heading.add(welcome);
        content.add(heading, BorderLayout.NORTH);

        JPanel actions = new JPanel(new GridLayout(1, 2, 16, 0));
        actions.add(memberButton);
        actions.add(membershipButton);
        content.add(actions, BorderLayout.CENTER);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.add(logoutButton);
        content.add(footer, BorderLayout.SOUTH);
        return content;
    }

    private void logout() {
        int choice = JOptionPane.showConfirmDialog(this, "Yakin ingin logout?", "Logout",
                JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            new LoginFrame().setVisible(true);
            dispose();
        }
    }
}

