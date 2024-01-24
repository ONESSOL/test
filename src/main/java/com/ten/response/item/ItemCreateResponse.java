package com.ten.response.item;

import com.ten.domain.item.Item;
import com.ten.domain.item.ItemCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemCreateResponse {

    private String itemName;
    private int price;
    private String size;
    private String color;
    private ItemCode itemCode;

    public static ItemCreateResponse toSave(Item item) {
        ItemCreateResponse response = new ItemCreateResponse();
        response.setItemName(item.getItemName());
        response.setPrice(item.getPrice());
        response.setSize(item.getSize());
        response.setColor(item.getColor());
        response.setItemCode(item.getItemCode());
        return response;
    }
}
