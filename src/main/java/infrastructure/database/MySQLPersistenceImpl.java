package infrastructure.database;

import domain.models.User;
import infrastructure.IPersistence;

import java.sql.*;

public class MySQLPersistenceImpl implements IPersistence {

    private Connection connection;

    public MySQLPersistenceImpl() {
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public void saveUser(User user) {
        String sql = "INSERT INTO users (id, username, email, password, orders) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement preparator = this.connection.prepareStatement(sql);
            preparator.setInt(1, user.getId());
            preparator.setString(2, user.getUsername());
            preparator.setString(3, user.getEmail());
            preparator.setString(4, user.getPassword());
            preparator.setString(5, user.getOrders());
            preparator.executeUpdate();
            preparator.close();
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
