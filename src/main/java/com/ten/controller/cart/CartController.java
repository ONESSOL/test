package com.ten.controller.cart;

import com.ten.config.SecurityUtil;
import com.ten.request.cart.CartCreateRequest;
import com.ten.response.cart.CartDetailResponse;
import com.ten.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping("/save")
    public ResponseEntity<Void> cartSave(@RequestBody CartCreateRequest request) {
        cartService.cartSave(SecurityUtil.currentMemberId(), request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/my_cart")
    public ResponseEntity<CartDetailResponse> myCart() {
        return ResponseEntity.ok(cartService.myCart(SecurityUtil.currentMemberId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cartService.delete(id);
        return ResponseEntity.ok().build();
    }
}






































