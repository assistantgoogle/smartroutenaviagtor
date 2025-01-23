package com.ptts.driver;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Driver {
	
    
   
    private String driverId;
    
    public String getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(String institutionId) {
		this.institutionId = institutionId;
	}
	

	@NotNull
    @Size(min = 1, max = 50)
    private String institutionId; 
    
    @NotNull(message = "First name cannot be null")
    @Size(min = 1, max = 50)
    private String fName;
    
    @NotNull(message = "Last name cannot be null")
    @Size(min = 1, max = 50)
    private String lName;
    
    @NotNull(message = "License number cannot be null")
    @Size(min = 5, max = 20)
    private String licenseNo;

    @NotNull(message = "Phone number cannot be null")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be a valid 10-digit number")
    private String phoneNo;

    @NotNull(message = "Email cannot be null")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Email should be valid")
    private String email;

    @NotNull(message = "Address cannot be null")
    @Size(min = 1, max = 200, message = "Address must be between 1 and 200 characters")
    private String address;

    @NotNull(message = "Current location cannot be null")
    @Size(min = 1, max = 100)
    private String currentLocation;

    @Min(value = 0)
    @Max(value = 5)
    private Float driverRating;

    @NotNull(message = "Status cannot be null")
    @Size(min = 1, max = 20)
    private String status;

    @NotNull(message = "Number of years of experience cannot be null")
    @Min(value = 0)
    private Integer no_of_experience;

    // Getters and Setters

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Float getDriverRating() {
        return driverRating;
    }

    public void setDriverRating(Float driverRating) {
        this.driverRating = driverRating;
    }

    public String getStatus() {
        return status;
    }  

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getNo_of_experience() {
        return no_of_experience;
    }

    public void setNo_of_experience(Integer no_of_experience) {
        this.no_of_experience = no_of_experience;
    }
}
