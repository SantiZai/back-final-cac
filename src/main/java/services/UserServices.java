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
    public User findUserByEmail(String email) {
        return persistence.findUserByEmail(email);
    }

    @Override
    public boolean login(String username, String password) {
        return persistence.login(username, password);
    }

}
