package soundtrack.models;

import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//we might make this extend one of the Spring Security User classes if it becomes a problem
public class User {

    private int userId;

    @NotBlank(message = "First name cannot be null or blank!")
    private String firstName;

    @NotBlank(message = "Last name cannot be null or blank!")
    private String lastName;

    @NotBlank(message = "Email cannot be null or blank!")
    @Email(message = "Email must be a valid email address")
    private String email;

    @Pattern(regexp = "\\(?\\d\\d\\d\\)?[ -]?\\d\\d\\d[- ]?\\d\\d\\d\\d", message = "Phone number must be a valid phone number!")
    private String phone;

    @NotNull(message = "Access level cannot be null!")
    private AccessLevel accessLevel;

    private List<GrantedAuthority> authorities = new ArrayList<>();

    @NotBlank(message = "Password cannot be null or blank!")
    @Size(min = 16, message = "Password must be at least 16 characters")
    private String password;

    @NotEmpty(message = "User must have at least one role!")
    private List<String> roles;

    public User() {
    }

    public User(@NotNull(message = "First name cannot be null!") @NotBlank(message = "First name cannot be blank") String firstName, @NotNull(message = "Last name cannot be null!") @NotBlank(message = "Last name cannot be blank") String lastName, @NotNull(message = "Email cannot be null!") @NotBlank(message = "Email cannot be blank") @Email(message = "Email must be a valid email address") String email, @Pattern(regexp = "\\(?\\d\\d\\d\\)?[ -]?\\d\\d\\d[- ]?\\d\\d\\d\\d", message = "Phone number must be a valid phone number!") String phone, AccessLevel accessLevel, @Size(min = 16, message = "Password must be at least 16 characters") String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.accessLevel = accessLevel;
        this.password = password;
    }

    public User(@Min(value = 1, message = "Id cannot be less than 1!") int userId, @NotNull(message = "First name cannot be null!") @NotBlank(message = "First name cannot be blank") String firstName, @NotNull(message = "Last name cannot be null!") @NotBlank(message = "Last name cannot be blank") String lastName, @NotNull(message = "Email cannot be null!") @NotBlank(message = "Email cannot be blank") @Email(message = "Email must be a valid email address") String email, @Pattern(regexp = "\\(?\\d\\d\\d\\)?[ -]?\\d\\d\\d[- ]?\\d\\d\\d\\d", message = "Phone number must be a valid phone number!") String phone, AccessLevel accessLevel, @Size(min = 16, message = "Password must be at least 16 characters") String password) {
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
        if (password == null) {
            this.password = null;
        }
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void addAuthority(GrantedAuthority authority) {
        this.authorities.add(authority);
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
                accessLevel == user.accessLevel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, firstName, lastName, email, phone, accessLevel);
    }
}
