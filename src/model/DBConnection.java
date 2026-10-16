package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

/**
 * Provides the shared database connection used by the application.
 */
public class DBConnection {
    private static final String DEFAULT_URL = "jdbc:postgresql://localhost:5432/hospital_manager";

    private DBConnection() {
    }

    public static Connection getConnection() throws SQLException {
        String url = System.getenv().getOrDefault("DB_URL", DEFAULT_URL);
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        if (user == null || user.isBlank() || password == null) {
            throw new SQLException("Set the DB_USER and DB_PASSWORD environment variables.");
        }

        return DriverManager.getConnection(url, user, password);
    }
}