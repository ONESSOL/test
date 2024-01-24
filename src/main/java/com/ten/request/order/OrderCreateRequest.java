package com.ten.request.order;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderCreateRequest {

    private Long itemId;
    private int count;
}
