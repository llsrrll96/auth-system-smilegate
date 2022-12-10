package com.javapp.auth.security;

import com.javapp.auth.domain.User;
import com.javapp.auth.domain.repository.AuthJpaRepository;
import com.javapp.auth.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final AuthJpaRepository authJpaRepository;

    @Override
    @Transactional(readOnly = true) // 영속성 컨텍스트는 변경 감지를 위한 스냅샷을 보관 X -> 성능 향상
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        User user = authJpaRepository.findById(Long.parseLong(userId))
                .orElseThrow(()->new UserNotFoundException("유저 정보 없음", HttpStatus.NOT_FOUND));

        return new PrincipalDetails(user);
    }
}
