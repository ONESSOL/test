package com.ten.domain.board;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BoardSearchCode {

    T_C("제목+내용"), CB("작성자");
    private final String key;
}
