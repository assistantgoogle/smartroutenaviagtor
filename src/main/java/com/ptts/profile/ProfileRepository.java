package com.ptts.profile;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

@Repository
public class ProfileRepository {

    private static final Logger LOG = LoggerFactory.getLogger(ProfileRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Profile-related methods

    // Find a profile by ID
    public Profile findProfileById(String sqlQuery, String profileId) {
        try {
            return jdbcTemplate.queryForObject(sqlQuery, new BeanPropertyRowMapper<>(Profile.class), profileId);
        } catch (EmptyResultDataAccessException e) {
            LOG.error("Profile not found with ID: " + profileId, e);
            throw e;  // Let the service layer handle the exception if needed
        }
    }

    public int getNextProfileRouteId(String sqlQuery) {
        try {
            return jdbcTemplate.queryForObject(sqlQuery, Integer.class);
        } catch (Exception e) {
            LOG.error("Error fetching next profile route ID", e);
            return -1;  // Indicate an error
        }
    }

    // Find all profiles
    public List<Profile> findAllProfiles(String sqlQuery) {
        try {
            return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(Profile.class));
        } catch (Exception e) {
            LOG.error("Error fetching all profiles", e);
            return List.of();  // Return an empty list on error
        }
    }

    // Create a new profile
    public int createProfile(String sqlQuery, Object[] profileData) {
        try {
            return jdbcTemplate.update(sqlQuery, profileData);
        } catch (Exception e) {
            LOG.error("Error while creating profile", e);
            return 0;  // Indicate failure
        }
    }

    // Get next profile ID
    public int getNextProfileId(String sqlQuery) {
        try {
            return jdbcTemplate.queryForObject(sqlQuery, Integer.class);
        } catch (Exception e) {
            LOG.error("Error while getting next profile ID", e);
            return 0;  // Indicate failure
        }
    }

    // Update a profile
    public int updateProfile(String sqlQuery, Object[] updatedData) {
        try {
            return jdbcTemplate.update(sqlQuery, updatedData);
        } catch (Exception e) {
            LOG.error("Error while updating profile", e);
            return 0;  // Indicate failure
        }
    }

    // Delete a profile by ID
    public int deleteProfile(String sqlQuery, String profileId) {
        try {
            return jdbcTemplate.update(sqlQuery, profileId);
        } catch (Exception e) {
            LOG.error("Error while deleting profile with ID: " + profileId, e);
            return 0;  // Indicate failure
        }
    }

    // MapProfileRoute-related methods

    // Find a map profile route by ID
    public MapProfileRoute findMapProfileRouteById(String sqlQuery, String routeId) {
        try {
            return jdbcTemplate.queryForObject(sqlQuery, new BeanPropertyRowMapper<>(MapProfileRoute.class), routeId);
        } catch (EmptyResultDataAccessException e) {
            LOG.error("Route not found with ID: " + routeId, e);
            throw e;  // Let the service layer handle the exception if needed
        }
    }
    

    // Find all map profile routes
    public List<MapProfileRoute> findAllMapProfileRoutes(String sqlQuery) {
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(MapProfileRoute.class));
    }

    // Create a new map profile route
    private static final String CREATE_ROUTE_QUERY = "INSERT INTO mapprofileroute (INSTITUTION_ID, PRID, PROFILEID, ROUTEID, EFFECTIVEDATE, EXPIRYDATE, CREATEDATE, LASTUPDATEDATE, ISACTIVE) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

// Create a new map profile route
public int createMapProfileRoute(MapProfileRoute route) {
// Prepare parameters for the SQL query
	
 Object[] routeData = {
 route.getInstitutionId(),
 route.getPrId(),
 route.getProfileId(),
 route.getRouteId(),
 route.getEffectiveDate(),
 route.getExpiryDate(),
 new Date(),  // Set current date for CREATEDATE
 new Date(),  // Set current date for LASTUPDATEDATE
 route.getIsActive()
 };

 try {
return jdbcTemplate.update(CREATE_ROUTE_QUERY, routeData);
} catch (Exception e) {
LOG.error("Error while creating map profile route", e);
return 0;  // Indicate failure
}
}

//Repository class for MapProfileRoute


 private static final String UPDATE_ROUTE_QUERY = "UPDATE mapprofileroute SET " +
                                                 "INSTITUTION_ID = ?, " +
                                                 "PRID = ?, " +
                                                 "PROFILEID = ?, " +
                                                 "EFFECTIVEDATE = ?, " +
                                                 "EXPIRYDATE = ?, " +
                                                 "LASTUPDATEDATE = ?, " +
                                                 "ISACTIVE = ? " +
                                                 "WHERE ROUTEID = ?";

 // Update an existing map profile route
 public int updateMapProfileRoute(MapProfileRoute updatedRoute, String routeId) {
     // Prepare parameters for the SQL query
     Object[] updatedData = {
         updatedRoute.getInstitutionId(),
         updatedRoute.getPrId(),
         updatedRoute.getProfileId(),
         updatedRoute.getEffectiveDate(),
         updatedRoute.getExpiryDate(),
         updatedRoute.getLastUpdateDate(),
         updatedRoute.getIsActive(),
         routeId  // for WHERE clause
     };

     try {
         return jdbcTemplate.update(UPDATE_ROUTE_QUERY, updatedData);
     } catch (Exception e) {
         LOG.error("Error while updating map profile route", e);
         return 0;  // Indicate failure
     }
 }
    // Delete a map profile route by ID
    public int deleteMapProfileRoute(String sqlQuery, String routeId) {
        try {
            return jdbcTemplate.update(sqlQuery, routeId);
        } catch (Exception e) {
            LOG.error("Error while deleting map profile route with ID: " + routeId, e);
            return 0; 
        }
    }
}
