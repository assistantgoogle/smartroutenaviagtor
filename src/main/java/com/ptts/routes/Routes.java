package com.ptts.routes;

import java.util.List;

import com.ptts.stops.StopLocation;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Routes {

    @NotNull
    @Size(min = 1, max = 50)
    private String institutionId; 

    @NotNull
    @Size(min = 1, max = 50)
    private String routeId; 

    @NotNull
    @Size(min = 1, max = 100)
    private String routeName;

    @NotNull
    @Size(min = 1, max = 100)
    private String startPoint;

    @NotNull
    @Size(min = 1, max = 100)
    private String endPoint;

    @NotNull
    private String distance;

    @NotNull(message = "Estimated Time cannot be null")
    @Pattern(regexp = "^\\d{1,2}:\\d{2}$", message = "Estimated time must be in HH:MM format")
    private String estimatedTime;

    private List<StopLocation> routeStopList;

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

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public List<StopLocation> getRouteStopList() {
        return routeStopList;
    }

    public void setRouteStopList(List<StopLocation> routeStopList) {
        this.routeStopList = routeStopList;
    }
}
