package com.ptts.vehicle;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Repository
public class VehicleRepository {

    private static final Logger LOG = LoggerFactory.getLogger(VehicleRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // ............................ Find a vehicle by ID ............................//
    public Vehicle findVehicleById(String sqlQuery, String vehicleId,String institutionId) {
        Vehicle vehicle = null;
        try {
            vehicle = jdbcTemplate.queryForObject(sqlQuery, new BeanPropertyRowMapper<>(Vehicle.class), vehicleId,institutionId);
        } catch (EmptyResultDataAccessException e) {
            LOG.error("Vehicle not found with ID: " + vehicleId, e);
            throw new EmptyResultDataAccessException("Vehicle not found with ID: " + vehicleId, 1);
        }
        return vehicle;
    }

    // .................................... Find all vehicles ..................................//
    public List<Vehicle> findAllVehicles(String sqlQuery) {
        List<Vehicle> vehicles = null;
        try {
            vehicles = jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(Vehicle.class));
        } catch (Exception e) {
            LOG.error("Error fetching all vehicles", e);
        }
        return vehicles;
    }

 // ............................ Create a new vehicle ......................................//
    public int createVehicle(String sqlQuery, Object[] vehicleData) {
        try {
            return jdbcTemplate.update(sqlQuery, vehicleData);
        } catch (Exception e) {
            LOG.error("Error while creating vehicle", e);
            return 0;
        }
    }

    // ........................... Update a vehicle ..........................................//
    public int updateVehicle(String sqlQuery, Object[] updatedData) {
        try {
            return jdbcTemplate.update(sqlQuery, updatedData);
        } catch (Exception e) {
            LOG.error("Error while updating vehicle", e);
            return 0;
        }
    }

    // ........................... Delete a vehicle by ID ....................................//
    public int deleteVehicle(String sqlQuery, String vehicleId) {
        try {
            return jdbcTemplate.update(sqlQuery, vehicleId);
        } catch (Exception e) {
            LOG.error("Error while deleting vehicle with ID: " + vehicleId, e);
            return 0;
        }
    }
 // Method to get the next vehicle ID from the database
    public int getNextVehicleId(String sqlQuery) {
        try {
            // Use queryForObject to retrieve a single value
            return jdbcTemplate.queryForObject(sqlQuery, Integer.class);
        } catch (Exception e) {
            LOG.error("Error while retrieving next vehicle ID: ", e);
            return 0; 
        }
    }
}

