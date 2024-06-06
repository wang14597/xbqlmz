package com.wwlei.gateway.security;

import com.wwlei.common.utils.JwtTokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Component
public class JwtTokenAuthenticationFilter implements WebFilter {

    private final ServerSecurityContextRepository securityContextRepository = new WebSessionServerSecurityContextRepository();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (token != null) {
            String substring = token.substring(7);
            if (JwtTokenProvider.validateToken(substring)) {
                return getAuthentication(substring)
                        .flatMap(authentication -> {
                            SecurityContext securityContext = new SecurityContextImpl(authentication);
                            return securityContextRepository
                                    .save(exchange, securityContext)
                                    .then(chain
                                            .filter(exchange)
                                            .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext))));
                        });
            }
        }
        return chain.filter(exchange);
    }

    public Mono<Authentication> getAuthentication(String token) {
        String username = JwtTokenProvider.getUsernameFromToken(token);
        String role = JwtTokenProvider.getRoleFromToken(token);
        GrantedAuthority authority = new SimpleGrantedAuthority(role);
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, Collections.singleton(authority));
        return Mono.just(authentication);
    }
}
