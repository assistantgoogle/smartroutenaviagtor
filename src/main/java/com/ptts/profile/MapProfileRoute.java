package com.ptts.profile;

import java.util.Date;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.FutureOrPresent;

public class MapProfileRoute {

    @NotNull
    @Size(min = 1, max = 50)
    private String institutionId;
    private String prId;
    

    @NotNull(message = "Profile ID cannot be null")
    private String profileId;

    @NotNull(message = "Route ID cannot be null")
    private String routeId;
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

    // Getters and Setters
    public String getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    public String getPrId() {
        return prId;
    }

    public void setPrId(String prId) {
        this.prId = prId;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
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
