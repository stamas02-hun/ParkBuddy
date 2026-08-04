package org.example.parkbuddy.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j(topic = "fileLogger")
public class GlobalExceptionHandler {

    @ExceptionHandler(ParkingSpaceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ResponseEntity<?> handleParkingSpaceNotFoundException(ParkingSpaceNotFoundException ex, HttpServletRequest request) {
        String errorMessage = String.format("@%s - %s", request.getRequestURI(), ex.getMessage());
        log.error(errorMessage);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReservationConflictException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<?> handleReservationConflictException(ReservationConflictException ex, HttpServletRequest request) {
        String errorMessage = String.format("@%s - %s", request.getRequestURI(), ex.getMessage());
        log.error(errorMessage);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
