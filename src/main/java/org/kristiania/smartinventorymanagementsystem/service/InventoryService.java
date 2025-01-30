package org.kristiania.smartinventorymanagementsystem.service;

import org.kristiania.smartinventorymanagementsystem.model.Product;

import org.kristiania.smartinventorymanagementsystem.exceptions.LowStockException;
import org.kristiania.smartinventorymanagementsystem.exceptions.ProductNotFoundException;
import org.kristiania.smartinventorymanagementsystem.model.Product;
import org.kristiania.smartinventorymanagementsystem.model.Sale;
import org.kristiania.smartinventorymanagementsystem.repository.ProductRepository;
import org.kristiania.smartinventorymanagementsystem.repository.SaleRepository;
import org.kristiania.smartinventorymanagementsystem.util.StockAlertNotifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InventoryService {

    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final StockAlertNotifier stockAlertNotifier;

    @Autowired
    public InventoryService(ProductRepository productRepository,
                            SaleRepository saleRepository,
                            StockAlertNotifier stockAlertNotifier) {
        this.productRepository = productRepository;
        this.saleRepository = saleRepository;
        this.stockAlertNotifier = stockAlertNotifier;
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public void sellProduct(Long productId, int quantity) {
        Product product = productRepository.findById(productId);
        if (product == null) {
            throw new ProductNotFoundException("Product not found: " + productId);
        }
        if (product.getQuantity() < quantity) {
            throw new LowStockException("Not enough stock for product " + productId);
        }

        // Update inventory
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.update(product);

        // Record sale
        Sale sale = new Sale();
        sale.setProductId(productId);
        sale.setQuantitySold(quantity);
        sale.setTotalPrice(product.getPrice() * quantity);
        sale.setSaleDate(LocalDateTime.now());
        saleRepository.save(sale);

        // Alert if under threshold
        if (product.getQuantity() < 10) {
            stockAlertNotifier.alertLowStock(product);
        }
    }

    // Additional methods for updating stock, removing products, etc.
}

