package com.ten.domain.cart;

import com.ten.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class Cart {

    @Id @GeneratedValue
    @Column(name = "cart_id")
    private Long id;
    @OneToOne(fetch = LAZY, mappedBy = "cart")
    private Member member;
    @OneToMany(mappedBy = "cart", cascade = ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

    public static Cart createCart() {
        return new Cart();
    }
    protected Cart() {
    }

}





































