package com.ten.service.cart;

import com.ten.domain.cart.CartItem;
import com.ten.domain.item.Item;
import com.ten.domain.member.Member;
import com.ten.exception.item.ItemNotFoundException;
import com.ten.exception.member.MemberNotFoundException;
import com.ten.repository.cart.CartItemRepository;
import com.ten.repository.cart.CartRepository;
import com.ten.repository.item.ItemRepository;
import com.ten.repository.member.MemberRepository;
import com.ten.request.cart.CartCreateRequest;
import com.ten.response.cart.CartDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;


    @Transactional
    public void cartSave(Long memberId, CartCreateRequest request) {

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Item item = itemRepository.findById(request.getItemId()).orElseThrow(ItemNotFoundException::new);

        cartItemRepository.save(CartItem.builder()
                .cart(member.getCart())
                .item(item)
                .count(request.getCount())
                .build());
    }

    @Transactional(readOnly = true)
    public CartDetailResponse myCart(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        List<CartItem> cartItems = member.getCart().getCartItems();
        return CartDetailResponse.toSave(cartItems);
    }

    @Transactional
    public void delete(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
}

































