package com.ptts.appuser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

@Repository
public class AppUserRepository {

    private static final Logger LOG = LoggerFactory.getLogger(AppUserRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ModelAppUser findUserById(String sqlQuery, String userName, String passKey, String token) {
        ModelAppUser user = null;
        try {
            user = jdbcTemplate.queryForObject(sqlQuery, new BeanPropertyRowMapper<>(ModelAppUser.class), userName, passKey, token);
        } catch (EmptyResultDataAccessException e) {
            LOG.error("User not found with Username: {}, PassKey: {}, Token: {}", userName, passKey, token, e);
            throw e; // Return the exception without wrapping it
        }
        return user;
    }

    // Find all users
    public List<ModelAppUser> findAllUsers(String sqlQuery) {
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(ModelAppUser.class));
    }

    public int createUser(String sqlQuery, Object[] userData) {
        return jdbcTemplate.update(sqlQuery, userData);
    }

 
    public ModelAppUser findUserByUserNameAndPassKey(String sqlQuery, String userName, String passKey) {
        ModelAppUser user = null;
        try {
            user = jdbcTemplate.queryForObject(sqlQuery, new BeanPropertyRowMapper<>(ModelAppUser.class), userName, passKey);
        } catch (EmptyResultDataAccessException e) {
            LOG.error("User not found with Username: {}, PassKey: {}", userName, passKey, e);
            throw e; // Throw exception to indicate authentication failure
        }
        return user;
    }
    public int validateToken(String sqlQuery, String tokenId) {
        LOG.info("Validating token with SQL: {}", sqlQuery);
        return jdbcTemplate.queryForObject(sqlQuery, Integer.class, tokenId);
    }
    
    
    
    
    
    // Update an existing user
    public int updateUser(String sqlQuery, Object[] updatedData) {
        LOG.info("Updating user with SQL: {}", sqlQuery);
        return jdbcTemplate.update(sqlQuery, updatedData);
    }

    public int deleteUser(String sqlQuery, String userName, String passKey, String token) {
        return jdbcTemplate.update(sqlQuery, userName, passKey, token);
    }

    public int getNextUserId(String sqlQuery) {
        return jdbcTemplate.queryForObject(sqlQuery, Integer.class);
    }
}
