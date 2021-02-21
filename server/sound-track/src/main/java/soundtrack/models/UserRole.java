package soundtrack.models;

import java.util.ArrayList;
import java.util.List;

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
}
