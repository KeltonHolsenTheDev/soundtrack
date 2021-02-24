package soundtrack.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import soundtrack.models.AccessLevel;
import soundtrack.models.User;
import org.springframework.stereotype.Component;
import soundtrack.domain.UserService;

@Component
public class WebSecurity {

    private final UserService service;

    public WebSecurity(UserService service) {
        this.service = service;
    }

    public boolean checkUserId(Authentication authentication, int userId) {
        String email = (String) authentication.getPrincipal();
        System.out.println(email);
        User user = service.findByEmail(email);
        if (user == null) {
            return false;
        }
        if (user.getAccessLevel() == AccessLevel.ROLE_ADMINISTRATOR) {
            return true;
        }
        return user.getUserId() == userId;
    }

}
