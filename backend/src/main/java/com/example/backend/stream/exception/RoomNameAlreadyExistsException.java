package com.example.backend.stream.exception;

public class RoomNameAlreadyExistsException extends RuntimeException {
    public RoomNameAlreadyExistsException(String message) {
        super(message);
    }
}
