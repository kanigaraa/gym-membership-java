package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {
    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/gym_membership";
    private static final String DEFAULT_USER = "root";

    private DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {
        String url = environment("DB_URL", DEFAULT_URL);
        String user = environment("DB_USER", DEFAULT_USER);
        String password = environment("DB_PASSWORD", "");
        return DriverManager.getConnection(url, user, password);
    }

    private static String environment(String name, String fallback) {
        String value = System.getenv(name);
        return value == null || value.isBlank() ? fallback : value;
    }
}
