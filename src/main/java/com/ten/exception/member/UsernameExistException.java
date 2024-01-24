package com.ten.exception.member;

public class UsernameExistException extends RuntimeException {

    private static final String MESSAGE = "중복된 아이디 입니다.";

    public UsernameExistException() {
        super(MESSAGE);
    }

    public UsernameExistException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
