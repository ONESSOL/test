package com.ten.domain.item;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;

@Entity
@Getter
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;
    private String itemName;
    private int price;
    private String color;
    private String size;
    private int quantity;
    @Enumerated(STRING)
    private ItemCode itemCode;
    @OneToMany(mappedBy = "item", cascade = ALL, orphanRemoval = true)
    private List<ItemImage> itemImages = new ArrayList<>();

    @Builder
    public Item(String itemName, int price, String size, String color, int quantity, ItemCode itemCode) {
        this.itemName = itemName;
        this.price = price;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
        this.itemCode = itemCode;
    }

    protected Item() {
    }

    public void addImage(ItemImage itemImage) {
        this.itemImages.add(itemImage);
    }


}


































