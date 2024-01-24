package com.ten.request.cart;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartCreateRequest {

    private Long itemId;
    private int count;

}
