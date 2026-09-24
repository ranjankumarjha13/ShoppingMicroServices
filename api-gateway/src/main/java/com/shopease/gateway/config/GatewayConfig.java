package com.shopease.gateway.config;

import com.shopease.gateway.security.JwtGatewayFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.cloud.gateway.filter.GlobalFilter;

@Configuration
public class GatewayConfig {

    @Bean
    public GlobalFilter jwtGlobalFilter(
            JwtGatewayFilter jwtGatewayFilter) {

        return (exchange, chain) ->
                jwtGatewayFilter.filter(exchange, chain);
    }
}
