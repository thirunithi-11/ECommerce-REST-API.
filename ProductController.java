
package com.ecommerce.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.ecommerce.dto.ProductRequest;
import com.ecommerce.dto.ProductResponse;
import com.ecommerce.entity.Product;
import com.ecommerce.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Get all products
    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // Get product by ID
    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return convertToResponse(product);
    }

    // Add new product
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse addProduct(
            @Valid @RequestBody ProductRequest request) {
        Product product = productService.addProduct(request);
        return convertToResponse(product);
    }

    // Update product
    @PutMapping("/{id}")
    public ProductResponse updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        Product product = productService.updateProduct(id, request);
        return convertToResponse(product);
    }

    // Delete product
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    // Convert Product entity into response DTO
    private ProductResponse convertToResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getProductName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getImageUrl(),
                product.isActive()
        );
    }
}
