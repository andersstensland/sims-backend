package org.kristiania.smartinventorymanagementsystem.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String contactDetails;

    private String address;
    private String supplierHistory;

    /**
     * Could also add more fields like:
     * - List<Product> products; // Products supplied by this supplier
     * - String paymentTerms; // e.g. "Net 30"
     * - etc.
     */
}
