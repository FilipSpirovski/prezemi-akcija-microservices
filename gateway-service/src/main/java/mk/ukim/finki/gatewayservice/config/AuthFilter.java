package mk.ukim.finki.gatewayservice.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    private final JWTVerifier verifier;

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (!exchange.getRequest()
                    .getHeaders()
                    .containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new RuntimeException("Missing authorization information.");
            }

            String authHeader = Objects.requireNonNull(exchange.getRequest()
                    .getHeaders()
                    .get(HttpHeaders.AUTHORIZATION))
                    .get(0);
            String[] parts = authHeader.split(" ");
            if (parts.length != 2 || !"Bearer".equals(parts[0])) {
                throw new RuntimeException("Incorrect authorization structure.");
            }

            try {
                String token = parts[1];
                verifier.verify(token);
            } catch (JWTVerificationException e) {
                throw new RuntimeException("Invalid JWT.");
            }

            return chain.filter(exchange);
        };
    }

    public static class Config {
        // empty class as I don't need any particular configuration
    }
}
