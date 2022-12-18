package com.javapp.auth.security.jwt.repository;

import com.javapp.auth.domain.User;
import com.javapp.auth.security.jwt.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenJpaRepository extends JpaRepository<Token, Long> {

    int deleteByUser(User user);

    Optional<Token> findByAccessToken(String accessToken);

    int deleteByAccessToken(String accessToken);
}
