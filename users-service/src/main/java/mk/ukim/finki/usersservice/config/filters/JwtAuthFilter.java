package mk.ukim.finki.usersservice.config.filters;

import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import mk.ukim.finki.usersservice.config.JwtAuthConstants;
import mk.ukim.finki.usersservice.model.dto.RequestDetailsDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JwtAuthFilter extends BasicAuthenticationFilter {

    private final JWTVerifier verifier;

    public JwtAuthFilter(AuthenticationManager authenticationManager, JWTVerifier verifier) {
        super(authenticationManager);
        this.verifier = verifier;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwtToken = request.getHeader(JwtAuthConstants.HEADER_STRING)
                .replace(JwtAuthConstants.TOKEN_PREFIX, "");
        String subject = verifier.verify(jwtToken)
                .getSubject();
        RequestDetailsDto userDetails = new ObjectMapper()
                .readValue(subject, RequestDetailsDto.class);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                userDetails.getId(), "", Collections.singleton(userDetails.getRole())
        );

        SecurityContextHolder.getContext().setAuthentication(token);
        chain.doFilter(request, response);
    }
}
