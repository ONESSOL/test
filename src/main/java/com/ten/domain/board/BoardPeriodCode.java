package com.ten.domain.board;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BoardPeriodCode {

    WEEK("1"), MONTH("4"), ALL("all");
    private final String key;
}
