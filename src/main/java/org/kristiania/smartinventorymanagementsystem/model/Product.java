package org.kristiania.smartinventorymanagementsystem.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int quantity;        // Current stock level
    private double price;

    private Long supplierId;     // FK referencing Supplier
    private String description;
    private String category;

    /**
     * Could also add more fields like:
     * - String imageUrl; // URL to the product image
     * - String brand; // e.g. "Nike"
     * - etc
     * - List<Sale> sales; // Sales of this product
     * - List<Stock> stock; // Stock levels of this product
     * - etc.
     */
}

