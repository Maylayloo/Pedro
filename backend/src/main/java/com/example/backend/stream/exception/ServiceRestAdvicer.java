package com.example.backend.stream.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ServiceRestAdvicer {

    @ExceptionHandler(NoUserFound.class)
    public ResponseEntity<String> handleNotUserFound(NoUserFound ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoomNameAlreadyExistsException.class)
    public ResponseEntity<String> handleRoomNameAlreadyExists(RoomNameAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}
