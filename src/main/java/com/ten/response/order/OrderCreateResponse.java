package com.ten.response.order;

import com.ten.domain.member.Address;
import com.ten.domain.order.Order;
import com.ten.domain.order.OrderItem;
import com.ten.domain.order.OrderState;
import com.ten.dto.OrderItemDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class OrderCreateResponse {

    private List<OrderItemDto> orderItemDtos = new ArrayList<>();
    private int totalPrice;
    private Address address;
    private OrderState orderState;
    private LocalDateTime orderDate;

    public static OrderCreateResponse toSave(Order order) {
        OrderCreateResponse response = new OrderCreateResponse();
        List<OrderItemDto> orderItemDtoList = new ArrayList<>();
        int totalPrice = 0;
        for(OrderItem orderItem : order.getOrderItems()) {
            OrderItemDto dto = new OrderItemDto();
            dto.setItemName(orderItem.getItem().getItemName());
            dto.setPrice(orderItem.getItem().getPrice());
            dto.setCount(orderItem.getCount());
            dto.setOrderPrice(dto.getPrice() * dto.getCount());
            orderItemDtoList.add(dto);
            totalPrice += dto.getOrderPrice();
        }
        response.setOrderItemDtos(orderItemDtoList);
        response.setTotalPrice(totalPrice);
        response.setAddress(order.getDelivery().getAddress());
        response.setOrderState(order.getOrderState());
        response.setOrderDate(order.getOrderDate());
        return response;
    }
}
