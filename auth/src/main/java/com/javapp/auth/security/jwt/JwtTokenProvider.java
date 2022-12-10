package com.javapp.auth.security.jwt;


import com.javapp.auth.domain.User;
import com.javapp.auth.exception.CustomAPIException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;

    public String generateToken(User user){
        Map<String, Object> payloads = new HashMap<>();
        payloads.put("userId", user.getUserId());
        payloads.put("email",user.getEmail());
        payloads.put("username",user.getUsername());
        payloads.put("role",user.getRole());

        long expireTime = new Date().getTime() + jwtExpirationInMs;
        Date expireDate = new Date(expireTime);

        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        return Jwts.builder()
                .setSubject("user-info")
                .setClaims(payloads)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Map<String, Object> getUserFromJwt(String token){
        Map<String, Object> claimMap = null;
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
        claimMap = claims;
        return claimMap;
    }

    public String getTokenFromHeader(HttpServletRequest request){
        return request.getHeader("authorization").substring("Bearer".length()).trim();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                    .build()
                    .parseClaimsJws(token);
            return true;
        }catch(io.jsonwebtoken.security.SecurityException ex) {
            throw new CustomAPIException(HttpStatus.BAD_GATEWAY, "Invalid JWT signature"); // 토큰 번호 발급한적 없거나 다름
        }catch(MalformedJwtException ex) {
            throw new CustomAPIException(HttpStatus.BAD_GATEWAY, "Invalid JWT token");
        }catch(ExpiredJwtException ex) {
            throw new CustomAPIException(HttpStatus.BAD_GATEWAY, "Expired JWT token");
        }catch(UnsupportedJwtException ex) {
            throw new CustomAPIException(HttpStatus.BAD_GATEWAY, "Unsupported JWT token");
        }catch(IllegalArgumentException ex) {
            throw new CustomAPIException(HttpStatus.BAD_GATEWAY, "JWT claims string is empty");
        }
    }
}
