package org.kristiania.smartinventorymanagementsystem.util;

import org.kristiania.smartinventorymanagementsystem.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StockAlertNotifier {
    private static final Logger logger = LoggerFactory.getLogger(StockAlertNotifier.class);

    public void alertLowStock(Product product) {
        logger.warn("Low stock alert for product '{}' (ID={}): only {} left",
                product.getName(), product.getId(), product.getQuantity());
        // In real systems, you might send an email, push notification, etc.
    }
}

