package com.ptts.routes;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.ptts.stops.StopLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

@Repository
public class RouteRepository {

    private static final Logger LOG = LoggerFactory.getLogger(RouteRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // ............................ Find a route by ID ............................//
    public Routes findRouteById(String sqlQuery, String routeId, String institutionId) {
        try {
            return jdbcTemplate.queryForObject(sqlQuery, new BeanPropertyRowMapper<>(Routes.class), routeId, institutionId);
        } catch (EmptyResultDataAccessException e) {
            LOG.error("Route not found with ID: {} and Institution ID: {}", routeId, institutionId, e);
            throw e;
        } catch (Exception e) {
            LOG.error("Error while fetching route with ID: {} and Institution ID: {}", routeId, institutionId, e);
            return null;
        }
    }
    
    
    
    
    


    // .................................... Find all routes ..................................//
    public List<Routes> findAllRoutes(String sqlQuery) {
        try {
            return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(Routes.class));
        } catch (Exception e) {
            LOG.error("Error fetching all routes", e);
            return null;
        }
    }

    // ............................ Create a new route ......................................//
    public int createRoute(String sqlQuery, Object[] routeData) {
        try {
            return jdbcTemplate.update(sqlQuery, routeData);
        } catch (Exception e) {
            LOG.error("Error while creating route", e);
            return 0;
        }
    }

    // ........................... Update a route ..........................................//
    public int updateRoute(String sqlQuery, Object[] updatedData) {
        try {
            return jdbcTemplate.update(sqlQuery, updatedData);
        } catch (Exception e) {
            LOG.error("Error while updating route", e);
            return 0;
        }
    }

    public int getNextRouteId(String sqlQuery) {
        try {
            return jdbcTemplate.queryForObject(sqlQuery, Integer.class);
        } catch (Exception e) {
            LOG.error("Error while fetching next Route ID", e);
            return 0;
        }
    }
    
    public int getNextStopId(String sqlQuery) {
        try {
            return jdbcTemplate.queryForObject(sqlQuery, Integer.class);
        } catch (Exception e) {
            LOG.error("Error fetching next stop ID", e);
            return -1; 
        }
    }
    
    // Method to get the next mapped ID
    public int getNextMappedId(String sqlQuery) {
        try {
            return jdbcTemplate.queryForObject(sqlQuery, Integer.class);
        } catch (Exception e) {
            LOG.error("Error while fetching next mapped ID", e);
            return 0;
        }
    }

    // ............................ Save Route-Driver-Vehicle Mapping ............................//
    public int saveRouteDriverVehicleMapping(RouteDriverVehicleMapping routeMapping) {
        String sqlQuery = "INSERT INTO maproutedrivervehicle (MVRD_ID, ROUTEID, DRIVERID, VEHICLEID, INSTITUTION_ID, EFFECTIVEDATE, EXPIRYDATE, CREATEDATE, LASTUPDATEDATE, ISACTIVE) " +
                          "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
       
        Object[] mappingData = {
            routeMapping.getMvrdId(),          
            routeMapping.getRouteId(),          
            routeMapping.getDriverId(),         
            routeMapping.getVehicleId(),        
            routeMapping.getInstitutionId(),     
            routeMapping.getEffectiveDate(),     
            routeMapping.getExpiryDate(),        
            new Date(),                         
            new Date(),                         
            routeMapping.getIsActive()   
            
        };

        try {
            return jdbcTemplate.update(sqlQuery, mappingData);
        } catch (Exception e) {
            LOG.error("Error while saving route-driver-vehicle mapping", e);
            return 0;
        }
    }

    
    ////////////////// deleteMapping////////////////////
    
    public int deleteMapping(String sqlQuery, Object[] params) {
        return jdbcTemplate.update(sqlQuery, params);
    }
    
