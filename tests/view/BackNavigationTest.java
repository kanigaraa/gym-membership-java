package view;

import org.junit.jupiter.api.Test;

import javax.swing.JButton;
import javax.swing.JLabel;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BackNavigationTest {
    @Test
    void memberHeaderContainsBackButton() {
        JButton backButton = new JButton("Kembali");

        var header = MemberFrame.createHeader(new JLabel("Member"), backButton);

        assertEquals(backButton, header.getComponent(1));
    }

    @Test
    void membershipHeaderContainsBackButton() {
        JButton backButton = new JButton("Kembali");

        var header = MembershipFrame.createHeader(new JLabel("Membership"), backButton);

        assertEquals(backButton, header.getComponent(1));
    }
}
