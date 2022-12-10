package com.javapp.auth.domain.repository;

import com.javapp.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthJpaRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
