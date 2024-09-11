package uz.mh.driver_load_crud.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Component
public class JwtAuthenticationFilter implements WebFilter {

    private final JWTAuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(JWTAuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange);

//        String path = exchange.getRequest().getURI().getPath();
//        if (path.startsWith("/swagger-ui/") || path.startsWith("/v3/api-docs/") || path.startsWith("/swagger-ui.html/") || path.startsWith("/api/signup") || path.startsWith("/api/login")) {
//            return chain.filter(exchange);
//        }
//        Mono<Void> voidMono = authenticationManager.authenticationConverter().convert(exchange)
//                .flatMap(authenticationManager::authenticate)
//                .flatMap(authentication -> Mono.deferContextual(ctx -> chain.filter(exchange)
//                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication))))
//                .onErrorResume(e -> chain.filter(exchange));
//        return voidMono;
    }
}
