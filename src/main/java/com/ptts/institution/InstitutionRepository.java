package com.ptts.institution;


import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Repository
public class InstitutionRepository {

    private static final Logger LOG = LoggerFactory.getLogger(InstitutionRepository.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Find an institution by ID
    public Institution findInstitutionById(String sqlQuery, String institutionId) {
        Institution institution = null;
        try {
            institution = jdbcTemplate.queryForObject(sqlQuery, new BeanPropertyRowMapper<>(Institution.class), institutionId);
        } catch (EmptyResultDataAccessException e) {
            LOG.error("Institution not found with ID: " + institutionId, e);
            throw new EmptyResultDataAccessException("Institution not found with ID: " + institutionId, 1);
        } catch (Exception e) {
            LOG.error("Error fetching institution with ID: " + institutionId, e);
        }
        return institution;
    }

    // Find all institutions
    public List<Institution> findAllInstitutions(String sqlQuery) {
        List<Institution> institutions = null;
        try {
            institutions = jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(Institution.class));
        } catch (Exception e) {
            LOG.error("Error fetching all institutions", e);
        }
        return institutions;
    }

    // Create a new institution
    public int createInstitution(String sqlQuery, Object[] institutionData) {
        try {
            return jdbcTemplate.update(sqlQuery, institutionData);
        } catch (Exception e) {
            LOG.error("Error while creating institution", e);
            return 0;
        }
    }
    
    public int getNextInstitutionId(String sqlQuery) {
        try {
            
            return jdbcTemplate.queryForObject(sqlQuery, Integer.class);
        } catch (Exception e) {
            LOG.error("Error while retrieving next vehicle ID: ", e);
            return 0; 
        }
    }

    // Update an existing institution
    public int updateInstitution(String sqlQuery, Object[] updatedData) {
        try {
            return jdbcTemplate.update(sqlQuery, updatedData);
        } catch (Exception e) {
            LOG.error("Error while updating institution", e);
            return 0;
        }
    }
    
    

    // Delete an institution by ID
    public int deleteInstitution(String sqlQuery, String institutionId) {
        try {
            return jdbcTemplate.update(sqlQuery, institutionId);
        } catch (Exception e) {
            LOG.error("Error while deleting institution with ID: " + institutionId, e);
            return 0;
        }
    }
}
