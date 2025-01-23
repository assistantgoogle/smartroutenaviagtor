package com.ptts.institution;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/api/institution")
public class InstitutionController {

    private static final Logger LOG = LoggerFactory.getLogger(InstitutionController.class);

    @Autowired
    private AppUserController appUserInterface;

    @Autowired
    private InstitutionServices institutionService; // Ensure this is properly annotated

    // ............................ Get institution by ID ............................//
    @GetMapping("/{id}")
    public ResponseEntity<Institution> getInstitution(@PathVariable("id") String id, @Valid @RequestHeader("x-token-id") String tokenId) {
        appUserInterface.authorizeSysuser(tokenId); // Only call once
        LOG.info("Request to fetch institution with ID: {}", id);
        try {
            Institution institution = institutionService.getInstitutionById(id);
            if (institution == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Institution not found");
            }
            return ResponseEntity.ok(institution);
        } catch (Exception e) {
            LOG.error("Error fetching institution with ID: {}", id, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching institution");
        }
    }

    // ................................ Get all institutions ................................//
    @GetMapping
    public ResponseEntity<List<Institution>> getAllInstitutions() {
        LOG.info("Request to fetch all institutions");
        try {
            List<Institution> institutions = institutionService.getAllInstitutions();
            return ResponseEntity.ok(institutions);
        } catch (Exception e) {
            LOG.error("Error fetching all institutions", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching institutions");
        }
    }

    // ............................ Create new institution ................................//
    @PostMapping("/add")
    public ResponseEntity<Void> createInstitution(@RequestBody Institution institution, @Valid @RequestHeader("x-token-id") String tokenId) {
        LOG.info("Request to create institution: {}", institution.getName());
        appUserInterface.authorizeSysuser(tokenId);
        try {
            institutionService.addInstitution(institution);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            LOG.error("Error creating institution: {}", institution.getName(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating institution");
        }
    }

    // ............................ Update an institution ................................//
    @PutMapping("/update")
    public ResponseEntity<String> updateInstitution(@RequestBody Institution institution, @Valid @RequestHeader("x-token-id") String tokenId) {
        LOG.info("Request to update institution with ID: {}", institution.getInstitutionId());
        appUserInterface.authorizeSysuser(tokenId);
        try {
            institutionService.updateInstitution(institution);
            return ResponseEntity.ok("Institution updated successfully");
        } catch (IllegalArgumentException e) {
            LOG.error("Invalid data for institution update: {}", e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data provided: " + e.getMessage());
        } catch (NullPointerException e) {
            LOG.error("NullPointerException while updating institution with ID: {}", institution.getInstitutionId(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Institution data is incomplete: " + e.getMessage());
        } catch (Exception e) {
            LOG.error("Error updating institution with ID: {}", institution.getInstitutionId(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred: " + e.getMessage());
        }
    }

    // ............................ Delete institution by ID ................................//
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteInstitution(@PathVariable("id") String id, @Valid @RequestHeader("x-token-id") String tokenId) {
        LOG.info("Request to delete institution with ID: {}", id);
        appUserInterface.authorizeSysuser(tokenId);
        try {
            institutionService.deleteInstitution(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            LOG.error("Error deleting institution with ID: {}", id, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting institution");
        }
    }
}
