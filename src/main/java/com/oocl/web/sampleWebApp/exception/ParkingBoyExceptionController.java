package com.oocl.web.sampleWebApp.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ParkingBoyExceptionController {
    @ExceptionHandler(value = DuplicateParkingBoyEmployeeID.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> exception(DuplicateParkingBoyEmployeeID exception) {
        return new ResponseEntity<String>("Product not found", HttpStatus.CONFLICT);
    }
}