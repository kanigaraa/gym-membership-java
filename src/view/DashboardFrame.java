package view;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    private final JButton memberButton = new JButton("Data Member");
    private final JButton membershipButton = new JButton("Membership");
    private final JButton logoutButton = new JButton("Logout");

    public DashboardFrame() {
        setTitle("Akali Fit - Dashboard");
        JPanel content = createContentPanel(memberButton, membershipButton, logoutButton);
        AkaliFitTheme.apply(content);
        setContentPane(content);
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
        JLabel title = new JLabel("AKALI FIT");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel subtitle = new JLabel("Dashboard Admin");
        subtitle.setForeground(AkaliFitTheme.MUTED_TEXT);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel welcome = new JLabel("Selamat datang, Admin");
        welcome.setForeground(AkaliFitTheme.MUTED_TEXT);
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

