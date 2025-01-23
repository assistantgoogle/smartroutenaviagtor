package com.ptts.routes;

import java.util.Date;

import com.ptts.driver.Driver;
import com.ptts.vehicle.Vehicle;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RouteDriverVehicleMapping {

    @NotNull
    @Size(min = 1, max = 50, message = "Institution ID must be between 1 and 50 characters")
    private String institutionId;
    private Vehicle vehicle;

    public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	@NotNull(message = "Route ID cannot be null")
    private String routeId;

    @NotNull(message = "Driver ID cannot be null")
    private String driverId;

    @NotNull(message = "Vehicle ID cannot be null")
    private String vehicleId;

    @NotNull(message = "Effective Date cannot be null")
    @FutureOrPresent(message = "Effective Date must be in the present or future")
    private Date effectiveDate;

    @NotNull(message = "Expiry Date cannot be null")
    @FutureOrPresent(message = "Expiry Date must be in the present or future")
    private Date expiryDate;

    private Date createDate;
    private Date lastUpdateDate;

    @NotNull(message = "isActive cannot be null")
    private Boolean isActive;

    private Routes routes; // Associated route details
    private Driver driver; // Associated driver details (updated type)

    private String mvrdId; // Mapping ID (optional)

    // Getters and Setters
    public String getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Routes getRoutes() {
        return routes;
    }

    public void setRoutes(Routes routes) {
        this.routes = routes;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public String getMvrdId() {
        return mvrdId;
    }

    public void setMvrdId(String mvrdId) {
        this.mvrdId = mvrdId;
    }
}
