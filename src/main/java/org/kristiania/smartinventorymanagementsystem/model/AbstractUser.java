package org.kristiania.smartinventorymanagementsystem.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class AbstractUser {
    // Getters and setters
    private int id;
    private String username;
    private String email;

    public AbstractUser() {}

    public AbstractUser(int id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    // Possibly add common methods like authenticate(), etc.

}
