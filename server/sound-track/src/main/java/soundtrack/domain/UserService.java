package soundtrack.domain;

import org.springframework.stereotype.Service;
import soundtrack.data.UserRepository;
import soundtrack.models.Location;
import soundtrack.models.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(int id) {
        return repository.findById(id);
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Result<User> add(User user) {
        Result<User> result = validate(user);
        if (!result.isSuccess()) {
            return result;
        }
        User out = repository.addUser(user);
        if (out == null) {
            result.addMessage("Could not add user due to repository error", ResultType.NOT_FOUND);
        }
        else {
            result.setPayLoad(out);
        }
        return result;
    }

    public Result<User> update(User user) {
        Result<User> result = validate(user);
        if (!result.isSuccess()) {
            return result;
        }
        if (repository.update(user)) {
            result.setPayLoad(user);
        }
        else {
            result.addMessage("User not found", ResultType.NOT_FOUND);
        }
        return result;
    }

    public boolean deleteById(int userId) {
        return repository.deleteById(userId);
    }

    private Result<User> validate(User user) {
        Result<User> result = new Result<>();
        if (user == null) {
            result.addMessage("User cannot be null!", ResultType.NOT_FOUND);
            return result;
        }
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<User> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
            return result;
        }

        List<User> all = repository.findAll();
        if (all.stream().anyMatch(u -> u.getEmail().equals(user.getEmail()) && u.getUserId() != user.getUserId())) {
            result.addMessage("User email already exists in the system!", ResultType.INVALID);
        }
        return result;
    }
}
