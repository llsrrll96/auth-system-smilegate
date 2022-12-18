package com.javapp.auth.security;

import com.javapp.auth.domain.User;
import com.javapp.auth.domain.repository.UserJpaRepository;
import com.javapp.auth.exception.ErrorCode;
import com.javapp.auth.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserJpaRepository userJpaRepository;

    @Override
    @Transactional(readOnly = true) // 영속성 컨텍스트는 변경 감지를 위한 스냅샷을 보관 X -> 성능 향상
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        User user = userJpaRepository.findById(Long.parseLong(userId))
                .orElseThrow(()->new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        return new PrincipalDetails(user);
    }
}
