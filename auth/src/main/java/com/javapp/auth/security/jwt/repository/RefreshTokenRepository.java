package com.javapp.auth.security.jwt.repository;

import com.javapp.auth.domain.User;
import com.javapp.auth.security.jwt.models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    @Modifying
    int deleteByUser(User user);
}
