package com.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.entity.Wishlist;
import com.ecommerce.entity.WishlistItem;
import com.ecommerce.entity.Product;

public interface WishlistItemRepository
        extends JpaRepository<WishlistItem, Long> {

    List<WishlistItem> findByWishlist(Wishlist wishlist);

    Optional<WishlistItem> findByWishlistAndProduct(
            Wishlist wishlist, Product product);
}