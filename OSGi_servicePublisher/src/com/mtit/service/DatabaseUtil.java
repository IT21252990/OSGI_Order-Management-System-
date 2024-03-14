package com.mtit.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseUtil {
    private static Connection connection;

    public static void initializeDatabase() {
        try {
            Properties properties = new Properties();
            try (InputStream input = DatabaseUtil.class.getClassLoader().getResourceAsStream("com/mtit/service/db.properties")) {
                properties.load(input);
            }

            Class.forName("com.mysql.cj.jdbc.Driver");
            String jdbcUrl = properties.getProperty("jdbc.url");
            String username = properties.getProperty("jdbc.username");
            String password = properties.getProperty("jdbc.password");
            connection = DriverManager.getConnection(jdbcUrl, username, password);
        } catch (ClassNotFoundException | SQLException | IOException e) {
            System.out.println("Error initializing database: " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing database connection: " + e.getMessage());
        }
    }
}
