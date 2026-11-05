
package com.ecommerce.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.ecommerce.dto.CartRequest;
import com.ecommerce.dto.CartItemRequest;
import com.ecommerce.dto.CartItemResponse;
import com.ecommerce.dto.CartResponse;
import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartItem;
import com.ecommerce.service.CartService;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CartResponse createCart(
            @Valid @RequestBody CartRequest request) {
        Cart cart = cartService.createCart(request);
        return convertToResponse(cart);
    }

    @GetMapping
    public List<CartResponse> getAllCarts() {
        return cartService.getAllCarts()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public CartResponse getCartById(@PathVariable Long id) {
        Cart cart = cartService.getCartById(id);
        return convertToResponse(cart);
    }

    @PostMapping("/{cartId}/items")
    @ResponseStatus(HttpStatus.CREATED)
    public CartItemResponse addItemToCart(
            @PathVariable Long cartId,
            @Valid @RequestBody CartItemRequest request) {

        CartItem item = cartService.addItemToCart(cartId, request);
        return convertItemToResponse(item);
    }

    @PutMapping("/{cartId}/items/{itemId}")
    public CartItemResponse updateItemQuantity(
            @PathVariable Long cartId,
            @PathVariable Long itemId,
            @RequestParam Integer quantity) {

        CartItem item = cartService.updateItemQuantity(
                cartId, itemId, quantity);

        return convertItemToResponse(item);
    }

    @DeleteMapping("/{cartId}/items/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeItemFromCart(
            @PathVariable Long cartId,
            @PathVariable Long itemId) {

        cartService.removeItemFromCart(cartId, itemId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);
    }

    private CartResponse convertToResponse(Cart cart) {

        List<CartItemResponse> items = cartService.getCartById(cart.getId())
                .getId() != null
                ? cartServiceItems(cart)
                : List.of();

        BigDecimal totalAmount = items.stream()
                .map(CartItemResponse::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CartResponse(
                cart.getId(),
                cart.getUser().getId(),
                cart.getCreatedAt(),
                items,
                totalAmount
        );
    }

    private List<CartItemResponse> cartServiceItems(Cart cart) {
        return cart.getId() == null
                ? List.of()
                : cartItemsForResponse(cart);
    }

    private List<CartItemResponse> cartItemsForResponse(Cart cart) {
        // Cart item details are loaded by the service.
        return cartService.getCartItems(cart.getId())
                .stream()
                .map(this::convertItemToResponse)
                .toList();
    }

    private CartItemResponse convertItemToResponse(CartItem item) {

        BigDecimal subtotal = item.getProduct().getPrice()
                .multiply(BigDecimal.valueOf(item.getQuantity()));

        return new CartItemResponse(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getProductName(),
                item.getProduct().getPrice(),
                item.getQuantity(),
                subtotal
        );
    }
}
