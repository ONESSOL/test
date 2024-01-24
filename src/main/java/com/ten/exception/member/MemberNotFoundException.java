package com.ten.exception.member;

public class MemberNotFoundException extends RuntimeException {

    private static final String MESSAGE = "회원을 찾을 수 없습니다.";

    public MemberNotFoundException() {
        super(MESSAGE);
    }

    public MemberNotFoundException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
