package Repository.User;

import model.User;
import model.validation.Notification;

public interface UserRepository {

    Notification<User> findByUsernameAndPassword(String username, String password);
    boolean save(User user);
    boolean deleteById(Long id);
    boolean updateUser(User user);
    void removeAll();

}
