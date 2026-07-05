package dao;

import config.ConnectionFactory;
import model.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MemberDAOTest {
    private MemberDAO dao;

    @BeforeEach
    void setUp() throws Exception {
        String url = "jdbc:h2:mem:" + UUID.randomUUID() + ";MODE=MySQL;DB_CLOSE_DELAY=-1";
        ConnectionFactory factory = () -> DriverManager.getConnection(url);
        try (Connection connection = factory.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("""
                    CREATE TABLE members (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        phone VARCHAR(30) NOT NULL,
                        membership_type VARCHAR(20) NOT NULL,
                        registration_date DATE NOT NULL,
                        expiry_date DATE NOT NULL
                    )
                    """);
        }
        dao = new MemberDAO(factory);
    }

    @Test
    void insertsMemberAndAssignsGeneratedId() throws Exception {
        Member member = member("Budi", "0812", "Regular");

        dao.insert(member);

        assertNotNull(member.getId());
        assertEquals("Budi", dao.findAll().get(0).getName());
    }

    @Test
    void listsMembersOrderedByNewestId() throws Exception {
        dao.insert(member("Budi", "0812", "Regular"));
        dao.insert(member("Citra", "0813", "Premium"));

        List<Member> members = dao.findAll();

        assertEquals(List.of("Citra", "Budi"), members.stream().map(Member::getName).toList());
    }

    @Test
    void updatesExistingMember() throws Exception {
        Member original = member("Budi", "0812", "Regular");
        dao.insert(original);
        Member changed = new Member(original.getId(), "Budi Baru", "0899", "Premium",
                LocalDate.of(2026, 7, 5), LocalDate.of(2026, 9, 5));

        assertTrue(dao.update(changed));
        assertEquals("Budi Baru", dao.findAll().get(0).getName());
        assertEquals("Premium", dao.findAll().get(0).getMembershipType());
    }

    @Test
    void deletesExistingMember() throws Exception {
        Member member = member("Budi", "0812", "Regular");
        dao.insert(member);

        assertTrue(dao.delete(member.getId()));
        assertTrue(dao.findAll().isEmpty());
    }

    @Test
    void searchesCaseInsensitivePartialNameOrPhone() throws Exception {
        dao.insert(member("Budi Santoso", "0812-999", "Regular"));
        dao.insert(member("Citra", "0777", "Premium"));

        assertEquals("Budi Santoso", dao.search("bUdI").get(0).getName());
        assertEquals("Budi Santoso", dao.search("999").get(0).getName());
        assertEquals(2, dao.search("").size());
    }

    @Test
    void countsMembers() throws Exception {
        assertEquals(0, dao.count());
        dao.insert(member("Budi", "0812", "Regular"));
        dao.insert(member("Citra", "0813", "Premium"));
        assertEquals(2, dao.count());
    }

    private Member member(String name, String phone, String type) {
        return new Member(null, name, phone, type,
                LocalDate.of(2026, 7, 5), LocalDate.of(2026, 8, 5));
    }
}
