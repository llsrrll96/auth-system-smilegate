package com.javapp.gateway.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    @Autowired
    private JwtUtils jwtUtils;

    public AuthorizationHeaderFilter() {
        super(Config.class);
    }

    public static class Config {
        // application.yml 파일에서 지정한 filer의 Argument값을 받는 부분
    }

    @Override
    public GatewayFilter apply(Config config) {
        // custom pre filter
        return (exchange, chain) -> {
            String token = jwtUtils.getJwtFromRequestHeader(exchange.getRequest());

            if(token != null) {
                System.out.println("api,token: " + token);
                // jwt 를 헤더에 담아 서비스에 담아주는게 나은지
                // api server에서 userId로 헤더에 담아 요청하는게 나은지

                // 토큰 유효성 검증
                // feign 을 통해 인증서비스에 요청
                TokenUser tokenUser = jwtUtils.getUserFromJwt(token);

                // 토큰 유효성 검증 후
                addAuthorizationHeaders(exchange.getRequest(), tokenUser);
            }

            return chain.filter(exchange);
        };

    }

    private void addAuthorizationHeaders(ServerHttpRequest request, TokenUser tokenUser) {
        request.mutate()
                .header("X-Authorization-Id", tokenUser.getUserId())
                .header("X-Authorization-Role", tokenUser.getRole())
                .build();
    }

    @Bean
    public ErrorWebExceptionHandler tokenValidation() {
        return new JwtTokenExceptionHandler();
    }

}