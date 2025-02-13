package org.kristiania.smartinventorymanagementsystem.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;  // hashed

    private String roles; // e.g. "ROLE_USER,ROLE_ADMIN"

    /**
     * Could also add more fields like:
     * - String email;
     * - String firstName;
     * - String lastName;
     * - etc.
     */
}

