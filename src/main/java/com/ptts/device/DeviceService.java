package com.ptts.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    private final String GET_NEXT_DEVICE_ID = "SELECT COALESCE(MAX(ID), 0) + 1 FROM devices";
    private final String findByIdQuery = "SELECT * FROM devices WHERE DEVICEID = ?";
    private final String findAllQuery = "SELECT * FROM devices";
    private final String createQuery = "INSERT INTO devices (DEVICEID, DEVICENAME, DEVICETYPE, ISACTIVE, DEVICEMODEL) VALUES (?, ?, ?, ?, ?)";
    private final String updateQuery = "UPDATE devices SET DEVICENAME = ?, DEVICETYPE = ?, ISACTIVE = ?, DEVICEMODEL = ? WHERE DEVICEID = ?";
    private final String deleteQuery = "DELETE FROM devices WHERE DEVICEID = ?";
    private final String findByVehicleIdQuery = "SELECT d.* FROM devices d JOIN mapdevicevehicle mdv ON d.DEVICEID = mdv.DEVICEID WHERE mdv.VEHICLEID = ? AND mdv.ISACTIVE = 1";
    private final String findMappingByIdQuery = "SELECT * FROM mapdevicevehicle WHERE DEVICEID = ? AND VEHICLEID = ? AND INSTITUTION_ID = ?";
    private final String findAllMappingsQuery = "SELECT * FROM mapdevicevehicle";
    private final String updateMappingQuery = "UPDATE mapdevicevehicle SET EFFECTIVEDATE = ?, EXPIRYDATE = ?, LASTUPDATEDATE = ?, ISACTIVE = ? WHERE DEVICEID = ? AND VEHICLEID = ? AND INSTITUTION_ID = ?";
    private final String deleteMappingQuery = "DELETE FROM mapdevicevehicle WHERE DEVICEID = ? AND VEHICLEID = ? AND INSTITUTION_ID = ?";

    // Get a device by ID
    public Device getDeviceById(String deviceId) {
        return deviceRepository.findDeviceById(findByIdQuery, deviceId);
    }

    // Get all devices
    public List<Device> getAllDevices() {
        return deviceRepository.findAllDevices(findAllQuery);
    }
    public Device getDeviceByVehicleId(String vehicleId) {
        // Fetch the device related to the vehicle
        return deviceRepository.findDeviceByVehicleId(findByVehicleIdQuery, vehicleId);
    }

    // Create or update a device
    public String saveOrUpdateDevice(Device device) {
        if (device.getDeviceId() == null || device.getDeviceId().isEmpty()) {
            int nextId = deviceRepository.getNextDeviceId(GET_NEXT_DEVICE_ID);
            String deviceId = "DID_" + nextId;
            device.setDeviceId(deviceId);
            int result = deviceRepository.createDevice(createQuery, device);
            return result > 0 ? "Device saved successfully" : "Failed to save device";
        } else {
            int result = deviceRepository.updateDevice(updateQuery, device);
            return result > 0 ? "Device updated successfully" : "Failed to update device";
        }
    }

    // Delete a device by ID
    public String deleteDeviceByDeviceId(String deviceId) {
        int result = deviceRepository.deleteDevice(deleteQuery, deviceId);
        return result > 0 ? "Device deleted successfully" : "Failed to delete device";
    }

    // Find a specific device-vehicle mapping
    public MapDeviceVehicle findMappingById(String deviceId, String vehicleId, String institutionId) {
        return deviceRepository.findMappingById(findMappingByIdQuery, deviceId, vehicleId, institutionId);
    }

    // Get all device-vehicle mappings
    public List<MapDeviceVehicle> getAllMappings() {
        return deviceRepository.findAllMappings(findAllMappingsQuery);
    }

    // Create a new device-vehicle mapping
    public String createMapping(MapDeviceVehicle mapDeviceVehicle) {
        int nextId = deviceRepository.getNextMappingId(GET_NEXT_DEVICE_ID);
        mapDeviceVehicle.setId(nextId);
        int result = deviceRepository.saveMapping(mapDeviceVehicle);
        return result > 0 ? "Mapping created successfully" : "Failed to create mapping";
    }

    // Update an existing device-vehicle mapping
    public String updateMapping(MapDeviceVehicle mapDeviceVehicle) {
        Object[] updatedData = {
            mapDeviceVehicle.getEffectiveDate(),
            mapDeviceVehicle.getExpiryDate(),
            new java.util.Date(), // lastUpdateDate
            mapDeviceVehicle.getIsActive(),
            mapDeviceVehicle.getDeviceId(),
            mapDeviceVehicle.getVehicleId(),
            mapDeviceVehicle.getInstitutionId()
        };
        int result = deviceRepository.updateMapping(updateMappingQuery, updatedData);
        return result > 0 ? "Mapping updated successfully" : "Failed to update mapping";
    }

    // Delete a device-vehicle mapping
    public String deleteMapping(String deviceId, String vehicleId, String institutionId) {
        Object[] params = {deviceId, vehicleId, institutionId};
        int result = deviceRepository.deleteMapping(deleteMappingQuery, params);
        return result > 0 ? "Mapping deleted successfully" : "Failed to delete mapping";
    }

    // Find mappings by device ID
    public List<MapDeviceVehicle> findMappingsByDeviceId(String deviceId) {
        return deviceRepository.findMappingsByDeviceId(findAllMappingsQuery, deviceId);
    }
}
