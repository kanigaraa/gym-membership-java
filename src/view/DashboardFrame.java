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
        setSize(680, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        memberButton.addActionListener(event -> {
            new MemberFrame().setVisible(true);
            dispose();
        });
        membershipButton.addActionListener(event -> {
            new MembershipFrame().setVisible(true);
            dispose();
        });
        logoutButton.addActionListener(event -> logout());
    }

    static JPanel createContentPanel(JButton memberButton, JButton membershipButton, JButton logoutButton) {
        JPanel content = new JPanel(new BorderLayout(16, 16));
        content.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));
        content.setBackground(Color.WHITE);

        JPanel heading = new JPanel();
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));
        heading.setBackground(Color.WHITE);
        JLabel title = new JLabel("Gym Membership System");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel subtitle = new JLabel("Dashboard Admin");
        subtitle.setForeground(new Color(90, 90, 90));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel welcome = new JLabel("Selamat datang, Admin");
        welcome.setForeground(new Color(70, 70, 70));
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        heading.add(title);
        heading.add(Box.createVerticalStrut(8));
        heading.add(subtitle);
        heading.add(Box.createVerticalStrut(4));
        heading.add(welcome);
        content.add(heading, BorderLayout.NORTH);

        JPanel actions = new JPanel(new GridLayout(1, 2, 16, 0));
        actions.setBackground(Color.WHITE);
        actions.setBorder(BorderFactory.createTitledBorder("Menu Utama"));
        styleMenuButton(memberButton);
        styleMenuButton(membershipButton);
        actions.add(memberButton);
        actions.add(membershipButton);
        content.add(actions, BorderLayout.CENTER);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setBackground(Color.WHITE);
        logoutButton.setPreferredSize(new Dimension(100, 32));
        footer.add(logoutButton);
        content.add(footer, BorderLayout.SOUTH);
        return content;
    }

    private static void styleMenuButton(JButton button) {
        button.setFont(button.getFont().deriveFont(Font.BOLD, 15f));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(220, 90));
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

