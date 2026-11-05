
package com.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.dto.ProductRequest;
import com.ecommerce.entity.Product;
import com.ecommerce.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Get product by ID
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // Add new product
    @Transactional
    public Product addProduct(ProductRequest request) {

        Product product = new Product();

        product.setProductName(request.getProductName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setImageUrl(request.getImageUrl());
        product.setActive(true);

        return productRepository.save(product);
    }

    // Update product
    @Transactional
    public Product updateProduct(Long id, ProductRequest request) {

        Product product = getProductById(id);

        product.setProductName(request.getProductName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setImageUrl(request.getImageUrl());

        return productRepository.save(product);
    }

    // Delete product
    @Transactional
    public void deleteProduct(Long id) {

        Product product = getProductById(id);
        productRepository.delete(product);
    }
}
