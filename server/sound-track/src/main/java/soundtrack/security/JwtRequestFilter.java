package soundtrack.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import soundtrack.models.User;

public class JwtRequestFilter extends BasicAuthenticationFilter {

    private final JwtConverter converter;

    public JwtRequestFilter(AuthenticationManager authenticationManager, JwtConverter converter) {
        super(authenticationManager);
        this.converter = converter;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String authorization = request.getHeader("access");
        if (authorization != null && authorization.startsWith("Bearer ")) {

            // 3. The value looks okay, confirm it with JwtConverter.
            User user = converter.getUserFromToken(authorization);
            if (user == null) {
                response.setStatus(403); // Forbidden
            } else {

                // 4. Confirmed. Set auth for this single request.
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        user.getEmail(), null, List.of(new SimpleGrantedAuthority(user.getAccessLevel().name())));

                SecurityContextHolder.getContext().setAuthentication(token);
            }
        }

        // 5. Keep the chain going.
        chain.doFilter(request, response);
    }
}
