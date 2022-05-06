package com.bohdan.customerrest.exception;

public class CustomerDeletedException extends RuntimeException{
    public CustomerDeletedException() {
        super("Customer is deleted");
    }

    public CustomerDeletedException(String message) {
        super(message);
    }

    public CustomerDeletedException(Long id) {
        super("Customer with id " + id + " is deleted");
    }
}
