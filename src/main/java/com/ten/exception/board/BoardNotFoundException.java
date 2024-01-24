package com.ten.exception.board;

public class BoardNotFoundException extends RuntimeException {

    private static final String MESSAGE = "게시글을 찾을 수 없습니다.";

    public BoardNotFoundException() {
        super(MESSAGE);
    }

    public BoardNotFoundException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
