package soundtrack.data;

import soundtrack.models.User;

import java.util.List;

public interface UserRepository {
    List<User> findAll();
    List<String> findUserEventRoles(int userId, int eventId);
    User findById(int id);
    User addUser(User user);
    boolean update(User user);
    boolean deleteById(int userId);

    User findByEmail(String email);
}
