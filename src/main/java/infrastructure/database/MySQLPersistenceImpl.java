package infrastructure.database;

import domain.models.User;
import infrastructure.IPersistence;
import services.PasswordServices;

import java.sql.*;
import java.util.ArrayList;

public class MySQLPersistenceImpl implements IPersistence {

    private Connection connection;

    public MySQLPersistenceImpl() {
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public void saveUser(User user) {
        String sql = "INSERT INTO users (username, email, password, orders) VALUES (?,?,?,?)";
        try {
            PreparedStatement preparator = this.connection.prepareStatement(sql);
            preparator.setString(1, user.getUsername());
            preparator.setString(2, user.getEmail());
            preparator.setString(3, PasswordServices.hashPassword(user.getPassword()));
            preparator.setString(4, "");
            preparator.executeUpdate();
            preparator.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<User> findAllUsers() {
        String sql = "SELECT * FROM users";
        ArrayList<User> users = new ArrayList<>();
        try {
            PreparedStatement preparator = connection.prepareStatement(sql);
            ResultSet resultsTable = preparator.executeQuery();

            while (resultsTable.next()) {
                User user = new User(resultsTable.getInt("id"),
                        resultsTable.getString("username"),
                        resultsTable.getString("email"),
                        resultsTable.getString("password"),
                        resultsTable.getString("orders"));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try {
            PreparedStatement preparator = this.connection.prepareStatement(sql);
            preparator.setString(1, username);

            ResultSet results = preparator.executeQuery();
            if(results.next()) {
                User existingUser = new User(results.getInt("id"),
                        results.getString("username"),
                        results.getString("email"),
                        results.getString("password"),
                        results.getString("orders"));
                return existingUser;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
