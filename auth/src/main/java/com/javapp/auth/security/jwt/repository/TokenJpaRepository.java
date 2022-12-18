package com.javapp.auth.security.jwt.repository;

import com.javapp.auth.domain.User;
import com.javapp.auth.security.jwt.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface TokenJpaRepository extends JpaRepository<Token, Long> {

    @Modifying
    int deleteByUser(User user);

    Token findByAccessToken(String accessToken);
}
