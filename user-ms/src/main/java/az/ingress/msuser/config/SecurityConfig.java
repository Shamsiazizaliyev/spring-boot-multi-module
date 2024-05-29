package az.ingress.msuser.config;


import az.ingress.config.JwtAuthenticationFilter;
import az.ingress.config.SpringConfig;
import az.ingress.repository.UserRepostory;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationProvider;

@Configuration
@Import({JwtAuthenticationFilter.class,AuthenticationProvider.class})


public class SecurityConfig extends SpringConfig {

    public SecurityConfig(AuthenticationProvider authenticationProvider, JwtAuthenticationFilter jwtAuthenticationFilter) {
        super(authenticationProvider, jwtAuthenticationFilter);
    }
}
