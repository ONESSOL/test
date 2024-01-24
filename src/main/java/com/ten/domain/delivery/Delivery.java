package com.ten.domain.delivery;

import com.ten.domain.member.Address;
import com.ten.domain.order.Order;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import static com.ten.domain.delivery.DeliveryState.READY;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;
    @Embedded
    private Address address;
    @Enumerated(STRING)
    private DeliveryState deliveryState;
    @OneToOne(fetch = LAZY, mappedBy = "delivery")
    private Order order;

    @Builder
    public Delivery(Address address) {
        this.address = address;
        this.deliveryState = READY;
    }

    protected Delivery() {
    }

    public void createOrder(Order order) {
        this.order = order;
    }
}























