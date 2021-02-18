package soundtrack.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtConverter converter;

    public SecurityConfig(JwtConverter converter) {
        this.converter = converter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();;

        http.authorizeRequests()
                //If one of the below methods is giving you grief about logging in, just replace the .hasRole([role]) with a .permitAll()
                //Just make sure you put it back how it was when you're done :)
                .antMatchers("/login", "/registration").permitAll()
                .antMatchers("/**").permitAll() //comment this out unless you're testing controllers
                .antMatchers(HttpMethod.GET, "/api/location", "/api/location/*").hasAnyRole("USER", "ADMINISTRATOR")
                .antMatchers(HttpMethod.GET, "/api/item", "/api/item/*").hasAnyRole("USER", "ADMINISTRATOR")
                .antMatchers("/api/user/{userId}").access("@webSecurity.checkUserId(authentication, #userId)")
                .antMatchers(HttpMethod.GET, "/api/user", "/api/user/*").hasAnyRole("ADMINISTRATOR")
                .antMatchers(HttpMethod.POST).hasRole("ADMINISTRATOR")
                .antMatchers(HttpMethod.PUT).hasAnyRole("ADMINISTRATOR")
                .antMatchers(HttpMethod.DELETE).hasAnyRole("ADMINISTRATOR")
                //.antMatchers("/**").denyAll() //when testing controllers ONLY, comment this out
                .and()
                .addFilter(new JwtRequestFilter(authenticationManager(), converter)) // 3
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("*");
            }
        };
    }
}
