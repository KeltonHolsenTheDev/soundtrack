package soundtrack.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    private final PasswordEncoder encoder;

    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(int id) {
        return repository.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("No user exists with that email!");
        }
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getAccessLevel().name()));
        //annoying name sharing going on here
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    public Result<User> add(User user) {
        Result<User> result = validate(user);
        if (!result.isSuccess()) {
            return result;
        }
        user.setPassword(encoder.encode(user.getPassword()));
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
        user.setPassword(encoder.encode(user.getPassword()));
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
