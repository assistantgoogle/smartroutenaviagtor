package com.ptts.device;

import java.util.Date;
import jakarta.validation.constraints.NotNull;


public class MapDeviceVehicle {

    private Integer id; 

   
    private String dvId;

    @NotNull(message = "Device ID cannot be null")
    private String deviceId;

    @NotNull(message = "Vehicle ID cannot be null")
    private String vehicleId;

    @NotNull(message = "Institution ID cannot be null")
    private String institutionId;

    @NotNull(message = "Effective Date cannot be null")
    private Date effectiveDate;

    @NotNull(message = "Expiry Date cannot be null")
    private Date expiryDate;

    private Date createDate;
    private Date lastUpdateDate;
    private Boolean isActive;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getdvId() {
        return dvId;
    }

    public void setdvId(String dvId) {
        this.dvId = dvId;
    }
    
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
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
}
