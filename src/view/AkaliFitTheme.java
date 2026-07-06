package view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;

final class AkaliFitTheme {
    static final Color BACKGROUND = new Color(15, 15, 18);
    static final Color SURFACE = new Color(27, 27, 33);
    static final Color FIELD = new Color(38, 38, 46);
    static final Color ACCENT = new Color(124, 58, 237);
    static final Color ACCENT_HOVER = new Color(139, 76, 246);
    static final Color TEXT = new Color(245, 243, 255);
    static final Color MUTED_TEXT = new Color(190, 185, 204);
    static final Color BORDER = new Color(69, 60, 84);

    private AkaliFitTheme() {
    }

    static void apply(Component component) {
        component.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));

        if (component instanceof JPanel panel) {
            panel.setBackground(BACKGROUND);
            if (panel.getBorder() instanceof TitledBorder titledBorder) {
                titledBorder.setTitleColor(MUTED_TEXT);
                titledBorder.setBorder(BorderFactory.createLineBorder(BORDER));
            }
        } else if (component instanceof JLabel label) {
            label.setForeground(TEXT);
        } else if (component instanceof AbstractButton button) {
            button.setBackground(ACCENT);
            button.setForeground(TEXT);
            button.setFocusPainted(false);
            button.setOpaque(true);
            button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(ACCENT_HOVER),
                    BorderFactory.createEmptyBorder(7, 14, 7, 14)));
        } else if (component instanceof JTextComponent textComponent) {
            textComponent.setBackground(FIELD);
            textComponent.setForeground(TEXT);
            textComponent.setCaretColor(TEXT);
            textComponent.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER),
                    BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        } else if (component instanceof JComboBox<?> comboBox) {
            comboBox.setBackground(FIELD);
            comboBox.setForeground(TEXT);
        } else if (component instanceof JTable table) {
            table.setBackground(SURFACE);
            table.setForeground(TEXT);
            table.setGridColor(BORDER);
            table.setSelectionBackground(ACCENT);
            table.setSelectionForeground(TEXT);
            table.getTableHeader().setBackground(FIELD);
            table.getTableHeader().setForeground(TEXT);
        } else if (component instanceof JScrollPane scrollPane) {
            scrollPane.getViewport().setBackground(SURFACE);
            scrollPane.setBorder(BorderFactory.createLineBorder(BORDER));
        }

        if (component instanceof Container container) {
            for (Component child : container.getComponents()) {
                apply(child);
            }
        }
    }
}
