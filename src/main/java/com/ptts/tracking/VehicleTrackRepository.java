package com.ptts.tracking;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleTrackRepository {

    private final JdbcTemplate jdbcTemplate;

    public VehicleTrackRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Save a new vehicle track
    public int save(VehicleTrack vehicle) {
        String sql = "INSERT INTO vehicletrack (DEVICEID, VEHICLEID, VEHICLENAME, LATITUDE, LONGITUDE, DATETIME) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, 
                vehicle.getDeviceId(), 
                vehicle.getVehicleId(), 
                vehicle.getVehicleName(), 
                vehicle.getLatitude(), 
                vehicle.getLongitude(), 
                vehicle.getDatetime());
    }

    // Update an existing vehicle track
    public int updateVehicleLocation(int deviceId, int vehicleId, String vehicleName, double latitude, double longitude, String datetime) {
        String sql = "UPDATE vehicletrack SET DeviceID = ?, VEHICLENAME = ?, LATITUDE = ?, LONGITUDE = ?, DATETIME = ? " +
                     "WHERE VEHICLEID = ?";
        return jdbcTemplate.update(sql, 
                deviceId, 
                vehicleId,
                vehicleName, 
                latitude, 
                longitude, 
                datetime
              );
    }

    // Find a vehicle track by vehicle ID
    public VehicleTrack findByVehicleId(int vehicleId) {
        String sql = "SELECT * FROM vehicletrack WHERE VEHICLEID = ?";
        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<>(VehicleTrack.class),
                    vehicleId
            );
        } catch (EmptyResultDataAccessException e) {
            return null; // Return null if no record is found
        }
    }
}
