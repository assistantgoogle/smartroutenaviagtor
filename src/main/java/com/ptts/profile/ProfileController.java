package com.ptts.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.ptts.appuser.AppUserController;

import jakarta.validation.Valid;

import java.util.List;

@Service
@RestController
@RequestMapping("/api")
public class ProfileController {

    @Autowired
    private ProfileService profileService;
    
    @Autowired
    AppUserController appUserInterface;

    @PostMapping("/mpr/create")
    
    
    
    
    public ResponseEntity<String> createMapProfileRoute(@RequestBody MapProfileRoute route,
                                                         @Valid @RequestHeader("x-token-id") String tokenId) {
    	   appUserInterface.authorizeSysuser(tokenId);
        try {
            MapProfileRoute createdRoute = profileService.createMapProfileRoute(route);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Map Profile Route created successfully with ID: " + createdRoute.getPrId());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create Map Profile Route: " + e.getMessage());
        }
    }

    @GetMapping("/mapProfileRoute/{routeId}")
    public ResponseEntity<MapProfileRoute> getMapProfileRouteById(@PathVariable("routeId") String routeId,
                                                                   @Valid @RequestHeader("x-token-id") String tokenId) {
    	   appUserInterface.authorizeSysuser(tokenId);
        try {
            MapProfileRoute route = profileService.findMapProfileRouteById(routeId);
            return ResponseEntity.ok(route);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Map Profile Route not found: " + e.getMessage());
        }
    }
    

    @GetMapping("/mapProfileRoute")
    public ResponseEntity<List<MapProfileRoute>> getAllMapProfileRoutes() {
    	
        try {
            List<MapProfileRoute> routes = profileService.findAllMapProfileRoutes();
            return ResponseEntity.ok(routes);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve Map Profile Routes: " + e.getMessage());
        }
    }

    @PutMapping("/mapProfileRoute/update/{routeId}")
    public ResponseEntity<String> updateMapProfileRoute(@PathVariable("routeId") String routeId, 
                                                         @RequestBody MapProfileRoute updatedRoute,
                                                         @Valid @RequestHeader("x-token-id") String tokenId) {
        try {
            profileService.updateMapProfileRoute(routeId, updatedRoute);
            return ResponseEntity.ok("Map Profile Route updated successfully with ID: " + routeId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update Map Profile Route: " + e.getMessage());
        }
    }

    @DeleteMapping("/mapProfileRoute/delete/{routeId}")
    public ResponseEntity<String> deleteMapProfileRoute(@PathVariable("routeId") String routeId) {
        try {
            profileService.deleteMapProfileRoute(routeId);
            return ResponseEntity.ok("Map Profile Route deleted successfully with ID: " + routeId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete Map Profile Route: " + e.getMessage());
        }
    }

    @PostMapping("/profile/create")
    public ResponseEntity<String> createProfile(@RequestBody Profile profile,
                                                 @Valid @RequestHeader("x-token-id") String tokenId) {
        try {
            Profile createdProfile = profileService.createProfile(profile);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Profile created successfully with ID: " + createdProfile.getProfileId());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create Profile: " + e.getMessage());
        }
    }

    @GetMapping("/profiles/{profileId}")
    public ResponseEntity<Profile> getProfileById(@PathVariable("profileId") String profileId,
                                                   @Valid @RequestHeader("x-token-id") String tokenId) {
        try {
            appUserInterface.authorizeSysuser(tokenId);
            
            Profile profile = profileService.findProfileById(profileId);
            return ResponseEntity.ok(profile);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found: " + e.getMessage());
        }
    }

    @GetMapping("/profiles")
    public ResponseEntity<List<Profile>> getAllProfiles() {
        try {
            List<Profile> profiles = profileService.findAllProfiles();
            return ResponseEntity.ok(profiles);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve Profiles: " + e.getMessage());
        }
    }

    @PutMapping("/profiles/update/{profileId}")
    public ResponseEntity<String> updateProfile(@PathVariable("profileId") String profileId, 
                                                 @RequestBody Profile updatedProfile,
                                                 @Valid @RequestHeader("x-token-id") String tokenId) {
        try {
            profileService.updateProfile(profileId, updatedProfile);
            return ResponseEntity.ok("Profile updated successfully with ID: " + profileId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update Profile: " + e.getMessage());
        }
    }
    

    @DeleteMapping("/profiles/delete/{profileId}")
    public ResponseEntity<String> deleteProfile(@PathVariable("profileId") String profileId) {
    	
        try {
            profileService.deleteProfile(profileId);
            return ResponseEntity.ok("Profile deleted successfully with ID: " + profileId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete Profile: " + e.getMessage());
        }
    }
    
}
