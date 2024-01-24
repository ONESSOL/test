package com.ten.response.item;

import com.ten.domain.item.Item;
import com.ten.domain.item.ItemCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemListResponse {

    private String itemName;
    private int price;
    private String color;
    private String size;
    private int quantity;
    private ItemCode itemCode;

    public static ItemListResponse toSave(Item item) {
        ItemListResponse response = new ItemListResponse();
        response.setItemName(item.getItemName());
        response.setPrice(item.getPrice());
        response.setColor(item.getColor());
        response.setSize(item.getSize());
        response.setQuantity(item.getQuantity());
        response.setItemCode(item.getItemCode());
        return response;
    }
}
