package controller;

import config.ConnectionFactory;
import dao.MemberDAO;
import model.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberControllerPersistenceTest {
    private MemberController controller;

    @BeforeEach
    void setUp() throws Exception {
        String url = "jdbc:h2:mem:" + UUID.randomUUID() + ";MODE=MySQL;DB_CLOSE_DELAY=-1";
        ConnectionFactory factory = () -> DriverManager.getConnection(url);
        try (Connection connection = factory.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE members (id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(100), phone VARCHAR(30), membership_type VARCHAR(20), " +
                    "registration_date DATE, expiry_date DATE)");
        }
        controller = new MemberController(new MemberDAO(factory));
    }

    @Test
    void managesMembersThroughController() throws Exception {
        Member budi = controller.addMember("Budi", "0812", "Regular",
                "2026-07-05", "2026-08-05");
        controller.addMember("Citra", "0813", "Premium",
                "2026-07-05", "2026-09-05");

        assertEquals(2, controller.getMemberCount());
        assertEquals(2, controller.getMembers().size());
        assertEquals("Budi", controller.searchMembers("0812").get(0).getName());

        controller.updateMember(budi.getId(), "Budi Baru", "0899", "Premium",
                "2026-07-05", "2026-10-05");
        assertEquals("Budi Baru", controller.searchMembers("Baru").get(0).getName());

        controller.deleteMember(budi.getId());
        assertEquals(1, controller.getMemberCount());
    }

    @Test
    void rejectsUpdateAndDeleteWithoutSelection() {
        assertThrows(ValidationException.class, () -> controller.updateMember(null,
                "Budi", "0812", "Regular", "2026-07-05", "2026-08-05"));
        assertThrows(ValidationException.class, () -> controller.deleteMember(null));
    }
}
