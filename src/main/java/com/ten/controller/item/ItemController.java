package com.ten.controller.item;

import com.ten.request.item.ItemCreateRequest;
import com.ten.response.item.ItemCreateResponse;
import com.ten.response.item.ItemDetailResponse;
import com.ten.response.item.ItemListResponse;
import com.ten.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/save")
    public ResponseEntity<ItemCreateResponse> saveItem(@ModelAttribute ItemCreateRequest request) throws IOException {
        return ResponseEntity.ok(itemService.saveItem(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDetailResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.findById(id));
    }

    @GetMapping("/list")
    public ResponseEntity<Page<ItemListResponse>> findAll(@PageableDefault(page = 1, sort = "id")Pageable pageable) {
        return ResponseEntity.ok(itemService.findAll(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ItemListResponse>> findByItemName(@RequestParam String itemName,
                                                                 @PageableDefault(page = 1, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(itemService.findByItemName(itemName, pageable));
    }
}



































