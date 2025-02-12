package org.kristiania.smartinventorymanagementsystem.controllers;

import org.kristiania.smartinventorymanagementsystem.model.Product;
import org.kristiania.smartinventorymanagementsystem.service.InventoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/products")
    public void addProduct(@RequestBody Product product) {
        inventoryService.addProduct(product);
    }

    @PostMapping("/products/{id}/sell")
    public void sellProduct(@PathVariable Long id,
                            @RequestParam int quantity) {
        inventoryService.sellProduct(id, quantity);
    }

    // You might create a GET method that fetches all products from the repository, etc.
}

