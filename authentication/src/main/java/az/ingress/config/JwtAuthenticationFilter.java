package az.ingress.config;

import az.ingress.JwtService;
import az.ingress.repository.UserRepostory;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@RequiredArgsConstructor
@Component
@Slf4j
@Import({JwtService.class})
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("begin doFilter");

        final String header = request.getHeader("Authorization");
        final String jwt;
        final String username;
        if (header == null || !header.startsWith("Bearer ")) {
            log.info("end doFilter");
           // response.setStatus(12);
            System.out.println(response.getStatus());

            filterChain.doFilter(request, response);

            return;
        }
        jwt = header.substring(7);
        username = jwtService.findUsername(jwt);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            log.info("begin security context");
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtService.tokenControl(jwt, userDetails)) {
                log.info("begin token control");
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                log.info("end token control");
                System.out.println(response.getStatus());
            }
        }
             response.setStatus(234);
        filterChain.doFilter(request, response);
    }
}
