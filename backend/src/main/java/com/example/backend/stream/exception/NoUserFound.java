package com.example.backend.stream.exception;

public class NoUserFound extends RuntimeException {
    public NoUserFound(Long id) {
        super("No user found with id " + id);
    }
}
