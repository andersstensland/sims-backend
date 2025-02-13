package org.kristiania.smartinventorymanagementsystem.model;

public class Admin extends AbstractUser {

    public Admin() {}

    public Admin(int id, String username, String email) {
        super(id, username, email);
    }

    // Admin-specific methods
    public void manageInventory() {
        // ...
    }
}
