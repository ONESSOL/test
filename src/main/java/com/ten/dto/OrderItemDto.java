package com.ten.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderItemDto {

    private String itemName;
    private int price;
    private int count;
    private int orderPrice;

}
