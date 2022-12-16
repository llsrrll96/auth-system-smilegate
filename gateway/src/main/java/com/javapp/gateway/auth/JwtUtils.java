package com.javapp.gateway.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import java.security.Key;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@RequiredArgsConstructor
public class JwtUtils implements InitializingBean {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    private Key key;


    // Init supporter
    @Override
    public void afterPropertiesSet() throws Exception {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public TokenUser getUserFromJwt(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return TokenUser.builder()
                .userId(String.valueOf(claims.get("userId")))
                .role((String) claims.get("role"))
                .build();
    }

    public String getJwtFromRequestHeader(ServerHttpRequest request){
        try{
            String bearerToken = request.getHeaders().get("Authorization").get(0).substring(7);
            if(StringUtils.hasText(bearerToken)){
                return bearerToken;
            }
            return null;
        }catch (NullPointerException e){
            return null;
        }
    }


}
