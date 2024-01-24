package com.ten.exception.token;

public class RefreshTokenNotFoundException extends RuntimeException {

    private static final String MESSAGE = "로그인 후 이용 가능합니다.";

    public RefreshTokenNotFoundException() {
        super(MESSAGE);
    }

    public RefreshTokenNotFoundException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
