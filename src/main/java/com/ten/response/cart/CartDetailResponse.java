package com.ten.response.cart;

import com.ten.domain.cart.CartItem;
import com.ten.dto.CartItemDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class CartDetailResponse {

    private List<CartItemDto> cartItemDtoList = new ArrayList<>();
    private int totalPrice;

    public static CartDetailResponse toSave(List<CartItem> cartItems) {

        CartDetailResponse response = new CartDetailResponse();
        List<CartItemDto> cartItemDtos = new ArrayList<>();
        for(CartItem cartItem : cartItems) {
            CartItemDto dto = CartItemDto.toSave(cartItem);
            cartItemDtos.add(dto);
        }
        response.setCartItemDtoList(cartItemDtos);
        int totalPrice = 0;
        for(CartItemDto dto : response.cartItemDtoList) {
            totalPrice += dto.getOrderPrice();
        }
        response.setTotalPrice(totalPrice);
        return response;
    }
}
