package com.ptts.appuser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.validation.Valid;
import java.util.Base64;
import java.util.List;
@RestController
@RequestMapping("/api/users")
public class AppUserController {
	
    private static final Logger LOG = LoggerFactory.getLogger(AppUserController.class);
    @Autowired
    private AppUserService appUserService;
    
    public String authorizeSysuser(@Valid @RequestHeader("x-token-id") String tokenId) {
        LOG.debug("AuthorizeInterface authorizeSysuser : Authorize SystemUser with token {}", tokenId);
        String tokenIdDecoded = new String(Base64.getDecoder().decode(tokenId.getBytes()));
        LOG.debug("Decoded tokenId: {}", tokenIdDecoded);

      
        boolean isTokenValid = appUserService.isTokenValid(tokenIdDecoded);

        if (!isTokenValid) {
        	String errorMsg = "Unauthorized to access service";
            LOG.warn(errorMsg);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMsg );
        }
        return "success";
    }

    @GetMapping("/{userName}/{passKey}/{token}")
    public ResponseEntity<?> getUser(@PathVariable String userName, 
                                     @PathVariable String passKey, 
                                     @PathVariable String token,
                                     @RequestHeader("x-token-id") String tokenId) {
       
       
        authorizeSysuser(tokenId);
        LOG.info("Received request to fetch user with Username: {}, PassKey: {}, Token: {}", userName, passKey, token);
        ModelAppUser user = appUserService.findUserById(userName, passKey, token);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers(@RequestHeader("x-token-id") String tokenId) {
        // Authorize the user with the token
       // ResponseEntity<String> authResponse = authorizeSysuser(tokenId);
        //if (authResponse != null) return authResponse;

    	 authorizeSysuser(tokenId);
        LOG.info("Received request to fetch all users");
        List<ModelAppUser> users = appUserService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody ModelAppUser user, 
                                        @RequestHeader("x-token-id") String tokenId) {
        // Authorize the user with the token
       // ResponseEntity<String> authResponse = authorizeSysuser(tokenId);
        //if (authResponse != null) return authResponse;
    	 authorizeSysuser(tokenId);
        LOG.info("Received request to create a new user with data: {}", user);
        int result = appUserService.createUser(user);
        return ResponseEntity.ok("User created successfully, affected rows: " + result);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@RequestBody ModelAppUser request) {
    	
        LOG.info("Authentication request received for Username: {}", request.getUserName());
        
        String token = appUserService.authenticateUser(request.getUserName(), request.getPassKey());
        if (token != null) {
            return ResponseEntity.ok(token);
        } else {
        	throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication failed. Invalid username or password.");
        }
    }

    @PutMapping("/update/{userName}")
    public ResponseEntity<?> updateUser(@PathVariable("userName") String userName, 
                                        @RequestBody ModelAppUser updatedUser,
                                        @RequestHeader("x-token-id") String tokenId) {
        // Authorize the user with the token
       // ResponseEntity<String> authResponse = authorizeSysuser(tokenId);
        //if (authResponse != null) return authResponse;

        LOG.info("Received request to update user with username: {}", userName);
        authorizeSysuser(tokenId);
        int result = appUserService.updateUser(userName, updatedUser);
        return ResponseEntity.ok("User updated successfully, affected rows: " + result);
    }

    @DeleteMapping("/{userName}/{passKey}/{token}")
    public ResponseEntity<?> deleteUser(@PathVariable("userName") String userName, 
                                        @PathVariable("passKey") String passKey, 
                                        @PathVariable("token") String token,
                                        @RequestHeader("x-token-id") String tokenId) {
        // Authorize the user with the token
        //ResponseEntity<String> authResponse = authorizeSysuser(tokenId);
        //if (authResponse != null) return authResponse;

        LOG.info("Received request to delete user with Username: {}, PassKey: {}, Token: {}", userName, passKey, token);
        authorizeSysuser(tokenId);
        int result = appUserService.deleteUser(userName, passKey, token);
        return ResponseEntity.ok("User deleted successfully, affected rows: " + result);
    }
}
