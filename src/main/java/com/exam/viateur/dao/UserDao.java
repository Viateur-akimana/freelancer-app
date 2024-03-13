package com.exam.viateur.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.exam.viateur.model.User;

public class UserDao {
    private static final String url = "jdbc:mysql://localhost:3306/exam";
    private static final String username = "root";
    private static final String password = "";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public void addUser(User user) {
        String insert_user = "INSERT INTO exam (name, email, country) VALUES (?, ?, ?)";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(insert_user);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getCountry());
            statement.executeUpdate();
            System.out.println("User added successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User selectUser(int id) {
        User user = null;
        String SELECT_USER_BY_ID = "SELECT * FROM exam WHERE id= ?";
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                user = new User(id, name, email, country);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public List<User> selectAllUsers() {
        List<User> users = new ArrayList<>();
        String SELECT_ALL_USERS = "SELECT * FROM exam";
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);) {
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                users.add(new User(id, name, email, country));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void updateUser(User user) {
        String update_user = "UPDATE exam SET name = ?, email = ?, country = ? WHERE id = ?";
        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(update_user);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getCountry());
            statement.setInt(4, user.getId());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User updated successfully");
            } else {
                System.out.println("No user found with the provided ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteUser(int id) {
        boolean rowDeleted = false;
        String DELETE_USERS_SQL = "DELETE FROM exam WHERE id = ?";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }
}
