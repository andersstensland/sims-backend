package org.kristiania.smartinventorymanagementsystem.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {
    private Long id;
    private String name;
    private String contactDetails;
    // Could also track supply history, address, etc.
}
