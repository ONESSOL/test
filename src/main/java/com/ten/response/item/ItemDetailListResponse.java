package com.ten.response.item;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ItemDetailListResponse {

    private List<ItemDetailResponse> responses;

    public static ItemDetailListResponse toSave(ItemDetailResponse response) {
        ItemDetailListResponse list = new ItemDetailListResponse();
        List<ItemDetailResponse> responseList = new ArrayList<>();
        responseList.add(response);
        list.setResponses(responseList);
        return list;
    }
}