    // ............................ Find mappings by Route ID ............................//
    public List<RouteDriverVehicleMapping> findMappingsByRouteId(String sqlQuery, String routeId) {
        try {
            return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(RouteDriverVehicleMapping.class), routeId);
        } catch (Exception e) {
            LOG.error("Error fetching mappings for route ID: {}", routeId, e);
            return null; // You may choose to return an empty list instead
        }
    }
    
    
 
    // ............................ Other existing methods ..........................

    public List<StopLocation> getStopsByRouteId(String sqlQuery, String routeId) {
        try {
            return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(StopLocation.class), routeId);
        } catch (Exception e) {
            LOG.error("Error fetching stops for route ID: {}", routeId, e);
            return null;
        }
    }
    

    public int deleteStopsByRouteId(String sqlQuery, String routeId, String institutionId) {
        try {
            return jdbcTemplate.update(sqlQuery, routeId, institutionId);
        } catch (Exception e) {
            LOG.error("Error while deleting stops with route ID: {} and institution ID: {}", routeId, institutionId, e);
            return 0;
        }
    }
    
   

    public int deleteRoute(String sqlQuery, String routeId, String institutionId) {
        try {
            return jdbcTemplate.update(sqlQuery, routeId, institutionId);
        } catch (Exception e) {
            LOG.error("Error while deleting route with ID: {} for institution ID: {}", routeId, institutionId, e);
            return 0;
        }
    }

    public StopLocation findStopById(String sqlQuery, String stopId) {
        try {
            return jdbcTemplate.queryForObject(sqlQuery, new BeanPropertyRowMapper<>(StopLocation.class), stopId);
        } catch (EmptyResultDataAccessException e) {
            LOG.error("Stop not found with ID: {}", stopId, e);
            throw e;
        } catch (Exception e) {
            LOG.error("Error while fetching stop with ID: {}", stopId, e);
            return null;
        }
    }

    public List<StopLocation> findAllStops(String sqlQuery) {
        try {
            return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(StopLocation.class));
        } catch (Exception e) {
            LOG.error("Error fetching all stops", e);
            return null;
        }
    }

    public int createStop(String sqlQuery, Object[] stopData) {
        try {
            return jdbcTemplate.update(sqlQuery, stopData);
        } catch (Exception e) {
            LOG.error("Error while creating stop", e);
            return 0;
        }
    }

    public int updateStop(String sqlQuery, Object[] updatedData) {
        try {
            return jdbcTemplate.update(sqlQuery, updatedData);
        } catch (Exception e) {
            LOG.error("Error while updating stop", e);
            return 0;
        }
    }

    public int deleteStop(String sqlQuery, String stopId) {
        try {
            return jdbcTemplate.update(sqlQuery, stopId);
        } catch (Exception e) {
            LOG.error("Error while deleting stop with ID: {}", stopId, e);
            return 0;
        }
    }

  


    // ........................... Route-Driver-Vehicle Mapping Methods ..........................//

    // Find all route-driver-vehicle mappings
    public List<RouteDriverVehicleMapping> findAllRouteDriverVehicleMappings(String sqlQuery) {
        try {
            return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(RouteDriverVehicleMapping.class));
        } catch (Exception e) {
            LOG.error("Error fetching all route-driver-vehicle mappings", e);
            return null;
        }
    }

    // Create a new route-driver-vehicle mapping
    public int createRouteDriverVehicleMapping(String sqlQuery, Object[] mappingData) {
        try {
            return jdbcTemplate.update(sqlQuery, mappingData);
        } catch (Exception e) {
            LOG.error("Error while creating route-driver-vehicle mapping", e);
            return 0;
        }
    }

    // Update a route-driver-vehicle mapping
    public int updateRouteDriverVehicleMapping(String sqlQuery, Object[] updatedData) {
        try {
            return jdbcTemplate.update(sqlQuery, updatedData);
        } catch (Exception e) {
            LOG.error("Error while updating route-driver-vehicle mapping", e);
            return 0;
        }
    }

    // Delete a route-driver-vehicle mapping by ID
    public int deleteRouteDriverVehicleMapping(String sqlQuery, String mappingId) {
        try {
            return jdbcTemplate.update(sqlQuery, mappingId);
        } catch (Exception e) {
            LOG.error("Error while deleting route-driver-vehicle mapping with ID: {}", mappingId, e);
            return 0;
        }
    }

    // Find a route-driver-vehicle mapping by ID
    public RouteDriverVehicleMapping findRouteDriverVehicleMappingById(String sqlQuery, String mappingId) {
        try {
            return jdbcTemplate.queryForObject(sqlQuery, new BeanPropertyRowMapper<>(RouteDriverVehicleMapping.class), mappingId);
        } catch (EmptyResultDataAccessException e) {
            LOG.error("Route-driver-vehicle mapping not found with ID: {}", mappingId, e);
            throw e;
        } catch (Exception e) {
            LOG.error("Error while fetching route-driver-vehicle mapping with ID: {}", mappingId, e);
            return null;
        }
    }
}

