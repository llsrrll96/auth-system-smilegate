package com.javapp.auth.security.jwt.repository;

import com.javapp.auth.domain.User;
import com.javapp.auth.security.jwt.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenJpaRepository extends JpaRepository<Token, Long> {

    int deleteByUser(User user);

    int deleteByRefreshToken(String refreshToken);
}
