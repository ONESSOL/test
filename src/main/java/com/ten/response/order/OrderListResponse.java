package com.ten.response.order;

import com.ten.domain.order.Order;
import com.ten.domain.order.OrderItem;
import com.ten.domain.order.OrderState;
import com.ten.dto.OrderItemListResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class OrderListResponse {

    private Long orderId;
    private List<OrderItemListResponseDto> orderItemListResponseDtos = new ArrayList<>();
    private OrderState orderState;
    private LocalDateTime orderDate;

    public static OrderListResponse toSave(Order order) {
        OrderListResponse response = new OrderListResponse();
        response.setOrderId(order.getId());
        List<OrderItemListResponseDto> orderItemListResponseDtoList = new ArrayList<>();
        for(OrderItem orderItem : order.getOrderItems()) {
            OrderItemListResponseDto dto = new OrderItemListResponseDto();
            dto.setItemName(orderItem.getItem().getItemName());
            dto.setColor(orderItem.getItem().getColor());
            dto.setSize(orderItem.getItem().getSize());
            dto.setPrice(orderItem.getItem().getPrice());
            dto.setCount(orderItem.getCount());
            dto.setOrderPrice(orderItem.getItem().getPrice() * orderItem.getCount());
            orderItemListResponseDtoList.add(dto);
        }
        response.setOrderItemListResponseDtos(orderItemListResponseDtoList);
        response.setOrderState(order.getOrderState());
        response.setOrderDate(order.getOrderDate());
        return response;
    }

}























