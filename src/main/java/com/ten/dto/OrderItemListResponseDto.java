package com.ten.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderItemListResponseDto {

    private String itemName;
    private String color;
    private String size;
    private int price;
    private int count;
    private int orderPrice;

}
