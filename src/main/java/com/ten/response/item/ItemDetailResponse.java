package com.ten.response.item;

import com.ten.domain.item.Item;
import com.ten.domain.item.ItemImage;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ItemDetailResponse {

    private String itemName;
    private int price;
    private List<String> colors;
    private List<String> sizes;
    private List<String> storedFileName;

    public static ItemDetailResponse toSave(List<Item> items) {
        ItemDetailResponse response = new ItemDetailResponse();
        response.setItemName(items.get(0).getItemName());
        response.setPrice(items.get(0).getPrice());
        List<String> sizeList = new ArrayList<>();
        List<String> colorList = new ArrayList<>();
        for (Item item : items) {
            String color = item.getColor();
            colorList.add(color);
            colorList = colorList.stream().distinct().collect(Collectors.toList());
            String size = item.getSize();
            if(item.getQuantity() == 0) {
                sizeList.add(color + "_" + size + "[품절]");
            } else {
                sizeList.add(color + "_" + size);
            }
        }
        response.setColors(colorList);
        response.setSizes(sizeList);
        List<String> storedFileNameList = new ArrayList<>();
        for (ItemImage itemImage : items.get(0).getItemImages()) {
            storedFileNameList.add(itemImage.getStoredFileName());
        }
        response.setStoredFileName(storedFileNameList);
        return response;
    }
}




















