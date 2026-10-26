package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides the shared database connection used by the application.
 */
public class DBConnection {
    private static final String DEFAULT_URL = "jdbc:postgresql://localhost:5432/hospital_manager";
    private static final Path CONFIG_FILE = Path.of("config.env");

    private DBConnection() {
    }

    public static Connection getConnection() throws SQLException {
        Map<String, String> config = loadConfig();
        String url = getSetting("DB_URL", config, DEFAULT_URL);
        String user = getSetting("DB_USER", config, null);
        String password = getSetting("DB_PASSWORD", config, null);

        if (user == null || user.isBlank() || password == null) {
            throw new SQLException("Set DB_USER and DB_PASSWORD in config.env or the environment.");
        }

        return DriverManager.getConnection(url, user, password);
    }

    private static Map<String, String> loadConfig() throws SQLException {
        Map<String, String> config = new HashMap<>();

        if (!Files.exists(CONFIG_FILE)) {
            return config;
        }

        try {
            for (String line : Files.readAllLines(CONFIG_FILE)) {
                String trimmedLine = line.trim();
                if (trimmedLine.isEmpty() || trimmedLine.startsWith("#")) {
                    continue;
                }

                int separator = trimmedLine.indexOf('=');
                if (separator <= 0) {
                    throw new SQLException("Invalid config.env entry. Expected KEY=VALUE.");
                }

                String key = trimmedLine.substring(0, separator).trim();
                String value = trimmedLine.substring(separator + 1).trim();
                if ((value.startsWith("\"") && value.endsWith("\""))
                        || (value.startsWith("'") && value.endsWith("'"))) {
                    value = value.substring(1, value.length() - 1);
                }
                config.put(key, value);
            }
        } catch (IOException exception) {
            throw new SQLException("Could not read config.env.", exception);
        }

        return config;
    }

    private static String getSetting(String name, Map<String, String> config, String defaultValue) {
        String environmentValue = System.getenv(name);
        if (environmentValue != null) {
            return environmentValue;
        }
        return config.getOrDefault(name, defaultValue);
    }
}