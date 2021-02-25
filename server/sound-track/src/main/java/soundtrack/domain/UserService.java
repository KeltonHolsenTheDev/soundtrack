package soundtrack.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import soundtrack.data.EventRepository;
import soundtrack.data.UserRepository;
import soundtrack.models.AccessLevel;
import soundtrack.models.Location;
import soundtrack.models.User;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Properties;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    private final EventRepository eventRepository;

    private final PasswordEncoder encoder;

    public UserService(UserRepository repository, EventRepository eventRepository, PasswordEncoder encoder) {
        this.repository = repository;
        this.eventRepository = eventRepository;
        this.encoder = encoder;
    }

    public List<User> findAll() {
        List<User> all = repository.findAll();
        all.forEach(this::nullPassword);
        return all;
    }

    public User findById(int id) {
        User user = repository.findById(id);
        nullPassword(user);
        return user;
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

    public User findByEmail(String email) {
        User user = repository.findByEmail(email);
        if (user != null) {
            nullPassword(user);
        }
        return user;
    }

    public Result<User> add(User user) {
        Result<User> result = validate(user);
        if (user.getPassword().equals("@@@@@@@@@@@@@@@@")) {
            result.addMessage("That password is not allowed", ResultType.INVALID);
        }
        if (!result.isSuccess()) {
            return result;
        }
        user.setPassword(encoder.encode(user.getPassword()));
        User out = repository.addUser(user);
        if (out == null) {
            result.addMessage("Could not add user due to repository error", ResultType.NOT_FOUND);
        }
        else {
            contactNewUser(user);
            result.setPayLoad(out);
        }
        return result;
    }

    public Result<User> update(User user) {
        boolean theyUsedTheBad = false;
        if (user.getPassword().equals("@@@@@@@@@@@@@@@@")) { //special password to signify not changing password
            theyUsedTheBad = true;
        }
        if (user.getPassword().isBlank()) {
            user.setPassword("@@@@@@@@@@@@@@@@");
        }
        Result<User> result = validate(user);
        if (theyUsedTheBad) {
            result.addMessage("That password is not allowed", ResultType.INVALID);
        }
        if (!result.isSuccess()) {
            return result;
        }
        //make it so that users cannot be updated to admin status under any circumstances
        User oldValues = repository.findById(user.getUserId());
        if (oldValues.getAccessLevel() == AccessLevel.ROLE_USER && user.getAccessLevel() == AccessLevel.ROLE_ADMINISTRATOR) {
            result.addMessage("User access level cannot be escalated via update. Create a new account to give this user administrator access.",
                    ResultType.INVALID);
            return result;
        }
        if (user.getPassword().equals("@@@@@@@@@@@@@@@@")) {
            user.setPassword(repository.findById(user.getUserId()).getPassword());
        }
        else {
            alertUserOfPasswordChange(user);
            user.setPassword(encoder.encode(user.getPassword()));
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
        eventRepository.deleteByOwner(userId);
        return repository.deleteById(userId);
    }

    private void nullPassword(User user) {
        user.setPassword(null);
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

    private void contactNewUser(User user) {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", "smtp.gmail.com");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.port", "587");
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("soundtrackbyteamjak@gmail.com", "I pledge allegiance 2 Artemis.");
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("soundtrackbyteamjak@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            message.setSubject("Account Creation Notification");
            String text = "This is a notification that a user account has been created for you at SoundTrack using this email address as the username. Please ask the " +
                    "administrator for your login password.";
            message.setText(text);
            Transport.send(message);
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void alertUserOfPasswordChange(User user) {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", "smtp.gmail.com");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.port", "587");
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("soundtrackbyteamjak@gmail.com", "I pledge allegiance 2 Artemis.");
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("soundtrackbyteamjak@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            message.setSubject("Password Update Notification");
            String text = "This is a notification that your password has been updated. If you did not request this, please contact an administrator.";
            message.setText(text);
            Transport.send(message);
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
