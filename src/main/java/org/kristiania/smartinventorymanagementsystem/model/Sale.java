package org.kristiania.smartinventorymanagementsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "sale")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sale {
    // PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK referencing Product
    private Long productId;


    private int quantitySold;
    private double totalPrice;
    private LocalDateTime saleDate;

    private String customer;
    private double discount;
}
