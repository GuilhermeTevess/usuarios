package com.cadastro.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() {
        String sql = "CREATE TABLE IF NOT EXISTS users ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "name VARCHAR(100) NOT NULL,"
                + "email VARCHAR(100) NOT NULL,"
                + "age INT"
                + ");";

        try (Connection conn = DatabaseConfig.getConnection(); Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Table 'users' is ready!");

        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }
}
