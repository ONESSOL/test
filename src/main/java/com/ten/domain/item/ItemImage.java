package com.ten.domain.item;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class ItemImage {

    @Id @GeneratedValue
    @Column(name = "item_image_id")
    private Long id;
    private String originalFileName;
    private String storedFileName;
    private String imageUrl;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Builder
    public ItemImage(String originalFileName, String storedFileName, String imageUrl, Item item) {
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
        this.imageUrl = imageUrl;
        createItem(item);
    }

    protected ItemImage() {
    }

    public void createItem(Item item) {
        this.item = item;
        item.addImage(this);
    }
}
























