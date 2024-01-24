package com.ten.service.order;

import com.ten.domain.cart.CartItem;
import com.ten.domain.delivery.Delivery;
import com.ten.domain.item.Item;
import com.ten.domain.member.Member;
import com.ten.domain.order.Order;
import com.ten.domain.order.OrderItem;
import com.ten.exception.item.ItemNotFoundException;
import com.ten.exception.member.MemberNotFoundException;
import com.ten.repository.delivery.DeliveryRepository;
import com.ten.repository.item.ItemRepository;
import com.ten.repository.member.MemberRepository;
import com.ten.repository.order.OrderItemRepository;
import com.ten.repository.order.OrderRepository;
import com.ten.request.order.OrderCreateRequest;
import com.ten.response.order.OrderCreateResponse;
import com.ten.response.order.OrderDetailResponse;
import com.ten.response.order.OrderListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final DeliveryRepository deliveryRepository;

    @Transactional
    public OrderCreateResponse saveOrder(Long memberId, OrderCreateRequest request) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Item item = itemRepository.findById(request.getItemId()).orElseThrow(ItemNotFoundException::new);

        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = orderItemRepository.save(OrderItem.builder()
                .item(item)
                .count(request.getCount())
                .orderPrice(request.getCount() * item.getPrice())
                .build());
        orderItemList.add(orderItem);

        Delivery delivery = deliveryRepository.save(Delivery.builder()
                .address(member.getAddress())
                .build());

        Order order = orderRepository.save(Order.builder()
                .member(member)
                .orderItems(orderItemList)
                .delivery(delivery)
                .build());
        return OrderCreateResponse.toSave(order);
    }

    @Transactional
    public OrderCreateResponse saveCartOrder(Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        List<OrderItem> orderItemList = new ArrayList<>();
        List<CartItem> cartItems = member.getCart().getCartItems();
        for (CartItem cartItem : cartItems) {
            Item item = cartItem.getItem();
            OrderItem orderItem = orderItemRepository.save(OrderItem.builder()
                    .item(item)
                    .count(cartItem.getCount())
                    .orderPrice(item.getPrice() * cartItem.getCount())
                    .build());
            orderItemList.add(orderItem);
        }

        Delivery delivery = deliveryRepository.save(Delivery.builder()
                .address(member.getAddress())
                .build());

        Order order = orderRepository.save(Order.builder()
                .member(member)
                .delivery(delivery)
                .orderItems(orderItemList)
                .build());

        return OrderCreateResponse.toSave(order);
    }

    @Transactional
    public Page<OrderListResponse> myOrder(Long memberId, Pageable pageable) {

        int firstPage = pageable.getPageNumber() - 1;
        PageRequest pageRequest = PageRequest.of(firstPage, 10, Sort.by(DESC, "id"));
        Page<Order> orderList = orderRepository.findByMemberId(memberId, pageRequest);
        return orderList.map(OrderListResponse::toSave);
    }
}














































