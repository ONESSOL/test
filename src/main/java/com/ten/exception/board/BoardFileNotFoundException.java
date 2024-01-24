package com.ten.exception.board;

public class BoardFileNotFoundException extends RuntimeException {

    private static final String MESSAGE = "게시글의 파일을 찾을 수 없습니다.";

    public BoardFileNotFoundException() {
        super(MESSAGE);
    }

    public BoardFileNotFoundException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
