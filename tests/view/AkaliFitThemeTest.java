package view;

import org.junit.jupiter.api.Test;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AkaliFitThemeTest {
    @Test
    void appliesDarkSurfaceAndPurpleAccentRecursively() {
        JPanel panel = new JPanel();
        JButton button = new JButton("Aksi");
        panel.add(button);

        AkaliFitTheme.apply(panel);

        assertEquals(AkaliFitTheme.BACKGROUND, panel.getBackground());
        assertEquals(AkaliFitTheme.ACCENT, button.getBackground());
        assertEquals(AkaliFitTheme.TEXT, button.getForeground());
    }

    @Test
    void stylesLabelsWithReadableText() {
        JLabel label = new JLabel("Akali Fit");

        AkaliFitTheme.apply(label);

        assertEquals(AkaliFitTheme.TEXT, label.getForeground());
    }
}
