package services;

import domain.models.User;
import infrastructure.IPersistence;
import infrastructure.database.MySQLPersistenceImpl;

public class UserServices implements IPersistence {

    private IPersistence persistence = new MySQLPersistenceImpl();

    @Override
    public void saveUser(User user) {
        User newUser = new User(user.getId(),
                user.getUsername(),
                user.getEmail(),
                PasswordServices.hashPassword(user.getPassword()),
                user.getOrders());
        persistence.saveUser(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return persistence.findUserByUsername(username);
    }

}
