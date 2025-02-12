package org.kristiania.smartinventorymanagementsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sale {
    private Long id;
    private Long productId;
    private int quantitySold;
    private double totalPrice;
    private LocalDateTime saleDate;
    // Could track customer, discount, etc.
}
