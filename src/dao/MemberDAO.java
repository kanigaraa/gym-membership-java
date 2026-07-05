package dao;

import config.ConnectionFactory;
import config.DatabaseConnection;
import model.Member;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
    private static final String COLUMNS =
            "id, name, phone, membership_type, registration_date, expiry_date";

    private final ConnectionFactory connectionFactory;

    public MemberDAO() {
        this(DatabaseConnection::getConnection);
    }

    public MemberDAO(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public List<Member> findAll() throws SQLException {
        String sql = "SELECT " + COLUMNS + " FROM members ORDER BY id DESC";
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet results = statement.executeQuery()) {
            return mapAll(results);
        }
    }

    public void insert(Member member) throws SQLException {
        String sql = "INSERT INTO members " +
                "(name, phone, membership_type, registration_date, expiry_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            bindMember(statement, member);
            if (statement.executeUpdate() != 1) {
                throw new SQLException("Member gagal ditambahkan.");
            }
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (!keys.next()) {
                    throw new SQLException("ID member baru tidak tersedia.");
                }
                member.setId(keys.getInt(1));
            }
        }
    }

    public boolean update(Member member) throws SQLException {
        String sql = "UPDATE members SET name = ?, phone = ?, membership_type = ?, " +
                "registration_date = ?, expiry_date = ? WHERE id = ?";
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            bindMember(statement, member);
            statement.setInt(6, member.getId());
            return statement.executeUpdate() == 1;
        }
    }

    public boolean delete(int id) throws SQLException {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM members WHERE id = ?")) {
            statement.setInt(1, id);
            return statement.executeUpdate() == 1;
        }
    }

    public List<Member> search(String query) throws SQLException {
        if (query == null || query.isBlank()) {
            return findAll();
        }
        String sql = "SELECT " + COLUMNS + " FROM members " +
                "WHERE LOWER(name) LIKE ? OR LOWER(phone) LIKE ? ORDER BY id DESC";
        String pattern = "%" + query.trim().toLowerCase() + "%";
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, pattern);
            statement.setString(2, pattern);
            try (ResultSet results = statement.executeQuery()) {
                return mapAll(results);
            }
        }
    }

    public int count() throws SQLException {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM members");
             ResultSet results = statement.executeQuery()) {
            results.next();
            return results.getInt(1);
        }
    }

    private void bindMember(PreparedStatement statement, Member member) throws SQLException {
        statement.setString(1, member.getName());
        statement.setString(2, member.getPhone());
        statement.setString(3, member.getMembershipType());
        statement.setDate(4, Date.valueOf(member.getRegistrationDate()));
        statement.setDate(5, Date.valueOf(member.getExpiryDate()));
    }

    private List<Member> mapAll(ResultSet results) throws SQLException {
        List<Member> members = new ArrayList<>();
        while (results.next()) {
            members.add(new Member(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getString("phone"),
                    results.getString("membership_type"),
                    results.getDate("registration_date").toLocalDate(),
                    results.getDate("expiry_date").toLocalDate()));
        }
        return members;
    }
}
