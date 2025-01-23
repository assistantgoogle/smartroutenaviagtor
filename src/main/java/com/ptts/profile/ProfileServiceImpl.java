package com.ptts.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    // SQL queries for ProfileRoute operations
    private static final String GET_NEXT_PROFILE_ROUTE_ID = "SELECT COALESCE(MAX(ID), 0) + 1 FROM mapprofileroute";
    private static final String FIND_ROUTE_BY_ID_QUERY = "SELECT * FROM mapprofileroute WHERE ROUTEID = ?";
    private static final String FIND_ALL_ROUTES_QUERY = "SELECT * FROM mapprofileroute";
   
    private static final String DELETE_ROUTE_QUERY = "DELETE FROM mapprofileroute WHERE ROUTEID = ?";

    // SQL queries for Profile operations
    private static final String GET_NEXT_PROFILE_ID = "SELECT COALESCE(MAX(ID), 0) + 1 FROM profile";
    private static final String FIND_PROFILE_BY_ID_QUERY = "SELECT * FROM profile WHERE PROFILEID = ?";
    private static final String FIND_ALL_PROFILES_QUERY = "SELECT * FROM profile";
    private static final String CREATE_PROFILE_QUERY = "INSERT INTO profile (PROFILEID, ORGID, INSTITUTION_ID, FNAME, LNAME, PHNO1, PROFILEEMAILONE, DOB, REFERENCEID, GENDER, EMERGENCYNO, ADDRESSTYPE, ADDRESS1, ADDRESS2, PROFILEPINCODE, PROFILECITY, PROFILESTATE, PROFILECOUNTRY, LANDMARK, PHNO2) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_PROFILE_QUERY = "UPDATE profile SET ORGID = ?, FNAME = ?, LNAME = ?, PHNO1 = ?, PROFILEEMAILONE = ?, DOB = ?, REFERENCEID = ?, GENDER = ?, EMERGENCYNO = ?, ADDRESSTYPE = ?, ADDRESS1 = ?, ADDRESS2 = ?, PROFILEPINCODE = ?, PROFILECITY = ?, PROFILESTATE = ?, PROFILECOUNTRY = ?, LANDMARK = ?, PHNO2 = ?, INSTITUTION_ID = ? WHERE PROFILEID = ?";
    private static final String DELETE_PROFILE_QUERY = "DELETE FROM profile WHERE PROFILEID = ?";

    // ProfileRoute related operations
 // Service method
    public MapProfileRoute createMapProfileRoute(MapProfileRoute route) {
        // Set creation and last update dates
        route.setCreateDate(new Date());
        route.setLastUpdateDate(new Date());

        // Get the next ID for the profile route
        int nextId = profileRepository.getNextProfileRouteId(GET_NEXT_PROFILE_ROUTE_ID);
        String prId = "PRID_" + nextId;
        route.setPrId(prId);

        // Execute the insertion into the database
        int result = profileRepository.createMapProfileRoute(route);
        if (result <= 0) {
            throw new RuntimeException("Failed to create map profile route: " + route.getPrId());
        }

        return route;  
    }


    public MapProfileRoute findMapProfileRouteById(String routeId) {
        return profileRepository.findMapProfileRouteById(FIND_ROUTE_BY_ID_QUERY, routeId);
    }

    public List<MapProfileRoute> findAllMapProfileRoutes() {
        return profileRepository.findAllMapProfileRoutes(FIND_ALL_ROUTES_QUERY);
    }

 // Service method
    public MapProfileRoute updateMapProfileRoute(String routeId, MapProfileRoute updatedRoute) {
        // Set the current date as last update date
        updatedRoute.setLastUpdateDate(new Date());

        // Execute the update operation
        int result = profileRepository.updateMapProfileRoute(updatedRoute, routeId);
        if (result <= 0) {
            throw new RuntimeException("Failed to update map profile route: " + routeId);
        }

        return updatedRoute;
    }

    public void deleteMapProfileRoute(String routeId) {
        int result = profileRepository.deleteMapProfileRoute(DELETE_ROUTE_QUERY, routeId);
        if (result == 0) {
            throw new RuntimeException("Route not found: " + routeId);
        }
    }

    // Profile related operations
    public Profile createProfile(Profile profile) {
        // Get the next profile ID
        int nextProfileId = profileRepository.getNextProfileId(GET_NEXT_PROFILE_ID);
        String generatedProfileId = "PROF_" + nextProfileId;
        profile.setProfileId(generatedProfileId);

        // Prepare parameters for the SQL query
        Object[] profileData = new Object[]{
            profile.getProfileId(),
            profile.getOrgId(),
            profile.getInstitutionId(),
            profile.getfName(),
            profile.getlName(),
            profile.getPhno1(),
            profile.getProfileEmailOne(),
            profile.getDob(),
            profile.getReference_Id(),
            profile.getGender(),
            profile.getEmergency_no(),
            profile.getAdressType(),
            profile.getAdress1(),
            profile.getAdress2(),
            profile.getProfilepincode(),
            profile.getProfilecity(),
            profile.getProfilestate(),
            profile.getProfilecountry(),
            profile.getLandmark(),
            profile.getPhno2()
        };

        // Execute the insertion into the database
        int result = profileRepository.createProfile(CREATE_PROFILE_QUERY, profileData);
        if (result <= 0) {
            throw new RuntimeException("Failed to create profile: " + profile.getProfileId());
        }

        return profile;  // Return the created profile object
    }

    public Profile findProfileById(String profileId) {
        return profileRepository.findProfileById(FIND_PROFILE_BY_ID_QUERY, profileId);
    }

    public List<Profile> findAllProfiles() {
        return profileRepository.findAllProfiles(FIND_ALL_PROFILES_QUERY);
    }

    public Profile updateProfile(String profileId, Profile updatedProfile) {
        // Prepare parameters for the SQL query
        Object[] updatedData = new Object[]{
            updatedProfile.getOrgId(),
            updatedProfile.getfName(),
            updatedProfile.getlName(),
            updatedProfile.getPhno1(),
            updatedProfile.getProfileEmailOne(),
            updatedProfile.getDob(),
            updatedProfile.getReference_Id(),
            updatedProfile.getGender(),
            updatedProfile.getEmergency_no(),
            updatedProfile.getAdressType(),
            updatedProfile.getAdress1(),
            updatedProfile.getAdress2(),
            updatedProfile.getProfilepincode(),
            updatedProfile.getProfilecity(),
            updatedProfile.getProfilestate(),
            updatedProfile.getProfilecountry(),
            updatedProfile.getLandmark(),
            updatedProfile.getPhno2(),
            updatedProfile.getInstitutionId(),
            profileId  // for WHERE clause
        };

        // Execute the update operation
        int result = profileRepository.updateProfile(UPDATE_PROFILE_QUERY, updatedData);
        if (result <= 0) {
            throw new RuntimeException("Failed to update profile: " + profileId);
        }

        return updatedProfile;
    }

    public void deleteProfile(String profileId) {
        int result = profileRepository.deleteProfile(DELETE_PROFILE_QUERY, profileId);
        if (result == 0) {
            throw new RuntimeException("Profile not found: " + profileId);
        }
    }
    
}
