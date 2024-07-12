package services;

import domain.models.User;
import infrastructure.IPersistence;
import infrastructure.database.MySQLPersistenceImpl;

import java.util.ArrayList;

public class UserServices implements IPersistence {

    private IPersistence persistence = new MySQLPersistenceImpl();

    @Override
    public void saveUser(User user) {
        persistence.saveUser(user);
    }

    @Override
    public ArrayList<User> findAllUsers() {
        return persistence.findAllUsers();
    }

    @Override
    public User findUserByUsername(String username) {
        return persistence.findUserByUsername(username);
    }

}
