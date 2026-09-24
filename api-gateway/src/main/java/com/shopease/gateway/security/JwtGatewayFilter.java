package com.shopease.gateway.security;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtGatewayFilter implements Ordered {

    private final JwtService jwtService;

    public JwtGatewayFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain) {

        String path = exchange
                .getRequest()
                .getURI()
                .getPath();

        // Login and registration don't require JWT
        if (path.equals("/api/auth/login")
                || path.equals("/api/auth/register")
                || path.equals("/actuator/health")) {

            return chain.filter(exchange);
        }

        String authHeader = exchange
                .getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null
                || !authHeader.startsWith("Bearer ")) {

            return unauthorized(exchange);
        }

        String token = authHeader.substring(7);

        if (!jwtService.isTokenValid(token)) {

            return unauthorized(exchange);
        }

        return chain.filter(exchange);
    }

    private Mono<Void> unauthorized(
            ServerWebExchange exchange) {

        exchange.getResponse()
                .setStatusCode(HttpStatus.UNAUTHORIZED);

        return exchange.getResponse()
                .setComplete();
    }

    @Override
    public int getOrder() {

        return -1;
    }
}
