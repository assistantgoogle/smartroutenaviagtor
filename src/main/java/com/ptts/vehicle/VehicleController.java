package com.ptts.vehicle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.ptts.appuser.AppUserController;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;
    @Autowired
    AppUserController appUserInterface;
    // ............................ Get a vehicle by ID ............................//
    @GetMapping("/{vehicleId}")
    public ResponseEntity<Vehicle> getVehicleById(@Valid @PathVariable("vehicleId") String vehicleId,String institutionId,
    												@Valid @RequestHeader("x-token-id") String tokenId) {
    	   appUserInterface.authorizeSysuser(tokenId);
        try {
            Vehicle vehicle = vehicleService.getVehicleById(vehicleId,institutionId);
            return new ResponseEntity<>(vehicle, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        
    }
    
    // .......................... Get all vehicles .......................... //
    @GetMapping("/all")
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
    	
    	
        try {
            List<Vehicle> vehicles = vehicleService.getAllVehicles();
            return new ResponseEntity<>(vehicles, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    

    // ............................ Create a new vehicle .......................... //
    @PostMapping("/add")
    public ResponseEntity<String> createVehicle(@Valid @RequestBody Vehicle vehicle,
    											 @Valid @RequestHeader("x-token-id") String tokenId) {
    	   appUserInterface.authorizeSysuser(tokenId);
        try {
            if (vehicle.getInstitutionId() == null || vehicle.getInstitutionId().isEmpty()) {
                return new ResponseEntity<>("Institution ID cannot be blank", HttpStatus.BAD_REQUEST);
            }

            int result = vehicleService.createVehicle(vehicle);
            if (result > 0) {
                return new ResponseEntity<>("Vehicle created successfully", HttpStatus.CREATED);
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create vehicle");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    // ............................ Update a vehicle .......................... //
    @PutMapping("/update/{vehicleId}")
    public ResponseEntity<String> updateVehicle(@PathVariable("vehicleId") String vehicleId,
    											@Valid @RequestBody Vehicle updatedVehicle,
    											@Valid @RequestHeader("x-token-id") String tokenId) {
    	   appUserInterface.authorizeSysuser(tokenId);
        try {
            if (updatedVehicle.getInstitutionId() == null || updatedVehicle.getInstitutionId().isEmpty()) {
                return new ResponseEntity<>("Institution ID cannot be blank", HttpStatus.BAD_REQUEST);
            }

            int result = vehicleService.updateVehicle(vehicleId, updatedVehicle);
            if (result > 0) {
                return new ResponseEntity<>("Vehicle updated successfully", HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update vehicle");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    // ............................ Delete a vehicle .......................... //
    @DeleteMapping("/delete/{vehicleId}")
    public ResponseEntity<String> deleteVehicle(@PathVariable("vehicleId") String vehicleId) {
    	
        try {
            int result = vehicleService.deleteVehicle(vehicleId);
            if (result > 0) {
                return new ResponseEntity<>("Vehicle deleted successfully", HttpStatus.NO_CONTENT);
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete vehicle");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        
    }
}
