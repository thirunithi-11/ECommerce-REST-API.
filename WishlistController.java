package com.ecommerce.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.ecommerce.dto.WishlistRequest;
import com.ecommerce.dto.WishlistItemRequest;
import com.ecommerce.dto.WishlistItemResponse;
import com.ecommerce.dto.WishlistResponse;
import com.ecommerce.entity.Wishlist;
import com.ecommerce.entity.WishlistItem;
import com.ecommerce.service.WishlistService;

@RestController
@RequestMapping("/wishlists")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WishlistResponse createWishlist(
            @Valid @RequestBody WishlistRequest request) {

        Wishlist wishlist = wishlistService.createWishlist(request);
        return convertToResponse(wishlist);
    }

    @GetMapping
    public List<WishlistResponse> getAllWishlists() {
        return wishlistService.getAllWishlists()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public WishlistResponse getWishlistById(@PathVariable Long id) {
        Wishlist wishlist = wishlistService.getWishlistById(id);
        return convertToResponse(wishlist);
    }

    @PostMapping("/{wishlistId}/items")
    @ResponseStatus(HttpStatus.CREATED)
    public WishlistItemResponse addItemToWishlist(
            @PathVariable Long wishlistId,
            @Valid @RequestBody WishlistItemRequest request) {

        WishlistItem item = wishlistService.addItemToWishlist(
                wishlistId, request);

        return convertItemToResponse(item);
    }

    @DeleteMapping("/{wishlistId}/items/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeItemFromWishlist(
            @PathVariable Long wishlistId,
            @PathVariable Long itemId) {

        wishlistService.removeItemFromWishlist(wishlistId, itemId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWishlist(@PathVariable Long id) {
        wishlistService.deleteWishlist(id);
    }

    private WishlistResponse convertToResponse(Wishlist wishlist) {

        List<WishlistItemResponse> items =
                wishlistService.getWishlistItems(wishlist.getId())
                        .stream()
                        .map(this::convertItemToResponse)
                        .toList();

        return new WishlistResponse(
                wishlist.getId(),
                wishlist.getUser().getId(),
                wishlist.getCreatedAt(),
                items
        );
    }

    private WishlistItemResponse convertItemToResponse(WishlistItem item) {

        return new WishlistItemResponse(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getProductName(),
                item.getProduct().getPrice(),
                item.getProduct().getImageUrl()
        );
    }
}