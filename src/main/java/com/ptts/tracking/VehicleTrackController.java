package com.ptts.tracking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api/vehicles")
public class VehicleTrackController {

    private static final Logger LOG = LoggerFactory.getLogger(VehicleTrackController.class);
    
    @Autowired
    private VehicleTrackService vehicleTrackService;
    

    @PostMapping("/add-location")
    public ResponseEntity<?> addVehicleLocation(@RequestBody VehicleTrack locationRequest) {
        try {
            LOG.info("Received request to add location for Vehicle ID: {}", locationRequest.getVehicleId());

            VehicleTrack vehicleTrack = vehicleTrackService.addVehicleLocation(
                    locationRequest.getDeviceId(),
                    locationRequest.getVehicleId(),
                    locationRequest.getVehicleName(),
                    locationRequest.getLatitude(),
                    locationRequest.getLongitude(),
                    locationRequest.getDatetime()
            );

            return ResponseEntity.ok(vehicleTrack);
        } catch (Exception e) {
            LOG.error("Error adding vehicle location", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to add vehicle location", e);
        }
    }

    @GetMapping("/getlocation")
    public ResponseEntity<?> getVehicleLocation(@RequestParam("vehicleId") int vehicleId) {
        try {
            LOG.info("Fetching latest location for Vehicle ID: {}", vehicleId);
            VehicleTrack vehicleTrack = vehicleTrackService.getVehicleById(vehicleId);

            if (vehicleTrack == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found");
            }
            
            

            return ResponseEntity.ok(vehicleTrack);
        } catch (Exception e) {
            LOG.error("Error fetching vehicle location", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch vehicle location", e);
        }
    }

}
