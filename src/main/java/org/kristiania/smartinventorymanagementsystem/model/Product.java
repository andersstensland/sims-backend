package org.kristiania.smartinventorymanagementsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private int id;
    private String name;
    private int quantity;        // Current stock level
    private double price;
    private int supplierId;     // FK referencing Supplier

    // Additional fields like category, description, etc.
}

