package com.ptts.routes;

import com.ptts.stops.StopsRepository; // Import your stops repository
import com.ptts.stops.StopLocation; // Import StopLocation entity
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class RouteServices {

    private static final Logger LOG = LoggerFactory.getLogger(RouteServices.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private StopsRepository stopsRepository;
    
    private static final String DELETE_ROUTE_DRIVER_VEHICLE_MAPPING_QUERY = 
        "DELETE FROM maproutedrivervehicle WHERE ROUTEID = ? AND DRIVERID = ? AND VEHICLEID = ? AND INSTITUTION_ID=?";

    
  
    private static final String FIND_ROUTE_BY_ID_QUERY = "SELECT * FROM routes WHERE ROUTEID = ? AND INSTITUTION_ID = ?";
    private static final String FIND_ALL_ROUTES_QUERY = "SELECT * FROM routes";
    private static final String GET_NEXT_ROUTE_ID = "SELECT COALESCE(MAX(id), 0) + 1 FROM routes";
    private static final String CREATE_ROUTE_QUERY = "INSERT INTO routes (INSTITUTION_ID, ROUTEID, ROUTE_NAME, START_POINT, END_POINT, DISTANCE, ESTIMATED_TIME) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_ROUTE_QUERY = "UPDATE routes SET INSTITUTION_ID = ?, ROUTE_NAME = ?, START_POINT = ?, END_POINT = ?, DISTANCE = ?, ESTIMATED_TIME = ? WHERE ROUTEID = ?";
    private static final String DELETE_ROUTE_QUERY = "DELETE FROM routes WHERE ROUTEID = ? AND INSTITUTION_ID = ?";
    private static final String GET_NEXT_STOP_ID = "SELECT COALESCE(MAX(id), 0) + 1 FROM stoplocation";

    private static final String GET_NEXT_MAPPED_ID = "SELECT COALESCE(MAX(id), 0) + 1 FROM maproutedrivervehicle";
    // SQL queries for stops
    private static final String FIND_STOP_BY_ID_QUERY = "SELECT * FROM stoplocation WHERE STOPID = ?";
    private static final String FIND_ALL_STOPS_QUERY = "SELECT * FROM stoplocation";
    private static final String CREATE_STOP_QUERY = "INSERT INTO stoplocation (INSTITUTION_ID, ROUTEID, STOPID, STOPNAME, STOPLOCATION, ARRIVALTIME, DEPARTURETIME, ADDRESS, ADDRESS1, ADDRESS2, LATITUDE, LONGITUDE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_STOP_QUERY = "UPDATE stoplocation SET INSTITUTION_ID = ?, ROUTEID = ?, STOPNAME = ?, STOPLOCATION = ?, ARRIVALTIME = ?, DEPARTURETIME = ?, ADDRESS = ?, ADDRESS1 = ?, ADDRESS2 = ?, LATITUDE = ?, LONGITUDE = ? WHERE STOPID = ?";
    private static final String DELETE_STOP_QUERY = "DELETE FROM stoplocation WHERE STOPID = ?";
    private static final String FIND_STOPS_BY_ROUTE_ID_QUERY = "SELECT * FROM stoplocation WHERE ROUTEID = ?";
    private static final String DELETE_STOPS_BY_ROUTE_ID_QUERY = "DELETE FROM stoplocation WHERE ROUTEID = ? AND INSTITUTION_ID = ?";
    private static final String ROUTE_DRIVER_VEHICLE_MAP="SELECT * FROM maproutedrivervehicle WHERE ROUTEID = ?";
    		
    // Method to get stops by route ID
    public List<StopLocation> getStopsByRouteId(String routeId) {
        LOG.info("Fetching stops for route ID: {}", routeId);
        List<StopLocation> stops = stopsRepository.getStopsByRouteId(FIND_STOPS_BY_ROUTE_ID_QUERY, routeId);
        if (stops == null || stops.isEmpty()) {
            LOG.warn("No stops found for route ID: {}", routeId);
        }
        return stops;
    }

    public int deleteStopsByRouteId(String routeId, String institutionId) {
        LOG.info("Deleting stops for route ID: {} and institution ID: {}", routeId, institutionId);
        return routeRepository.deleteStopsByRouteId(DELETE_STOPS_BY_ROUTE_ID_QUERY, routeId, institutionId);
    }

    // Find a route by ID
    public Routes getRouteById(String routeId, String institutionId) {
        LOG.info("Fetching route with ID: {} and institution ID: {}", routeId, institutionId);
        return routeRepository.findRouteById(FIND_ROUTE_BY_ID_QUERY, routeId, institutionId);
    }
    

    // Find all routes
    public List<Routes> getAllRoutes() {
        LOG.info("Fetching all routes");
        List<Routes> routes = routeRepository.findAllRoutes(FIND_ALL_ROUTES_QUERY);
        if (routes == null || routes.isEmpty()) {
            LOG.warn("No routes found");
        }
        return routes;
    }
    
    
    public List<RouteDriverVehicleMapping> getDriverVehicleRoutesByInstitutionId(String institutionId) {
        LOG.info("Fetching all route-driver-vehicle mappings for institution ID: {}", institutionId);
        
        String query = "SELECT * FROM maproutedrivervehicle WHERE INSTITUTION_ID = ?";
        

        
        @SuppressWarnings("deprecation")
		List<RouteDriverVehicleMapping> driverVehicleRoutes = jdbcTemplate.query(
            query, 
            new Object[]{institutionId}, 
            new BeanPropertyRowMapper<>(RouteDriverVehicleMapping.class)
        );
        
        return driverVehicleRoutes;
    }

        


    
      

    // Create Route-Driver-Vehicle Mapping
    // Method to create Route-Driver-Vehicle Mapping
    @Transactional
    public int createRouteDriverVehicleMapping(RouteDriverVehicleMapping routeMapping) {
        LOG.info("Creating new mapping: Route ID: {}, Driver ID: {}, Vehicle ID: {}",
                 routeMapping.getRouteId(), routeMapping.getDriverId(), routeMapping.getVehicleId(),routeMapping.getInstitutionId());

       
        int nextMappedId = routeRepository.getNextMappedId(GET_NEXT_MAPPED_ID); 
        String mappedId = "RVD_" + nextMappedId; 
        routeMapping.setMvrdId(mappedId); 


        return routeRepository.saveRouteDriverVehicleMapping(routeMapping);
    }


    
    



    public List<RouteDriverVehicleMapping> getMappingsByRouteId(String routeId) {
        LOG.info("Fetching mappings for route ID: {}", routeId);
        List<RouteDriverVehicleMapping> mappings = routeRepository.findMappingsByRouteId(ROUTE_DRIVER_VEHICLE_MAP,routeId);
        if (mappings == null || mappings.isEmpty()) {
            LOG.warn("No mappings found for route ID: {}", routeId);
        }
        return mappings;
        
    }
    
   


    // Delete Route-Driver-Vehicle Mapping
    public int deleteRouteDriverVehicleMapping(String routeId, String driverId, String vehicleId) {
        LOG.info("Deleting mapping: Route ID: {}, Driver ID: {}, Vehicle ID: {}", routeId, driverId, vehicleId);
        int result = routeRepository.deleteMapping(DELETE_ROUTE_DRIVER_VEHICLE_MAPPING_QUERY, new Object[]{
            routeId, driverId, vehicleId
        });
        if (result > 0) {
            LOG.info("Mapping deleted successfully: Route ID: {}, Driver ID: {}, Vehicle ID: {}", routeId, driverId, vehicleId);
        } else {
            LOG.error("Failed to delete mapping: Route ID: {}, Driver ID: {}, Vehicle ID: {}", routeId, driverId, vehicleId);
        }
        return result;
    }

    
    
    // Create a new route
    public int createRoute(Routes route) {
        LOG.info("Creating new route: {}", route.getRouteName());

        int nextId = routeRepository.getNextRouteId(GET_NEXT_ROUTE_ID);
        String routId = "RID_" + nextId;
        route.setRouteId(routId);
        int result = routeRepository.createRoute(CREATE_ROUTE_QUERY, new Object[]{
            route.getInstitutionId(), // Added institutionId
            route.getRouteId(),
            route.getRouteName(),
            route.getStartPoint(),
            route.getEndPoint(),
            route.getDistance(),
            route.getEstimatedTime()
        });
        if (result > 0) {
            LOG.info("Route created successfully: {}", route.getRouteId());
        } else {
            LOG.error("Failed to create route: {}", route.getRouteId());
        }
        return result;
    }

    // Update a route
    public int updateRoute(String routeId, Routes updatedRoute) {
        LOG.info("Updating route with ID: {}", routeId);
        int result = routeRepository.updateRoute(UPDATE_ROUTE_QUERY, new Object[]{
            updatedRoute.getInstitutionId(), // Added institutionId
            updatedRoute.getRouteName(),
            updatedRoute.getStartPoint(),
            updatedRoute.getEndPoint(),
            updatedRoute.getDistance(),
            updatedRoute.getEstimatedTime(),
            routeId
        });
        if (result > 0) {
            LOG.info("Route updated successfully: {}", routeId);
        } else {
            LOG.error("Failed to update route with ID: {}", routeId);
        }
        return result;
    }

    // Delete a route by ID
    public int deleteRoute(String routeId, String institutionId) {
        LOG.info("Attempting to delete route with ID: {} and institution ID: {}", routeId, institutionId);

        // Delete related stops first
        int stopsDeleted = deleteStopsByRouteId(routeId, institutionId);
        LOG.info("Number of stops deleted for route ID {}: {}", routeId, stopsDeleted);

        // Then delete the route
        int result = routeRepository.deleteRoute(DELETE_ROUTE_QUERY, routeId, institutionId);
        if (result > 0) {
            LOG.info("Route deleted successfully: {}", routeId);
        } else {
            LOG.error("Failed to delete route: {}", routeId);
        }
        return result;
    }

    
    

    // ............................ Find a stop by ID ............................//
    public StopLocation getStopById(String stopId) {
        LOG.info("Fetching stop with ID: {}", stopId);
        return stopsRepository.findStopById(FIND_STOP_BY_ID_QUERY, stopId);
    }
    
    
    // .................................... Find all stops ..................................//
    public List<StopLocation> getAllStops() {
        LOG.info("Fetching all stops");
        List<StopLocation> stops = stopsRepository.findAllStops(FIND_ALL_STOPS_QUERY);
        if (stops == null || stops.isEmpty()) {
            LOG.warn("No stops found");
        }
        return stops;
    }



// ............................ Create a new stop ......................................//
public int createStop(StopLocation stop) {
    LOG.info("Creating new stop: {}", stop.getStopName());

    int result = stopsRepository.createStop(CREATE_STOP_QUERY, new Object[]{
        stop.getInstitutionId(), 
        stop.getRouteId(), // Make sure routeId is set
        stop.getStopId(),
        stop.getStopName(),
        stop.getStopLocation(),
        stop.getArrivalTime(),
        stop.getDepartureTime(),
        stop.getStopAddress(),
        stop.getStopAddress1(),
        stop.getStopAddress2(),
        stop.getLatitude(),
        stop.getLongitude()
    });
    if (result > 0) {
        LOG.info("Stop created successfully: {}", stop.getStopId());
    } else {
        LOG.error("Failed to create stop: {}", stop.getStopId());
    }
    return result;
}


public int saveStop(StopLocation stop, String routeId) {
    LOG.info("Saving stop: {}", stop.getStopName());

    // Generate a new stop ID
    int nextId = routeRepository.getNextStopId(GET_NEXT_STOP_ID);
    String stopId = "SID_" + nextId;
    stop.setStopId(stopId);
    stop.setRouteId(String.valueOf(routeId)); 
    

    // Create the stop using the createStop method, passing routeId
    int stopResult = createStop(stop); // Pass the updated stop with routeId

    // Check if the stop was saved successfully
    if (stopResult > 0) {
        LOG.info("Stop saved successfully: {}", stop.getStopId());
        return stopResult; // Return the result of saving the stop
    } else {
        LOG.error("Failed to save stop: {}", stop.getStopId());
        return 0; 
    }
}



// ........................... Update a stop ..........................................//
public int updateStop(String stopId, StopLocation updatedStop) {
    LOG.info("Updating stop with ID: {}", stopId);
    int result = stopsRepository.updateStop(UPDATE_STOP_QUERY, new Object[]{
        updatedStop.getInstitutionId(), // Added institutionId
        updatedStop.getRouteId(),       // Added routeId
        updatedStop.getStopName(),
        updatedStop.getStopLocation(),
        updatedStop.getArrivalTime(),
        updatedStop.getDepartureTime(),
       
        updatedStop.getStopAddress(),
        updatedStop.getStopAddress1(),
        updatedStop.getStopAddress2(),
        updatedStop.getLatitude(),
        updatedStop.getLongitude(),
        stopId
    });
    if (result > 0) {
        LOG.info("Stop updated successfully: {}", stopId);
    } else {
        LOG.error("Failed to update stop with ID: {}", stopId);
    }
    return result;
}

// ........................... Delete a stop by ID ....................................//
public int deleteStop(String stopId) {
    LOG.info("Deleting stop with ID: {}", stopId);
    int result = stopsRepository.deleteStop(DELETE_STOP_QUERY, stopId);
    if (result > 0) {
        LOG.info("Stop deleted successfully: {}", stopId);
    } else {
        LOG.error("Failed to delete stop with ID: {}", stopId);
    }
    return result;
    
    
}
}















