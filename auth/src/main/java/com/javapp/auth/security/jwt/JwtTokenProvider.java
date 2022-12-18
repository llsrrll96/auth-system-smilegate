package com.javapp.auth.security.jwt;


import com.javapp.auth.domain.User;
import com.javapp.auth.exception.CustomAPIException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;

    @Value("${app.refresh-secret}")
    private String refreshSecret;
    @Value("${app.refresh-expiration-milliseconds}")
    private int refreshExpirationInMs;

    public String generateAccessToken(User user){
        Map<String, Object> payloads = new HashMap<>();
        payloads.put("userId", user.getUserId());
//        payloads.put("email",user.getEmail());
//        payloads.put("username",user.getUsername());
//        payloads.put("role",user.getRole());

        Date expireDate = createExpireDate(jwtExpirationInMs);

        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        return Jwts.builder()
                .setSubject("user-info")
                .setClaims(payloads)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateRefreshToken(User user){
        Map<String, Object> payloads = new HashMap<>();
        payloads.put("userId", user.getUserId());
//        payloads.put("email",user.getEmail());
//        payloads.put("username",user.getUsername());
//        payloads.put("role",user.getRole());

        Date expireDate = createExpireDate(refreshExpirationInMs);

        Key key= Keys.hmacShaKeyFor(refreshSecret.getBytes());
        return Jwts.builder()
                .setSubject("user-info")
                .setClaims(payloads)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }


    private Date createExpireDate(long expirationInMs){
        long expireTime = new Date().getTime() + expirationInMs;
        return new Date(expireTime);
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

    public String getJwtFromRequestHeader(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring("Bearer".length());
        }
        return null;
    }

    public boolean validateJwtoken(String token){
        Key key= Keys.hmacShaKeyFor(jwtSecret.getBytes());
        return validateToken(token, key);
    }
    public boolean validateRefreshToken(String token){
        Key key= Keys.hmacShaKeyFor(refreshSecret.getBytes());
        return validateToken(token, key);
    }
    private boolean validateToken(String token, Key key){
        try{
            Jwts.parserBuilder().setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        }catch(io.jsonwebtoken.security.SecurityException ex) {
            log.error("Invalid JWT signature");
            throw new CustomAPIException(HttpStatus.BAD_GATEWAY, "Invalid JWT signature"); // 토큰 번호 발급한적 없거나 다름
        }catch(MalformedJwtException ex) {
            log.error("Invalid JWT token");
            throw new CustomAPIException(HttpStatus.BAD_GATEWAY, "Invalid JWT token");
        }catch(ExpiredJwtException ex) {
            return false;
        }catch(UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
            throw new CustomAPIException(HttpStatus.BAD_GATEWAY, "Unsupported JWT token");
        }catch(IllegalArgumentException ex) {
            log.error("JWT claims string is empty");
            throw new CustomAPIException(HttpStatus.BAD_GATEWAY, "JWT claims string is empty");
        }
    }
}
