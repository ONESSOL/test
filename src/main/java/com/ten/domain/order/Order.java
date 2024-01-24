package com.ten.domain.order;

import com.ten.domain.delivery.Delivery;
import com.ten.domain.member.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.ten.domain.order.OrderState.ORDER;
import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static java.time.LocalDateTime.now;

@Entity
@Getter
@Table(name = "orders")
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @OneToMany(mappedBy = "order", cascade = ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;
    private LocalDateTime orderDate;
    @Enumerated(STRING)
    private OrderState orderState;

    @Builder
    public Order(Member member, List<OrderItem> orderItems, Delivery delivery) {
        createMember(member);
        addOrderItems(orderItems);
        createDelivery(delivery);
        this.orderDate = now();
        this.orderState = ORDER;
    }

    protected Order() {
    }

    public void createMember(Member member) {
        this.member = member;
        member.addOrder(this);
    }

    public void addOrderItems(List<OrderItem> orderItems) {
        for(OrderItem orderItem : orderItems) {
            this.orderItems.add(orderItem);
            orderItem.createOrder(this);
        }
    }

    public void createDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.createOrder(this);
    }
}




























