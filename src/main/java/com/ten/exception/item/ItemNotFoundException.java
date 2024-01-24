package com.ten.exception.item;

public class ItemNotFoundException extends RuntimeException {

    private static final String MESSAGE = "상품을 찾을 수 없습니다.";

    public ItemNotFoundException() {
        super(MESSAGE);
    }

    public ItemNotFoundException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
