package com.ten.dto;

import com.ten.domain.cart.CartItem;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartItemDto {

    private String itemName;
    private int price;
    private int count;
    private int orderPrice;

    public static CartItemDto toSave(CartItem cartItem) {
        CartItemDto dto = new CartItemDto();
        dto.setItemName(cartItem.getItem().getItemName());
        dto.setPrice(cartItem.getItem().getPrice());
        dto.setCount(cartItem.getCount());
        dto.setOrderPrice(cartItem.getItem().getPrice() * cartItem.getCount());
        return dto;
    }
}
