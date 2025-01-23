package com.ptts.appuser;

import java.util.Date;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ModelAppUser {
	

   
    private String userId; 
    @NotNull(message = "User Name cannot be null")
    @Size(min = 3, max = 20, message = "User Name must be between 3 and 20 characters")
    private String userName;

    @NotNull(message = "Password key cannot be null")
    @Size(min = 8, max = 100, message = "Password key must be at least 8 characters long")
    private String passKey;
    @NotNull(message = "Token cannot be null")
    private String token;
    @NotNull(message = "Effective date cannot be null")
    private Date effectiveDate;

    @NotNull(message = "Expiry date cannot be null")
    private Date expiryDate;

    @NotNull(message = "Create date cannot be null")
    private Date createDate;

    @NotNull(message = "Last update date cannot be null")
    private Date lastUpdateDate;

    private boolean isActive;

    // Getter and Setter for userId
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    // Getter and Setter for userName
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    // Getter and Setter for passKey
    public String getPassKey() {
        return passKey;
    }

    public void setPassKey(String passKey) {
        this.passKey = passKey;
    }

    // Getter and Setter for token
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    // Getter and Setter for effectiveDate
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    // Getter and Setter for expiryDate
    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    // Getter and Setter for createDate
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    // Getter and Setter for lastUpdateDate
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    // Getter and Setter for isActive
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
