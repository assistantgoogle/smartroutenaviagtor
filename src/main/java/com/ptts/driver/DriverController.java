package com.ptts.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.ptts.appuser.AppUserController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.Valid;
import jakarta.validation.ConstraintViolationException;
import java.util.List;

@RestController
@RequestMapping("/api/drivers")
@Validated
public class DriverController {

    private static final Logger LOG = LoggerFactory.getLogger(DriverController.class);

    @Autowired
    private DriverService driverService;

    @Autowired
    AppUserController appUserInterface;

  
    @GetMapping("/{driverId}")
    public ResponseEntity<?> getDriverById(@PathVariable("driverId") String driverId,String institutionId, @Valid @RequestHeader("x-token-id") String tokenId) {
        LOG.info("Received request to fetch driver with ID: {}", driverId);
        appUserInterface.authorizeSysuser(tokenId);
        try {
            Driver driver = driverService.getDriverById(driverId,institutionId);
            if (driver != null) {
                return new ResponseEntity<>(driver, HttpStatus.OK);
            } else {
                LOG.error("Driver not found with ID: {}", driverId);
                return new ResponseEntity<>("Driver not found", HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            LOG.error("Invalid driver ID format: {}", driverId, e);
            return new ResponseEntity<>("Invalid driver ID format", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOG.error("Error fetching driver with ID: {}", driverId, e);
            return new ResponseEntity<>("Internal server error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            
        }
    }

    // Get all drivers
    @GetMapping
    public ResponseEntity<?> getAllDrivers() {
        LOG.info("Received request to fetch all drivers");
        
        
        try {
            List<Driver> drivers = driverService.getAllDrivers();
            return new ResponseEntity<>(drivers, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Error fetching all drivers", e);
            return new ResponseEntity<>("Internal server error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Create a new driver
    @PostMapping("/add")
    public ResponseEntity<?> createDriver(@Valid @RequestBody Driver driver, @Valid @RequestHeader("x-token-id") String tokenId) {
        LOG.info("Received request to create driver");
        appUserInterface.authorizeSysuser(tokenId);
        try {
            driverService.createDriver(driver);
            LOG.info("Successfully created driver with ID: {}", driver.getDriverId());
            appUserInterface.authorizeSysuser(tokenId);
            return new ResponseEntity<>("Driver created successfully", HttpStatus.CREATED);
        } catch (ConstraintViolationException e) {
            LOG.error("Validation error creating driver: {}", e.getMessage());
            return new ResponseEntity<>("Validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            LOG.error("Invalid input data: {}", e.getMessage(), e);
            return new ResponseEntity<>("Invalid data provided: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOG.error("Error creating driver", e);
            return new ResponseEntity<>("Internal server error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    // Update an existing driver
    @PutMapping("/update/{driverId}")
    
    public ResponseEntity<?> updateDriver(@PathVariable("driverId") String driverId, @Valid @RequestBody Driver driver, @Valid @RequestHeader("x-token-id") String tokenId) {
        LOG.info("Received request to update driver with ID: {}", driverId);
       
        appUserInterface.authorizeSysuser(tokenId);
        
        try {
            if (!driverId.equals(driver.getDriverId())) {
                LOG.error("Driver ID in path and body do not match");
                return new ResponseEntity<>("Driver ID mismatch", HttpStatus.BAD_REQUEST);
            }
            int result = driverService.updateDriver(driver);
            if (result > 0) {
                LOG.info("Successfully updated driver with ID: {}", driverId);
                return new ResponseEntity<>("Driver updated successfully", HttpStatus.OK);
            } else {
                LOG.error("Failed to update driver with ID: {}", driverId);
                return new ResponseEntity<>("Driver update failed", HttpStatus.NOT_FOUND);
            }
        } catch (ConstraintViolationException e) {
            LOG.error("Validation error updating driver: {}", e.getMessage());
            return new ResponseEntity<>("Validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            LOG.error("Invalid input data: {}", e.getMessage(), e);
            return new ResponseEntity<>("Invalid data provided: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOG.error("Error updating driver with ID: {}", driverId, e);
            return new ResponseEntity<>("Internal server error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete a driver
    @DeleteMapping("/del/{driverId}")
    public ResponseEntity<?> deleteDriver(@PathVariable("driverId") String driverId) {
        LOG.info("Received request to delete driver with ID: {}", driverId);
        try {
            int result = driverService.deleteDriver(driverId);
            if (result > 0) {
                LOG.info("Successfully deleted driver with ID: {}", driverId);
                return new ResponseEntity<>("Driver deleted successfully", HttpStatus.OK);
            } else {
                LOG.error("Driver not found with ID: {}", driverId);
                return new ResponseEntity<>("Driver not found", HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            LOG.error("Invalid driver ID format: {}", driverId, e);
            return new ResponseEntity<>("Invalid driver ID format", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOG.error("Error deleting driver with ID: {}", driverId, e);
            return new ResponseEntity<>("Internal server error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
