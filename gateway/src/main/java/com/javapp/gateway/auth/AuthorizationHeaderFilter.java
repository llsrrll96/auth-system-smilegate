package com.javapp.gateway.auth;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {



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
            String token = exchange.getRequest().getHeaders().get("Authorization").get(0).substring(7);

            System.out.println("api,token: "+ token);
            // jwt 를 헤더에 담아 서비스에 담아주는게 나은지
            // api server에서 userId로 헤더에 담아 요청하는게 나은지

            // 토큰 유효성 검증
            // feign 을 통해 인증서비스에 요청

            // 토큰 유효성 검증 후
            addAuthorizationHeaders(exchange.getRequest(), token);

            return chain.filter(exchange);
        };

    }

    private void addAuthorizationHeaders(ServerHttpRequest request, String token) {
        request.mutate()
                .header("Authorization", token)
                .build();
    }

//    @Bean
//    public ErrorWebExceptionHandler tokenValidation() {
//        return new JwtTokenExceptionHandler();
//    }
//
//    // 실제 토큰이 null, 만료 등 예외 상황에 따른 예외처리
//    public class JwtTokenExceptionHandler implements ErrorWebExceptionHandler {
//        private String getErrorCode(int errorCode) {
//            return "{\\errorCode\\"+":" + "errorCode" + "}";
//        }
//
//        @Override
//        public Mono<Void> handle(
//                ServerWebExchange exchange, Throwable ex) {
//            int errorCode = 500;
//            if (ex.getClass() == NullPointerException.class) {
//                errorCode = 100;
//            }
//
//            byte[] bytes = getErrorCode(errorCode).getBytes(StandardCharsets.UTF_8);
//            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
//            return exchange.getResponse().writeWith(Flux.just(buffer));
//        }
//    }

}