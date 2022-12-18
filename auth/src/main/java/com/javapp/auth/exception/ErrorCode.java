package com.javapp.auth.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND(1401,"USER-001","아이디 또는 비밀번호를 잘못 입력했습니다."),
    TOKEN_IS_NOT_FOUND(1411, "TOKEN-001", "토큰이 DB에 없는 경우: 재로그인");

    private final int status;
    private final String code;
    private final String message;
}
