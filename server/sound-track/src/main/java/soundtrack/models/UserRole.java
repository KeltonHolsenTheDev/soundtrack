package soundtrack.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserRole {
    private User user;
    private List<String> roles = new ArrayList<>();

    public UserRole(User user, List<String> roles) {
        this.user = user;
        this.roles = roles;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return Objects.equals(user, userRole.user) &&
                Objects.equals(roles, userRole.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, roles);
    }
}
