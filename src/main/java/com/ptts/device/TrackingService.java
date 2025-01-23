package com.ptts.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackingService {
	 @Autowired
    private final TrackingRepository trackingRepository;

   
    public TrackingService(TrackingRepository trackingRepository) {
        this.trackingRepository = trackingRepository;
    }
    

    public String saveLocation(Location location) {
        if (location.getMachineId() == null || location.getMachineId().isEmpty()) {
            return "Machine ID cannot be null or empty";
        }

        int rowsAffected = trackingRepository.saveLocation(location);
        if (rowsAffected > 0) {
            return "Location saved successfully for Machine ID: " + location.getMachineId();
        }
        return "Failed to save location for Machine ID: " + location.getMachineId();
    }
    
    

    public List<Location> getLocationsByMachineId(String machineId) {
        return trackingRepository.findByMachineId(machineId);
        
    }
}
