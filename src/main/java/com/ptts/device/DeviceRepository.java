package com.ptts.device;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Repository
public class DeviceRepository {
    
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(DeviceRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Find a device by ID
    public Device findDeviceById(String sqlQuery, String deviceId) {
        try {
            return jdbcTemplate.queryForObject(sqlQuery, new BeanPropertyRowMapper<>(Device.class), deviceId);
        } catch (Exception e) {
            return null;
        }
    }

    // Get all devices
    public List<Device> findAllDevices(String sqlQuery) {
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(Device.class));
    }

    // Get a device by Vehicle ID
    public Device findDeviceByVehicleId(String sqlQuery, String vehicleId) {
        try {
            return jdbcTemplate.queryForObject(sqlQuery, new BeanPropertyRowMapper<>(Device.class), vehicleId);
        } catch (Exception e) {
            LOG.error("Error while fetching device by Vehicle ID: {}", vehicleId, e);
            return null;
        }
    }

    // Create a new device
    public int createDevice(String sqlQuery, Device device) {
        return jdbcTemplate.update(sqlQuery, device.getDeviceId(), device.getDeviceName(), device.getDeviceType(),
                device.isActive(), device.getDeviceModel());
    }

    // Update a device
    public int updateDevice(String sqlQuery, Device device) {
        return jdbcTemplate.update(sqlQuery, device.getDeviceName(), device.getDeviceType(), device.isActive(),
                device.getDeviceModel(), device.getDeviceId());
    }

    // Delete a device
    public int deleteDevice(String sqlQuery, String deviceId) {
        return jdbcTemplate.update(sqlQuery, deviceId);
    }

    // Get the next device ID
    public int getNextDeviceId(String sqlQuery) {
        return jdbcTemplate.queryForObject(sqlQuery, Integer.class);
    }

    // Find a device-vehicle mapping by ID
    public MapDeviceVehicle findMappingById(String sqlQuery, String deviceId, String vehicleId, String institutionId) {
        try {
            return jdbcTemplate.queryForObject(sqlQuery, new BeanPropertyRowMapper<>(MapDeviceVehicle.class), deviceId, vehicleId, institutionId);
        } catch (Exception e) {
            LOG.error("Error while fetching device-vehicle mapping with Device ID: {}, Vehicle ID: {} and Institution ID: {}", deviceId, vehicleId, institutionId, e);
            return null;
        }
    }

    // Find all device-vehicle mappings
    public List<MapDeviceVehicle> findAllMappings(String sqlQuery) {
        try {
            return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(MapDeviceVehicle.class));
        } catch (Exception e) {
            LOG.error("Error while fetching all device-vehicle mappings", e);
            return null;
        }
    }

    // Create a new device-vehicle mapping
    public int createMapping(String sqlQuery, Object[] mappingData) {
        try {
            return jdbcTemplate.update(sqlQuery, mappingData);
        } catch (Exception e) {
            LOG.error("Error while creating device-vehicle mapping", e);
            return 0;
        }
    }

    // Update a device-vehicle mapping
    public int updateMapping(String sqlQuery, Object[] updatedData) {
        try {
            return jdbcTemplate.update(sqlQuery, updatedData);
        } catch (Exception e) {
            LOG.error("Error while updating device-vehicle mapping", e);
            return 0;
        }
    }

    // Delete a device-vehicle mapping
    public int deleteMapping(String sqlQuery, Object[] params) {
        try {
            return jdbcTemplate.update(sqlQuery, params);
        } catch (Exception e) {
            LOG.error("Error while deleting device-vehicle mapping", e);
            return 0;
        }
    }

    // Find mappings by device ID
    public List<MapDeviceVehicle> findMappingsByDeviceId(String sqlQuery, String deviceId) {
        try {
            return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(MapDeviceVehicle.class), deviceId);
        } catch (Exception e) {
            LOG.error("Error while fetching mappings for Device ID: {}", deviceId, e);
            return null;
        }
    }

    // Get the next available ID for the mapping
    public int getNextMappingId(String sqlQuery) {
        try {
            return jdbcTemplate.queryForObject(sqlQuery, Integer.class);
        } catch (Exception e) {
            LOG.error("Error while fetching next available mapping ID", e);
            return 0;
        }
    }

    // Save device-vehicle mapping
    public int saveMapping(MapDeviceVehicle mapDeviceVehicle) {
        String sqlQuery = "INSERT INTO mapdevicevehicle (DEVICEID, VEHICLEID, INSTITUTION_ID, EFFECTIVEDATE, EXPIRYDATE, CREATEDATE, LASTUPDATEDATE, ISACTIVE) " +
                          "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        Object[] mappingData = {
            mapDeviceVehicle.getId(),
            mapDeviceVehicle.getDeviceId(),
            mapDeviceVehicle.getVehicleId(),
            mapDeviceVehicle.getInstitutionId(),
            mapDeviceVehicle.getEffectiveDate(),
            mapDeviceVehicle.getExpiryDate(),
            new Date(),  // createDate
            new Date(),  // lastUpdateDate
            mapDeviceVehicle.getIsActive()
        };

        try {
            return jdbcTemplate.update(sqlQuery, mappingData);
        } catch (Exception e) {
            LOG.error("Error while saving device-vehicle mapping", e);
            return 0;
        }
    }
}
