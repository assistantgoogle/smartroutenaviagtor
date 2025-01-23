package com.ptts.driver;

import java.util.List;

public interface DriverService {
    Driver getDriverById(String driverId,String InstitutionId);
    List<Driver> getAllDrivers();
    int  createDriver(Driver driver);
    int updateDriver(Driver driver);
    int deleteDriver(String driverId);
}
