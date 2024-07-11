package infrastructure;

import domain.models.User;

public interface IPersistence {

    void saveUser(User user);

}
