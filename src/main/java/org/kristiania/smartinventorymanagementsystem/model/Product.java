package org.kristiania.smartinventorymanagementsystem.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long id;
    private String name;
    private int quantity;        // Current stock level
    private double price;
    private Long supplierId;     // FK referencing Supplier

    // Additional fields like category, description, etc.
}

