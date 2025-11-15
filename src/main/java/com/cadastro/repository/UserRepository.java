package com.cadastro.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cadastro.config.DatabaseConfig;
import com.cadastro.model.User;

public class UserRepository {

    public void save(User user) {
        String sql = "INSERT INTO users (name, email, age) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setInt(3, user.getAge());

            stmt.executeUpdate();
            System.out.println("User saved successfully!");

        } catch (SQLException e) {
            System.out.println("Error saving user: " + e.getMessage());
        }
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                u.setAge(rs.getInt("age"));
                users.add(u);
            }

        } catch (SQLException e) {
            System.out.println("Error listing users: " + e.getMessage());
        }

        return users;
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("User deleted successfully!");
            } else {
                System.out.println("No user found with this ID.");
            }

        } catch (SQLException e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
    }

    public void updateUser(User user) {
        String sql = "UPDATE users SET name = ?, email = ?, age = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setInt(3, user.getAge());
            stmt.setInt(4, user.getId());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("User updated successfully!");
            } else {
                System.out.println("No user found with this ID.");
            }

        } catch (SQLException e) {
            System.out.println("Error updating user: " + e.getMessage());
        }
    }

}
