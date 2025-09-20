package com.jordan.stage2.exception;

/**
 * Exception thrown when the ID in the path does not match the ID in the request body.
 * This is used to ensure that the correct Person is being updated.
 */
public class PersonIdAndIdMismatch extends RuntimeException {
    public PersonIdAndIdMismatch(String message) {
        super(message);
    }
}