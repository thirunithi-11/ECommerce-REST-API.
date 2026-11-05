
package com.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.dto.CartRequest;
import com.ecommerce.dto.CartItemRequest;
import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            ProductRepository productRepository,
            UserRepository userRepository) {

        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    // Create a new cart
    @Transactional
    public Cart createCart(CartRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = new Cart();
        cart.setUser(user);

        return cartRepository.save(cart);
    }

    // Get all carts
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    // Get cart by ID
    public Cart getCartById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    // Get all items in a cart
    public List<CartItem> getCartItems(Long cartId) {

        Cart cart = getCartById(cartId);

        return cartItemRepository.findByCart(cart);
    }

    // Add product to cart
    @Transactional
    public CartItem addItemToCart(Long cartId, CartItemRequest request) {

        Cart cart = getCartById(cartId);

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem item = cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElse(new CartItem());

        int newQuantity = request.getQuantity();

        if (item.getId() != null) {
            newQuantity += item.getQuantity();
        }

        if (product.getStockQuantity() < newQuantity) {
            throw new RuntimeException("Insufficient product stock");
        }

        item.setCart(cart);
        item.setProduct(product);
        item.setQuantity(newQuantity);

        return cartItemRepository.save(item);
    }

    // Update cart item quantity
    @Transactional
    public CartItem updateItemQuantity(Long cartId,
            Long itemId, Integer quantity) {

        Cart cart = getCartById(cartId);

        if (quantity == null || quantity <= 0) {
            throw new RuntimeException("Quantity must be greater than zero");
        }

        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!item.getCart().getId().equals(cart.getId())) {
            throw new RuntimeException("Cart item does not belong to this cart");
        }

        if (item.getProduct().getStockQuantity() < quantity) {
            throw new RuntimeException("Insufficient product stock");
        }

        item.setQuantity(quantity);

        return cartItemRepository.save(item);
    }

    // Remove one item from cart
    @Transactional
    public void removeItemFromCart(Long cartId, Long itemId) {

        Cart cart = getCartById(cartId);

        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!item.getCart().getId().equals(cart.getId())) {
            throw new RuntimeException("Cart item does not belong to this cart");
        }

        cartItemRepository.delete(item);
    }

    // Delete complete cart
    @Transactional
    public void deleteCart(Long id) {

        Cart cart = getCartById(id);

        List<CartItem> items = cartItemRepository.findByCart(cart);
        cartItemRepository.deleteAll(items);

        cartRepository.delete(cart);
    }
}

