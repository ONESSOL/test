package com.ten.exception.member;

public class NewPasswordMisMatchException extends RuntimeException {

    private static final String MESSAGE = "비밀번호가 일치하지 않습니다.";

    public NewPasswordMisMatchException() {
        super(MESSAGE);
    }

    public NewPasswordMisMatchException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
