package infrastructure;

import domain.models.User;

import java.util.ArrayList;

public interface IPersistence {

    void saveUser(User user);

    ArrayList<User> findAllUsers();

    User findUserByEmail(String email);

    boolean login(String username, String password);

}
