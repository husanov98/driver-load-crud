package uz.mh.driver_load_crud.config;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import uz.mh.driver_load_crud.service.DriverService;
import uz.mh.driver_load_crud.service.UserService;

@Component
public class JWTAuthenticationManager implements ReactiveAuthenticationManager {
    private final JWTUtil jwtUtil;
    private final UserService driverService;

    public JWTAuthenticationManager(JWTUtil jwtUtil, UserService driverService) {
        this.jwtUtil = jwtUtil;
        this.driverService = driverService;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getCredentials().toString();
        System.out.println(token);
        String username = jwtUtil.extractUsername(token);

        return driverService.findByUsername(username)
                .map(driverDetails -> {
                    if (jwtUtil.validateToken(token, driverDetails.getEmail())) {
                        return authentication;
                    } else {
                        throw new AuthenticationException("Invalid JWT token") {};
                    }
                });
    }

    public ServerAuthenticationConverter authenticationConverter() {
        return new ServerAuthenticationConverter() {
            @Override
            public Mono<Authentication> convert(ServerWebExchange exchange) {
                System.out.println(exchange.getRequest());
                String token = exchange.getRequest().getHeaders().getFirst("Authorization");
                System.out.println(token);
                if (token != null && token.startsWith("Bearer ")) {
                    token = token.substring(7);
                    System.out.println(token);
                    return Mono.just(SecurityContextHolder.getContext().getAuthentication());
                }
                return Mono.empty();
            }
        };
    }
}
