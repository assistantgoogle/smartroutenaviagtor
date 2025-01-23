package com.ptts.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.dao.EmptyResultDataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TrackingRepository {

    private static final Logger LOG = LoggerFactory.getLogger(TrackingRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Save location data into the database
    public int saveLocation(Location location) {
        String sql = "INSERT INTO Location (MACHINEID, LAT, LNG, TIMESTAMP) VALUES (?, ?, ?, ?)";
        try {
            return jdbcTemplate.update(sql, location.getMachineId(), location.getLat(), location.getLng(), location.getTimestamp());
        } catch (Exception e) {
            LOG.error("Error while saving location for Machine ID: " + location.getMachineId(), e);
            return 0;
        }
    }

    // Retrieve all location records for a specific machineId
    public List<Location> findByMachineId(String machineId) {
        String sql = "SELECT * FROM Location WHERE MACHINEID = ?";
        try {
            return jdbcTemplate.query(sql, this::mapRowToLocation, machineId);
        } catch (EmptyResultDataAccessException e) {
            LOG.error("No locations found for Machine ID: " + machineId, e);
            return null; 
        } catch (Exception e) {
            LOG.error("Error while retrieving locations for Machine ID: " + machineId, e);
            return null;
        }
    }
    
    private Location mapRowToLocation(ResultSet rs, int rowNum) throws SQLException {
        Location location = new Location();
        location.setMachineId(rs.getString("machineId"));
        location.setLat(rs.getDouble("lat"));
        location.setLng(rs.getDouble("lng"));
        location.setTimestamp(rs.getTimestamp("timestamp"));
        return location;
    }
}
