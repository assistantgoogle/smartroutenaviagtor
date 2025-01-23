package com.ptts.stops;

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
@RequestMapping("/api/stops")
public class StopsController {

    @Autowired
    private StopsServices stopsService;
    @Autowired
    AppUserController appUserInterface;

    
    // ............................ Get a stop by ID ............................//
    @GetMapping("/{stopId}")
    public ResponseEntity<StopLocation> getStopById( @PathVariable("stopId") String stopId, @Valid @RequestHeader("x-token-id") String tokenId) {
    	   appUserInterface.authorizeSysuser(tokenId);
    	   
        try {
            StopLocation stop = stopsService.getStopById(stopId);
            return new ResponseEntity<>(stop, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    
    // .................................... Get all stops ..................................//
    @GetMapping
    public ResponseEntity<List<StopLocation>> getAllStops() {
    	
    	
        try {
            List<StopLocation> stops = stopsService.getAllStops();
            return new ResponseEntity<>(stops, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    // ............................ Create a new stop ......................................//
    @PostMapping
    public ResponseEntity<String> createStop(@Valid @RequestBody StopLocation stop, @Valid @RequestHeader("x-token-id") String tokenId) {
    	  appUserInterface.authorizeSysuser(tokenId);
    	  
        try {
            int result = stopsService.createStop(stop);
            if (result > 0) {
                return new ResponseEntity<>("Stop created successfully", HttpStatus.CREATED);
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create stop");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    // ........................... Update a stop ..........................................//
    @PutMapping("/update/{stopId}")
    public ResponseEntity<String> updateStop(@PathVariable("stopId") String stopId, 
                                             @Valid @RequestBody StopLocation updatedStop, 
                                             @Valid @RequestHeader("x-token-id") String tokenId) {
    	  appUserInterface.authorizeSysuser(tokenId);
        try {
            int result = stopsService.updateStop(stopId, updatedStop);
            if (result > 0) {
                return new ResponseEntity<>("Stop updated successfully", HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update stop");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    // ........................... Delete a stop by ID ....................................//
    @DeleteMapping("/delete/{stopId}")
    public ResponseEntity<String> deleteStop(@PathVariable("stopId") String stopId) {
    	
        try {
            int result = stopsService.deleteStop(stopId);
            if (result > 0) {
                return new ResponseEntity<>("Stop deleted successfully", HttpStatus.NO_CONTENT);
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete stop");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    
    
}
