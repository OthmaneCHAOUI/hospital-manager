package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

/**
 * Provides the shared database connection used by the application.
 */
public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/cabinet_medical";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private DBConnection() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}