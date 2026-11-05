package com.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.dto.WishlistRequest;
import com.ecommerce.dto.WishlistItemRequest;
import com.ecommerce.entity.User;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.Wishlist;
import com.ecommerce.entity.WishlistItem;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.WishlistRepository;
import com.ecommerce.repository.WishlistItemRepository;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public WishlistService(WishlistRepository wishlistRepository,
            WishlistItemRepository wishlistItemRepository,
            UserRepository userRepository,
            ProductRepository productRepository) {

        this.wishlistRepository = wishlistRepository;
        this.wishlistItemRepository = wishlistItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Wishlist createWishlist(WishlistRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (wishlistRepository.existsByUser(user)) {
            throw new RuntimeException("Wishlist already exists");
        }

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);

        return wishlistRepository.save(wishlist);
    }

    public Wishlist getWishlistById(Long id) {
        return wishlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Wishlist not found"));
    }

    public List<Wishlist> getAllWishlists() {
        return wishlistRepository.findAll();
    }

    public List<WishlistItem> getWishlistItems(Long wishlistId) {
        Wishlist wishlist = getWishlistById(wishlistId);
        return wishlistItemRepository.findByWishlist(wishlist);
    }

    @Transactional
    public WishlistItem addItemToWishlist(
            Long wishlistId, WishlistItemRequest request) {

        Wishlist wishlist = getWishlistById(wishlistId);

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (wishlistItemRepository.findByWishlistAndProduct(
                wishlist, product).isPresent()) {
            throw new RuntimeException("Product already exists in wishlist");
        }

        WishlistItem item = new WishlistItem();
        item.setWishlist(wishlist);
        item.setProduct(product);

        return wishlistItemRepository.save(item);
    }

    @Transactional
    public void removeItemFromWishlist(Long wishlistId, Long itemId) {

        Wishlist wishlist = getWishlistById(wishlistId);

        WishlistItem item = wishlistItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Wishlist item not found"));

        if (!item.getWishlist().getId().equals(wishlist.getId())) {
            throw new RuntimeException("Item does not belong to this wishlist");
        }

        wishlistItemRepository.delete(item);
    }

    @Transactional
    public void deleteWishlist(Long id) {

        Wishlist wishlist = getWishlistById(id);

        List<WishlistItem> items =
                wishlistItemRepository.findByWishlist(wishlist);

        wishlistItemRepository.deleteAll(items);
        wishlistRepository.delete(wishlist);
    }
}