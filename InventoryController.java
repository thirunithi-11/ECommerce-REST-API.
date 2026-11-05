
package com.ecommerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.dto.InventoryRequest;
import com.ecommerce.dto.InventoryResponse;
import com.ecommerce.service.InventoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // Get product stock
    @GetMapping("/{productId}")
    public InventoryResponse getInventory(
            @PathVariable Long productId) {

        return inventoryService.getInventory(productId);
    }

    // Update product stock
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public InventoryResponse updateInventory(
            @Valid @RequestBody InventoryRequest request) {

        return inventoryService.updateInventory(request);
    }
}
