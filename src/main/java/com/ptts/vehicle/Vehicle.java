package com.ptts.vehicle;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

public class Vehicle {

	
   
    private String vehicleId;
    
    
    @NotNull
    @Size(min = 1, max = 50)
    private String institutionId; 
    
    public String getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(String institutionId) {
		this.institutionId = institutionId;
	}

	
    
    @NotBlank(message = "Vehicle number cannot be blank")
    @Pattern(regexp = "^[A-Z0-9-]{1,20}$")
    private String vehicleNumber;
    @NotBlank
    @Size(min = 2, max = 50)
    private String vehicleType;
    @NotBlank
    @Size(min = 1, max = 50)
    private String vehicleBrand;

    @NotBlank
    @Size(min = 1, max = 50)
    private String vehicleModel;

    @NotNull(message = "Registration date cannot be null")
    private LocalDate registrationDate;

    @NotNull(message = "Capacity cannot be null")
    @Pattern(regexp = "^\\d+$")
    private String capacity;

    @NotBlank(message = "Insurance details cannot be blank")
    @Size(min = 1, max = 200)
    private String insuranceDetails;

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }
  
    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
        
        
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleBrand() {
        return vehicleBrand;
    }

    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getInsuranceDetails() {
        return insuranceDetails;
    }

    public void setInsuranceDetails(String insuranceDetails) {
        this.insuranceDetails = insuranceDetails;
    }
    
}
