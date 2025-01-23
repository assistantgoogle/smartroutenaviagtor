package com.ptts.device;

import java.util.Date;

public class Location {

  
    private String machineId;
    private double lat;
    private double lng;
    private Date timestamp;

   
    public Location() {}

    // Constructor
    public Location(String machineId, double lat, double lng, Date timestamp) {
        this.machineId = machineId;
        this.lat = lat;
        this.lng = lng;
        this.timestamp = timestamp;
    }

   

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
