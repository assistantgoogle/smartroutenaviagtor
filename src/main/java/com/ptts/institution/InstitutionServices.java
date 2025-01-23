package com.ptts.institution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstitutionServices {

    private static final Logger LOG = LoggerFactory.getLogger(InstitutionServices.class);
    
    @Autowired
    private InstitutionRepository institutionRepository;
    
    private static final String GET_NEXT_INSTITUTION_ID="SELECT COALESCE(MAX(ID), 0) + 1 FROM institution";

    // Find an institution by ID
    public Institution getInstitutionById(String institutionId) {
        String sqlQuery = "SELECT * FROM institution WHERE INSTITUTION_ID = ?";
        LOG.info("Fetching institution with ID: {}", institutionId);
        return institutionRepository.findInstitutionById(sqlQuery, institutionId);
    }

    
    // Find all institutions
    public List<Institution> getAllInstitutions() {
        String sqlQuery = "SELECT * FROM institution";
        LOG.info("Fetching all institutions");
        return institutionRepository.findAllInstitutions(sqlQuery);
    }

 // Add new institution with dynamically generated institution_id
    public int addInstitution(Institution institution) {
       
        int nextInstitutionId = institutionRepository.getNextInstitutionId(GET_NEXT_INSTITUTION_ID);
        
       
        String institutionId = "INST_" + nextInstitutionId;
        institution.setInstitutionId(institutionId);  
     
        String sqlQuery = "INSERT INTO institution (INSTITUTION_ID, NAME, ADDRESS, CONTACTNUMBER, EMAIL) VALUES (?, ?, ?, ?, ?)";
        
       
        Object[] institutionData = {
            institution.getInstitutionId(),  
            institution.getName(),
            institution.getAddress(),
            institution.getContactNumber(),
            institution.getEmail()
        };

        LOG.info("Creating institution: {}", institution.getName());
        
        
        return institutionRepository.createInstitution(sqlQuery, institutionData);
    }


    // Update an existing institution
    public int updateInstitution(Institution institution) {
        String sqlQuery = "UPDATE institution SET NAME = ?, ADDRESS = ?, CONTACTNUMBER = ?, EMAIL = ? WHERE INSTITUTION_ID = ?";
        Object[] updatedData = {
            institution.getName(),
            institution.getAddress(),
            institution.getContactNumber(),
            institution.getEmail(),
            institution.getInstitutionId()
        };
        LOG.info("Updating institution with ID: {}", institution.getInstitutionId());
        return institutionRepository.updateInstitution(sqlQuery, updatedData);
    }

    // Delete an institution by ID
    public int deleteInstitution(String institutionId) {
        String sqlQuery = "DELETE FROM institution WHERE INSTITUTION_ID = ?";
        LOG.info("Deleting institution with ID: {}", institutionId);
        return institutionRepository.deleteInstitution(sqlQuery, institutionId);
    }
}
