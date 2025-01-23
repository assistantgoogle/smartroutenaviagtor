package com.ptts.stops;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class StopsServices {

    private static final Logger LOG = LoggerFactory.getLogger(StopsServices.class);

    @Autowired
    private StopsRepository stopsRepository;
 // SQL queries (You can also externalize them to a properties file if needed)
    private static final String FIND_STOP_BY_ID_QUERY = "SELECT * FROM stoplocation WHERE STOPID = ?";
    private static final String FIND_ALL_STOPS_QUERY = "SELECT * FROM stoplocation";
    private static final String CREATE_STOP_QUERY = "INSERT INTO stoplocation ( ROUTEID,INSTITUTION_ID,STOPID, STOPNAME, STOPLOCATION, ARRIVALTIME, DEPARTURETIME, STOPADDRESS, STOPADDRESS1, STOPADDRESS2, LATITUDE, LONGITUDE) VALUES (?, ?,  ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
    
    private static final String UPDATE_STOP_QUERY = "UPDATE stoplocation SET ROUTEID = ?, INSTITUTION_ID = ? STOPNAME = ?, STOPLOCATION = ?, ARRIVALTIME = ?, DEPARTURETIME = ?, STOPADDRESS = ?, STOPADDRESS1 = ?, STOPADDRESS2 = ?, LATITUDE = ?, LONGITUDE = ? WHERE STOPID = ?";
    
    private static final String DELETE_STOP_QUERY = "DELETE FROM stoplocation WHERE STOPID = ?";

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
        		stop.getRouteId(),
        		stop.getInstitutionId(),
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
    
    
    
    public int saveStop(StopLocation stop) {
        LOG.info("Saving stop: {}", stop.getStopName());

        // Call the repository to save the stop
        int result = stopsRepository.createStop(CREATE_STOP_QUERY, new Object[]{
        		stop.getRouteId(),
        		stop.getInstitutionId(),
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

        // Check the result and log success or failure
        if (result > 0) {
            LOG.info("Stop saved successfully: {}", stop.getStopId());
        } else {
            LOG.error("Failed to save stop: {}", stop.getStopId());
        }
        return result;
    }


    // ........................... Update a stop ..........................................//
    public int updateStop(String stopId, StopLocation updatedStop) {
        LOG.info("Updating stop with ID: {}", stopId);
        int result = stopsRepository.updateStop(UPDATE_STOP_QUERY, new Object[]{
        		updatedStop.getRouteId(),
        		updatedStop.getInstitutionId(),

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
