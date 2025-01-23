package com.ptts.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tracking")
public class TrackingController {

    @Autowired
    private final TrackingService trackingService;

    public TrackingController(TrackingService trackingService) {
        this.trackingService = trackingService;
    }
    
    @PostMapping("/loadlocation")
    public ResponseEntity<Map<String, Object>> loadLocation(
            @RequestParam("machineId") String machineId,
            @RequestParam("lat") double lat,
            @RequestParam("lng") double lng) {
        try {
            Location location = new Location(machineId, lat, lng, new Date());
            String result = trackingService.saveLocation(location);

            Map<String, Object> response = new HashMap<>();
            if (result.contains("success")) {
                response.put("status", "success");
                response.put("message", result);
                response.put("lat", lat);
                response.put("lng", lng);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to save location: " + result);
            }
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", ex);
        }
    }
    @GetMapping("/getlocation")
    public ResponseEntity<?> getLocationByMachineId(@RequestParam("machineId") String machineId) {
    	 System.out.println("Received request with machineId: " + machineId);   
    	try {
            List<Location> locations = trackingService.getLocationsByMachineId(machineId);
            if (locations.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No locations found for Machine ID: " + machineId);
            }
            return new ResponseEntity<>(locations, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", ex);
        }
    }
}
