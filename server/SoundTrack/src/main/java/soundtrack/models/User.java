package soundtrack.models;

import javax.validation.constraints.*;
import java.util.Objects;

public class User {

    @Min(value = 1, message = "Id cannot be less than 1!")
    private int userId;

    @NotNull(message = "First name cannot be null!")
    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotNull(message = "Last name cannot be null!")
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @NotNull(message = "Email cannot be null!")
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be a valid email address")
    private String email;

    @Pattern(regexp = "\"\\\\(?\\\\d\\\\d\\\\d\\\\)?[ -]?\\\\d\\\\d\\\\d[- ]?\\\\d\\\\d\\\\d\\\\d\"", message = "Phone number must be a valid phone number!")
    private String phone;

    private AccessLevel accessLevel;

    @Size(min = 16, message = "Password must be at least 16 characters")
    private String password;

    public User() {
    }

    public User(@Min(value = 1, message = "Id cannot be less than 1!") int userId, @NotNull(message = "First name cannot be null!") @NotBlank(message = "First name cannot be blank") String firstName, @NotNull(message = "Last name cannot be null!") @NotBlank(message = "Last name cannot be blank") String lastName, @NotNull(message = "Email cannot be null!") @NotBlank(message = "Email cannot be blank") @Email(message = "Email must be a valid email address") String email, @Pattern(regexp = "\"\\\\(?\\\\d\\\\d\\\\d\\\\)?[ -]?\\\\d\\\\d\\\\d[- ]?\\\\d\\\\d\\\\d\\\\d\"", message = "Phone number must be a valid phone number!") String phone, AccessLevel accessLevel, @Size(min = 16, message = "Password must be at least 16 characters") String password) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.accessLevel = accessLevel;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(email, user.email) &&
                Objects.equals(phone, user.phone) &&
                accessLevel == user.accessLevel &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, firstName, lastName, email, phone, accessLevel, password);
    }
}
