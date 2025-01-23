package com.ptts.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DriverServiceImpl implements DriverService {
    @Autowired
    private DriverRepository driverRepository;
    private static final String GET_NEXT_DRIVER_ID = "SELECT COALESCE(MAX(ID), 0) + 1 FROM driver";
    private static final String FIND_DRIVER_BY_ID_QUERY = "SELECT * FROM driver WHERE DRIVERID = ? and INSTITUTION_ID=?";
    private static final String FIND_ALL_DRIVERS_QUERY = "SELECT * FROM driver";
    private static final String CREATE_DRIVER_QUERY = "INSERT INTO driver (DRIVERID, INSTITUTION_ID, FNAME, LNAME, LICENSENO, PHONENO, EMAIL, ADDRESS, CURRENTLOCATION, DRIVERRATING, STATUS, NO_OF_EXPERIENCE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    private static final String UPDATE_DRIVER_QUERY = "UPDATE driver SET INSTITUTION_ID=?, FNAME=?, LNAME=?, LICENSENO=?, PHONENO=?, EMAIL=?, ADDRESS=?, CURRENTLOCATION=?, DRIVERRATING=?, STATUS=?, NO_OF_EXPERIENCE=? WHERE DRIVERID=?";
    
    private static final String DELETE_DRIVER_QUERY = "DELETE FROM driver WHERE DRIVERID=?";

    @Override
    public Driver getDriverById(String driverId, String institutionId) {
        return driverRepository.findDriverById(FIND_DRIVER_BY_ID_QUERY, driverId, institutionId);
    }

    @Override
    public List<Driver> getAllDrivers() {
        return driverRepository.findAllDrivers(FIND_ALL_DRIVERS_QUERY);
    }

    @Override
    public int createDriver(Driver driver) {
        // Get the next available driver ID
        int nextId = driverRepository.getNextDriverId(GET_NEXT_DRIVER_ID);
        String driverId = "DID_" + nextId; 
        driver.setDriverId(driverId); 

        // Prepare the driver data for insertion
        Object[] driverData = new Object[]{
            driver.getDriverId(), driver.getInstitutionId(), driver.getfName(), driver.getlName(),
            driver.getLicenseNo(), driver.getPhoneNo(), driver.getEmail(),
            driver.getAddress(), driver.getCurrentLocation(),
            driver.getDriverRating(), driver.getStatus(), driver.getNo_of_experience()
        };

        // Create the driver in the repository
        int result = driverRepository.createDriver(CREATE_DRIVER_QUERY, driverData);

        if (result <= 0) {
            throw new RuntimeException("Driver creation failed");
        }
        return result;
    }

    @Override
    public int updateDriver(Driver driver) {
        Object[] updatedData = new Object[]{
            driver.getInstitutionId(), driver.getfName(), driver.getlName(), driver.getLicenseNo(),
            driver.getPhoneNo(), driver.getEmail(), driver.getAddress(),
            driver.getCurrentLocation(), driver.getDriverRating(),
            driver.getStatus(), driver.getNo_of_experience(), driver.getDriverId()
        };
        return driverRepository.updateDriver(UPDATE_DRIVER_QUERY, updatedData);
    }

    @Override
    public int deleteDriver(String driverId) {
        return driverRepository.deleteDriver(DELETE_DRIVER_QUERY, driverId);
    }
}
