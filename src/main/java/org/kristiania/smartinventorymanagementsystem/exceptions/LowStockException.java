package org.kristiania.smartinventorymanagementsystem.exceptions;


public class LowStockException extends RuntimeException {
    public LowStockException(String message) {
        super(message);
    }
}
