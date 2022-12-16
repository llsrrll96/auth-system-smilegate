package com.javapp.gateway.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JwtTokenExceptionHandler implements ErrorWebExceptionHandler {
    @Autowired
    private ObjectMapper objectMapper;

    private String getErrorCode(String error, Map<String, String> requestLoginMap) {
        try{
            error = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestLoginMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return error;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        // for return json
        ServerHttpResponse response = exchange.getResponse();

        if(response.isCommitted()) { // 응답이 이미 Client에 커밋되었는지 여부 확인
            return Mono.error(ex);
        }

        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        if(ex instanceof ResponseStatusException){
            response.setStatusCode(((ResponseStatusException) ex).getStatus());
        }

        Map<String ,String> requestLoginMap = new HashMap<>();
        // statusCode : 200 OK
        String statusCode = Objects.requireNonNull(response.getStatusCode()).toString();
        if(statusCode.split(" ").length == 2){
            requestLoginMap.put("status", response.getStatusCode().toString().split(" ")[0]);
            requestLoginMap.put("message","requestLogin");
        }

        String error = "Gateway Error";

        byte[] bytes = getErrorCode(error,requestLoginMap).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return exchange.getResponse().writeWith(Flux.just(buffer));
    }
}
