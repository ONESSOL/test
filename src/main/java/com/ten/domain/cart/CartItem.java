package com.ten.domain.cart;

import com.ten.domain.item.Item;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class CartItem {

    @Id @GeneratedValue
    @Column(name = "cart_item_id")
    private Long id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
    private int count;

    @Builder
    public CartItem(Cart cart, Item item, int count) {
        this.cart = cart;
        this.item = item;
        this.count = count;
    }

    protected CartItem() {
    }
}
