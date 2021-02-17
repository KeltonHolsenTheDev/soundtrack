package soundtrack.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import soundtrack.domain.UserService;
import soundtrack.models.AccessLevel;
import soundtrack.models.User;

import java.security.Key;
import java.util.*;

@Component
public class JwtConverter {
    // 1. Signing key
    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    // 2. "Configurable" constants
    private final String ISSUER = "sound-track";
    private final int EXPIRATION_MINUTES = 15;
    private final int EXPIRATION_MILLIS = EXPIRATION_MINUTES * 60 * 1000;
    private final UserService service;

    public JwtConverter(UserService service) {
        this.service = service;
    }

    public String getTokenFromUser(User user) {

        String access = user.getAccessLevel().name();
        Map<String, Object> roleMap = new HashMap<>();
        for (int i = 0; i < user.getRoles().size(); i++) {
            roleMap.put("" + i, user.getRoles().get(i));
        }

        return Jwts.builder()
                .setIssuer(ISSUER)
                .setSubject(user.getEmail()) //using email for username
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .claim("phone", user.getPhone())
                .claim("access", access)
                .claim("password", user.getPassword())
                .addClaims(roleMap)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MILLIS))
                .signWith(key)
                .compact();
    }

    public User getUserFromToken(String token) {

        if (token == null || !token.startsWith("Bearer ")) {
            return null;
        }

        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                    .requireIssuer(ISSUER)
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token.substring(7));

            String email = jws.getBody().getSubject();
            String authStr = (String) jws.getBody().get("access");
            GrantedAuthority authority = new SimpleGrantedAuthority(authStr);
            String firstName = (String) jws.getBody().get("firstName");
            String lastName = (String) jws.getBody().get("lastName");
            String phone = (String) jws.getBody().get("phone");
            String password = (String) jws.getBody().get("password"); //this may be a bad practice and need changing idk
            HashMap<String, String> hashRoles = (HashMap<String, String>)jws.getBody().get("roles");
            List<String> roles = new ArrayList<>();
            for (Map.Entry<String, String> e: hashRoles.entrySet()) {
                roles.add(e.getValue());
            }
            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPhone(phone);
            user.setRoles(roles);
            user.setPassword(password);
            user.setAccessLevel(AccessLevel.valueOf(authStr));
            return user;

        } catch (JwtException e) {
            System.out.println(e);
        }

        return null;
    }
}
