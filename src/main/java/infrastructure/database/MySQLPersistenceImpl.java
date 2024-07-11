package infrastructure.database;

import domain.models.User;
import infrastructure.IPersistence;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
