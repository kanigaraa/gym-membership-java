package view;

import org.junit.jupiter.api.Test;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginFrameTest {
    @Test
    void createsLoginContentWithoutIntellijFormInstrumentation() {
        JTextField username = new JTextField();
        JPasswordField password = new JPasswordField();
        JButton login = new JButton("Login");

        var panel = LoginFrame.createContentPanel(username, password, login);

        assertNotNull(panel);
        assertTrue(panel.getComponentCount() > 0);
    }
}
