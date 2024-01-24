package com.ten.controller.order;

import com.ten.config.SecurityUtil;
import com.ten.request.order.OrderCreateRequest;
import com.ten.response.order.OrderCreateResponse;
import com.ten.response.order.OrderListResponse;
import com.ten.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/save/order")
    public ResponseEntity<OrderCreateResponse> saveOrder(@RequestBody OrderCreateRequest request) {
        return ResponseEntity.ok(orderService.saveOrder(SecurityUtil.currentMemberId(), request));
    }

    @PostMapping("/save/cart/order")
    public ResponseEntity<OrderCreateResponse> saveCartOrder() {
        return ResponseEntity.ok(orderService.saveCartOrder(SecurityUtil.currentMemberId()));
    }

    @GetMapping("/myOrder/list")
    public ResponseEntity<Page<OrderListResponse>> myOrder(@PageableDefault(page = 1, sort = "id")Pageable pageable) {
        return ResponseEntity.ok(orderService.myOrder(SecurityUtil.currentMemberId(), pageable));
    }
}












































