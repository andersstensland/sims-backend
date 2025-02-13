package org.kristiania.smartinventorymanagementsystem.controllers;

import org.kristiania.smartinventorymanagementsystem.model.Product;
import org.kristiania.smartinventorymanagementsystem.service.InventoryService;
import org.springframework.web.bind.annotation.*;

/**
 * Handles endpoints for inventory management requests
 */
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    /**
     * Handles the endpoint for adding products to database
     * @param product to add to database
     */
    @PostMapping("/products")
    public void addProduct(@RequestBody Product product) {
        inventoryService.addProduct(product);
    }

    /**
     * Handles endpoint for selling products
     * @param id of sold product
     * @param quantity sold of product
     */
    @PostMapping("/products/{id}/sell")
    public void sellProduct(@PathVariable int id,
                            @RequestParam int quantity) {
        inventoryService.sellProduct(id, quantity);
    }

    // You might create a GET method that fetches all products from the repository, etc.
}

