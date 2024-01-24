package com.ten.request.item;

import com.ten.domain.item.ItemCode;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter @Setter
public class ItemCreateRequest {

    private String itemName;
    private int price;
    private String size;
    private String color;
    private int quantity;
    private ItemCode itemCode;
    private List<MultipartFile> itemImages;

}
