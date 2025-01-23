package com.ptts.device;

public class Device {
    private int id;              
    private String deviceId;      
    private String deviceName;   
    private String deviceType;    
    private boolean isActive;    
    private String deviceModel; 
    public String getInstitutionId() {
		return institutionId;
	}


	public void setInstitutionId(String institutionId) {
		this.institutionId = institutionId;
		
	}

	private String institutionId;
    

    // Getters and Setters
    public int getId() {
        return id;
    }
    

    public void setId(int id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }
}
