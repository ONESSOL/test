package com.ten.domain.member;

import com.ten.domain.BaseTimeEntity;
import com.ten.domain.cart.Cart;
import com.ten.domain.order.Order;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static com.ten.domain.member.Role.USER;
import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private String password;
    private String name;
    private String phoneNum;
    private String email;
    @Embedded
    private Address address;
    @Enumerated(STRING)
    private Role role;
    @Enumerated(STRING)
    private SocialType socialType;
    private String socialId;
    @OneToOne(fetch = LAZY, cascade = REMOVE, orphanRemoval = true)
    @JoinColumn(name = "cart_id")
    private Cart cart;
    @OneToMany(mappedBy = "member", cascade = ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @Builder
    public Member(String username, String password, String name, String phoneNum, String email, Address address, Role role, SocialType socialType, String socialId, Cart cart) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phoneNum = phoneNum;
        this.email = email;
        this.address = address;
        this.role = role;
        this.socialType = socialType;
        this.socialId = socialId;
        this.cart = cart;
    }

    protected Member() {
    }

    public void additionalAddress(Address address) {
        this.address = address;
        this.role = USER;
    }

    public void createCart(Cart cart) {
        this.cart = cart;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    public void updateWithPassword(String password, String phoneNum, String email, Address address) {
        this.password = password;
        this.phoneNum = phoneNum;
        this.email = email;
        this.address = address;
    }

    public void updateWithoutPassword(String phoneNum, String email, Address address) {
        this.phoneNum = phoneNum;
        this.email = email;
        this.address = address;
    }

    public void changeId(String socialId) {
        this.id = Long.parseLong(socialId);
    }
}

























