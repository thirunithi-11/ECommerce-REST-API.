
package com.ecommerce.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.dto.InventoryRequest;
import com.ecommerce.dto.InventoryResponse;
import com.ecommerce.entity.Product;
import com.ecommerce.repository.ProductRepository;

@Service
public class InventoryService {

    private final ProductRepository productRepository;

    public InventoryService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Get current stock
    public InventoryResponse getInventory(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return convertToResponse(product);
    }

    // Update product stock
    @Transactional
    public InventoryResponse updateInventory(InventoryRequest request) {

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setStockQuantity(request.getQuantity());

        Product updatedProduct = productRepository.save(product);

        return convertToResponse(updatedProduct);
    }

    private InventoryResponse convertToResponse(Product product) {

        return new InventoryResponse(
                product.getId(),
                product.getProductName(),
                product.getStockQuantity(),
                product.getPrice()
        );
    }
}
