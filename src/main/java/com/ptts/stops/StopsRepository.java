package com.ptts.stops;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Repository
public class StopsRepository {

    private static final Logger LOG = LoggerFactory.getLogger(StopsRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // ............................ Find a stop by ID ............................//
    public StopLocation findStopById(String sqlQuery, String stopId) {
        StopLocation stop = null;
        try {
            stop = jdbcTemplate.queryForObject(sqlQuery, new BeanPropertyRowMapper<>(StopLocation.class), stopId);
        } catch (EmptyResultDataAccessException e) {
            LOG.error("Stop not found with ID: {}", stopId, e);
            throw new EmptyResultDataAccessException("Stop not found with ID: " + stopId, 1);
        }
        return stop;
    }

    // .................................... Find all stops ..................................//
    public List<StopLocation> findAllStops(String sqlQuery) {
        List<StopLocation> stops = null;
        try {
            stops = jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(StopLocation.class));
        } catch (Exception e) {
            LOG.error("Error fetching all stops", e);
        }
        return stops;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    // ............................ Find stops by Route ID ............................//
       public List<StopLocation> getStopsByRouteId(String sqlQuery, String routeId) {
           List<StopLocation> stops = null;
           try {
               stops = jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(StopLocation.class), routeId);
           } catch (Exception e) {
               LOG.error("Error fetching stops for route ID: " + routeId, e);
           }
           return stops;
       }

    // ............................ Create a new stop ......................................//
    public int createStop(String sqlQuery, Object[] stopData) {
        try {
            return jdbcTemplate.update(sqlQuery, stopData);
        } catch (Exception e) {
            LOG.error("Error while creating stop", e);
            return 0;
        }
    }

    // ........................... Update a stop ..........................................//
    public int updateStop(String sqlQuery, Object[] updatedData) {
        try {
            return jdbcTemplate.update(sqlQuery, updatedData);
        } catch (Exception e) {
            LOG.error("Error while updating stop", e);
            return 0;
        }
    }

    // ........................... Delete a stop by ID ....................................//
    public int deleteStop(String sqlQuery, String stopId) {
        try {
            return jdbcTemplate.update(sqlQuery, stopId);
        } catch (Exception e) {
            LOG.error("Error while deleting stop with ID: {}", stopId, e);
            return 0;
        }
    }

	public List<StopLocation> getStopsByRouteId(int routeId) {
		// TODO Auto-generated method stub
		return null;
	}
}
