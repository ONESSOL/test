package com.ten.domain.order;

import com.ten.domain.item.Item;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;
    private int orderPrice;
    private int count;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Builder
    public OrderItem(Item item, int count, int orderPrice) {
        this.item = item;
        this.count = count;
        this.orderPrice = orderPrice;
    }

    protected OrderItem() {
    }

    public void createOrder(Order order) {
        this.order = order;
    }
}






















