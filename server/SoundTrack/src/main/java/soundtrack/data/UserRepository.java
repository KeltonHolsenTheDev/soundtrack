package soundtrack.data;

import soundtrack.models.User;

import java.util.List;

public interface UserRepository {
    List<User> findAll();
    User findById(int id);
    User addUser(User user);
    boolean update(User user);
    boolean deleteById(int userId);
}
