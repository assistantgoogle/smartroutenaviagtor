package com.ptts.vehicle;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class VehicleGlobalExceptionHandler {
@ExceptionHandler(VehicleNotFoundException.class)
public ResponseEntity<String> handleVehicleNotFoundException(VehicleNotFoundException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
}

@ExceptionHandler(Exception.class)
public ResponseEntity<String> handleGenericException(Exception ex) {
    return new ResponseEntity<>("Internal server error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
}

	
	

}
