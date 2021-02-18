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
    protected void doFilterInternal(HttpServletRequest request, //this has a null UserPrincipal for some reason
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {


        String authorization = request.getHeader("Authorization"); //this is null but an exception even if that's ignored
        if (authorization != null && authorization.startsWith("Bearer ")) {
            User user = converter.getUserFromToken(authorization); //also ends up being null because authorization is null, so maybe don't do that
            if (user == null) {
                response.setStatus(403); // Forbidden
            } else {

                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        user.getEmail(), null, List.of(new SimpleGrantedAuthority(user.getAccessLevel().name())));

                SecurityContextHolder.getContext().setAuthentication(token);
            }
        }

        chain.doFilter(request, response); //This is where the exception gets thrown
    }
}
