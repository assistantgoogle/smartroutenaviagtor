package com.ptts.driver;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Repository
public class DriverRepository {

    private static final Logger LOG = LoggerFactory.getLogger(DriverRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Find a driver by ID
    public Driver findDriverById(String sqlQuery, String driverId, String institutionId) {
        Driver driver = null;
        try {
            driver = jdbcTemplate.queryForObject(sqlQuery, new BeanPropertyRowMapper<>(Driver.class), driverId, institutionId);
        } catch (EmptyResultDataAccessException e) {
            LOG.error("Driver not found with ID: {} for institutionId: {}", driverId, institutionId, e);
            // Handle the exception, but don't throw it
        }
        return driver; // Return null if no driver is found
    }

    // Find all drivers
    public List<Driver> findAllDrivers(String sqlQuery) {
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(Driver.class));
    }

    // Create a new driver
    public int createDriver(String sqlQuery, Object[] driverData) {
        return jdbcTemplate.update(sqlQuery, driverData);
    }

    // Update an existing driver
    public int updateDriver(String sqlQuery, Object[] updatedData) {
        return jdbcTemplate.update(sqlQuery, updatedData);
    }

    // Delete a driver by ID
    public int deleteDriver(String sqlQuery, String driverId) {
        return jdbcTemplate.update(sqlQuery, driverId);
    }

    // Get the next available driver ID
    public int getNextDriverId(String sqlQuery) {
        return jdbcTemplate.queryForObject(sqlQuery, Integer.class);
    }
}
