package com.ptts.tracking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VehicleTrackService {

    private final VehicleTrackRepository vehicleTrackRepository;
    private static final Logger logger = LoggerFactory.getLogger(VehicleTrackService.class);

    public VehicleTrackService(VehicleTrackRepository vehicleTrackRepository) {
        this.vehicleTrackRepository = vehicleTrackRepository;
    }
    public VehicleTrack addVehicleLocation(int deviceId, int vehicleId, String vehicleName, double latitude, double longitude, String datetime) {
        logger.info("Adding new location for vehicleId: {}", vehicleId);

        VehicleTrack vehicleTrack = vehicleTrackRepository.findByVehicleId(vehicleId);

        if (vehicleTrack == null) {
            logger.info("Vehicle not found, creating new record for vehicleId: {}", vehicleId);
            vehicleTrack = new VehicleTrack();
        }

        vehicleTrack.setDeviceId(deviceId);
        vehicleTrack.setVehicleId(vehicleId);
        vehicleTrack.setVehicleName(vehicleName);
        vehicleTrack.setLatitude(latitude);
        vehicleTrack.setLongitude(longitude);
        vehicleTrack.setDatetime(datetime);

        vehicleTrackRepository.save(vehicleTrack);
        return vehicleTrack;
    }

    public VehicleTrack getVehicleById(int vehicleId) {
        logger.info("Fetching latest location for vehicleId: {}", vehicleId);
        return vehicleTrackRepository.findByVehicleId(vehicleId);
        
    }
}
