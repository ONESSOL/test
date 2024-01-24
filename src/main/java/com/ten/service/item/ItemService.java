package com.ten.service.item;

import com.nimbusds.jose.util.IOUtils;
import com.ten.domain.item.Item;
import com.ten.domain.item.ItemImage;
import com.ten.exception.item.ItemNotFoundException;
import com.ten.repository.item.ItemImageRepository;
import com.ten.repository.item.ItemRepository;
import com.ten.request.item.ItemCreateRequest;
import com.ten.response.item.ItemCreateResponse;
import com.ten.response.item.ItemDetailResponse;
import com.ten.response.item.ItemListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImageRepository itemImageRepository;

    @Transactional
    public ItemCreateResponse saveItem(ItemCreateRequest request) throws IOException {

        Item item = itemRepository.save(Item.builder()
                .itemName(request.getItemName())
                .price(request.getPrice())
                .color(request.getColor())
                .size(request.getSize())
                .quantity(request.getQuantity())
                .itemCode(request.getItemCode())
                .build());

        if (!request.getItemImages().get(0).isEmpty()) {
            for (MultipartFile itemImage : request.getItemImages()) {
                String originalFileName = itemImage.getOriginalFilename();
                String storedFileName = item.getItemName() + "_" + UUID.randomUUID() + ".jpg";
                String savePath = "C:\\Users\\user\\Desktop\\image\\" + item.getItemName() + "\\" + storedFileName;
                File file = new File(savePath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                itemImage.transferTo(file);
                itemImageRepository.save(ItemImage.builder()
                        .originalFileName(originalFileName)
                        .storedFileName(storedFileName)
                        .imageUrl(savePath)
                        .item(item)
                        .build());
            }
        }
        return ItemCreateResponse.toSave(item);
    }

    @Transactional(readOnly = true)
    public ItemDetailResponse findById(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(ItemNotFoundException::new);
        List<Item> itemList = itemRepository.findByItemName(item.getItemName());
        ItemDetailResponse.toSave(itemList);
        return ItemDetailResponse.toSave(itemList);
    }

    @Transactional(readOnly = true)
    public Page<ItemListResponse> findAll(Pageable pageable) {

        int firstPage = pageable.getPageNumber() - 1;
        PageRequest pageRequest = PageRequest.of(firstPage, 10, Sort.by(DESC, "id"));
        Page<Item> itemList = itemRepository.findAll(pageRequest);
        return itemList.map(ItemListResponse::toSave);
    }

    @Transactional
    public Page<ItemListResponse> findByItemName(String itemName, Pageable pageable) {

        int firstPage = pageable.getPageNumber() - 1;
        PageRequest pageRequest = PageRequest.of(firstPage, 10, Sort.by(DESC, "id"));
        Page<Item> itemList = itemRepository.findByItemNameContaining(itemName, pageRequest);
        return itemList.map(ItemListResponse::toSave);
    }
}































