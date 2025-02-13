package org.kristiania.smartinventorymanagementsystem.model;

public class Customer extends AbstractUser {
    public Customer() {}

    public Customer(int id, String username, String email) {
        super(id, username, email);
    }

    // Customer-specific methods
    public void purchaseProduct() {
        // ...
    }
}
