package com.ptts.vehicle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    private final String GET_NEXT_VEHICLE_ID = "SELECT COALESCE(MAX(ID), 0) + 1 FROM vehicles";
    private final String findByIdQuery = "SELECT * FROM vehicles WHERE VEHICLEID = ? and INSTITUTION_ID=?";
    private final String findAllQuery = "SELECT * FROM vehicles";
    private final String createQuery = "INSERT INTO vehicles (VEHICLEID, VEHICLENUMBER, VEHICLETYPE, VEHICLEBRAND, VEHICLEMODEL, REGISTRATIONDATE, CAPACITY, INSURANCEDETAILS, INSTITUTION_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String updateQuery = "UPDATE vehicles SET VEHICLENUMBER = ?, VEHICLETYPE = ?, VEHICLEBRAND = ?, VEHICLEMODEL = ?, REGISTRATIONDATE = ?, CAPACITY = ?, INSURANCEDETAILS = ?, INSTITUTION_ID = ? WHERE VEHICLEID = ?";
    private final String deleteQuery = "DELETE FROM vehicles WHERE VEHICLEID = ?";


    // ............................ Get a vehicle by ID ............................//
    public Vehicle getVehicleById(String vehicleId,String institutionId) {
        return vehicleRepository.findVehicleById(findByIdQuery, vehicleId,institutionId);
    }

    // .................................... Get all vehicles ..................................//
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAllVehicles(findAllQuery);
    }

    // ............................ Create a new vehicle ......................................//
 // ............................ Create a new vehicle ......................................//
    public int createVehicle(Vehicle vehicle) {
        // Get the next vehicle ID
        int nextId = vehicleRepository.getNextVehicleId(GET_NEXT_VEHICLE_ID);
        String vehicleId = "VID_" + nextId;  
        vehicle.setVehicleId(vehicleId); 

      
        Object[] vehicleData = {
            vehicle.getVehicleId(),
            vehicle.getVehicleNumber(),
            vehicle.getVehicleType(),
            vehicle.getVehicleBrand(),
            vehicle.getVehicleModel(),
            vehicle.getRegistrationDate(),
            vehicle.getCapacity(),
            vehicle.getInsuranceDetails(),
            vehicle.getInstitutionId() 
        };

       
        int result = vehicleRepository.createVehicle(createQuery, vehicleData);
        
        // Return the result of the operation
        return result;
    }

    public int updateVehicle(String vehicleId, Vehicle updatedVehicle) {
        Object[] updatedData = {
            updatedVehicle.getVehicleNumber(),
            updatedVehicle.getVehicleType(),
            updatedVehicle.getVehicleBrand(),
            updatedVehicle.getVehicleModel(),
            updatedVehicle.getRegistrationDate(),
            updatedVehicle.getCapacity(),
            updatedVehicle.getInsuranceDetails(),
            updatedVehicle.getInstitutionId(),  // Include institutionId
            vehicleId
        };
        return vehicleRepository.updateVehicle(updateQuery, updatedData);
    }


    // ........................... Delete a vehicle by ID ....................................//
    public int deleteVehicle(String vehicleId) {
        return vehicleRepository.deleteVehicle(deleteQuery, vehicleId);
    }
}
