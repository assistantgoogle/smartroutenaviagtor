package com.ptts.stops;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

public class StopLocation {

    @NotNull
    @Size(min = 1, max = 50)
    private String institutionId; 

    @NotNull
    private String routeId;

    private String stopId; 

    @NotNull
    @Size(min = 1, max = 100)
    private String stopName;

    @NotNull
    @Size(min = 1, max = 200)
    private String stopLocation;

    @NotNull
    @Pattern(regexp = "^\\d{1,2}:\\d{2}$", message = "Arrival Time must be in HH:MM format")
    private String arrivalTime;

    @NotNull(message = "Departure Time cannot be null")
    @Pattern(regexp = "^\\d{1,2}:\\d{2}$", message = "Departure Time must be in HH:MM format")
    private String departureTime;


    

    @Size(max = 200)
    private String stopAddress;
   
    @Size(max = 200)
    private String stopAddress1;
    
    @Size(max = 200)
    private String stopAddress2;

    @DecimalMin(value = "-90.0", message = "Latitude must be between -90 and 90")
    @DecimalMax(value = "90.0", message = "Latitude must be between -90 and 90")
    private Double latitude;

    @DecimalMin(value = "-180.0", message = "Longitude must be between -180 and 180")
    @DecimalMax(value = "180.0", message = "Longitude must be between -180 and 180")
    private Double longitude;

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

    public String getStopId() {
        return stopId;
    }

    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public String getStopLocation() {
        return stopLocation;
    }

    public void setStopLocation(String stopLocation) {
        this.stopLocation = stopLocation;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

  

    public String getStopAddress() {
        return stopAddress;
    }

    public void setStopAddress(String stopAddress) {
        this.stopAddress = stopAddress;
    }

    public String getStopAddress1() {
        return stopAddress1;
    }

    public void setStopAddress1(String stopAddress1) {
        this.stopAddress1 = stopAddress1;
    }

    public String getStopAddress2() {
        return stopAddress2;
    }

    public void setStopAddress2(String stopAddress2) {
        this.stopAddress2 = stopAddress2;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
