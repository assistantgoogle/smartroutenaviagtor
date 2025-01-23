package com.ptts.appuser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class AppUserService {

    private static final Logger LOG = LoggerFactory.getLogger(AppUserService.class);

    @Autowired
    private AppUserRepository appUserRepository;

    private static final String GET_NEXT_USER_ID = "SELECT COALESCE(MAX(ID), 0) + 1 FROM appuser";
    private static final String CREATE_USER_QUERY = "INSERT INTO appuser (USERID, USERNAME, PASSKEY, TOKEN, EFFECTIVEDATE, EXPIRYDATE, CREATEDATE, LASTUPDATEDATE, ISACTIVE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_USER_QUERY = "UPDATE appuser SET USERID = ?, PASSKEY = ?, TOKEN = ?, EFFECTIVEDATE = ?, EXPIRYDATE = ?, LASTUPDATEDATE = ?, ISACTIVE = ? WHERE USERNAME = ?";
    private static final String DELETE_USER_QUERY = "DELETE FROM appuser WHERE USERNAME = ? AND PASSKEY = ? AND TOKEN = ?";
    
    private static final String VALIDATE_TOKEN_QUERY = "SELECT COUNT(*) FROM appuser WHERE TOKEN = ?";
    
    public ModelAppUser findUserById(String userName, String passKey, String token) {
        LOG.info("Attempting to find user with Username: {}, PassKey: {}, Token: {}", userName, passKey, token);
        String sqlQuery = "SELECT * FROM appuser WHERE USERNAME = ? AND PASSKEY = ? AND TOKEN = ?";
        ModelAppUser user = appUserRepository.findUserById(sqlQuery, userName, passKey, token);
        LOG.info("User found: {}", user);
        
        return user;
        
    }

    // Find all users
    public List<ModelAppUser> findAllUsers() {
        LOG.info("Fetching all users from the database");
        String sqlQuery = "SELECT * FROM appuser";
        List<ModelAppUser> users = appUserRepository.findAllUsers(sqlQuery);
        LOG.info("Number of users fetched: {}", users.size());
        return users;
    }

    // Create a new user
    public int createUser(ModelAppUser user) {
        LOG.info("Creating a new user with data: {}", user);

        // Get the next user ID
        int nextId = appUserRepository.getNextUserId(GET_NEXT_USER_ID);
        String userId = "UID_" + nextId;
        user.setUserId(userId);

        // Prepare user data for insertion
        Object[] userData = new Object[]{
            user.getUserId(),
            user.getUserName(),
            user.getPassKey(),
            user.getToken(),
            user.getEffectiveDate(),
            user.getExpiryDate(),
            new Date(), // Created date
            new Date(), // Last updated date
            user.isActive()
        };

        int result = appUserRepository.createUser(CREATE_USER_QUERY, userData);
        LOG.info("User created successfully, affected rows: {}", result);
        return result;
    }

    public int updateUser(String userName, ModelAppUser updatedUser) {
        LOG.info("Updating user with username: {}", userName);
        
      
        if (updatedUser.getUserId() == null || updatedUser.getUserId().isEmpty()) {
            LOG.warn("UserID is missing for username: {}. Generating new UserID...", userName);
            
           
            int nextId = appUserRepository.getNextUserId(GET_NEXT_USER_ID);
            String newUserId = "UID_" + nextId;
            updatedUser.setUserId(newUserId);
            
            LOG.info("Generated and set new UserID: {}", newUserId);
        }

        // Log the updated user ID
        LOG.info("Updated user ID: {}", updatedUser.getUserId());

        // Prepare the SQL update query
        int result = appUserRepository.updateUser(UPDATE_USER_QUERY, new Object[]{
            updatedUser.getUserId(),
            updatedUser.getPassKey(),
            updatedUser.getToken(),
            updatedUser.getEffectiveDate(),
            updatedUser.getExpiryDate(),
            new Date(), // Set the last updated date to the current date
            updatedUser.isActive(),
            userName // This is used in the WHERE clause to identify which user to update
        });

        if (result > 0) {
            LOG.info("User updated successfully: {}", userName);
        } else {
            LOG.error("Failed to update user with username: {}", userName);
        }
        
        return result;
    }
    
 // Method to authenticate a user
    
    public String authenticateUser(String userName, String passKey) {
        LOG.info("Authenticating user with Username: {}", userName);
        
        
        String sqlQuery = "SELECT * FROM appuser WHERE USERNAME = ? AND PASSKEY=?";
        ModelAppUser user = null;
        
        try {
            // Find the user in the repository
            user = appUserRepository.findUserByUserNameAndPassKey(sqlQuery, userName, passKey);
            
        } catch (EmptyResultDataAccessException e) {
            LOG.error("Authentication failed: User not found for Username: {}", userName);
            return null;
        }
        
        
        String token = UUID.randomUUID().toString();
        LOG.info("Generated token for user: {}", token);

        user.setToken(token);
        updateUser(userName, user);  
        
        return token;  
    }
    
    public boolean isTokenValid(String tokenId) {
        LOG.info("Validating token: {}", tokenId);

        try {
            // Query the database to check if the token exists
            int count = appUserRepository.validateToken(VALIDATE_TOKEN_QUERY, tokenId);
            
            if (count > 0) {
                LOG.info("Token {} is valid", tokenId);
                return true;
            } else {
                LOG.warn("Token {} is invalid", tokenId);
                return false;
            }
        } catch (Exception e) {
            LOG.error("Error occurred while validating token {}: {}", tokenId, e.getMessage());
            return false;
        }
    }
    
    public int deleteUser(String userName, String passKey, String token) {
        LOG.info("Attempting to delete user with Username: {}, PassKey: {}, Token: {}", userName, passKey, token);
        int result = appUserRepository.deleteUser(DELETE_USER_QUERY, userName, passKey, token);
        LOG.info("User deleted successfully, affected rows: {}", result);
        return result;
    }
}
