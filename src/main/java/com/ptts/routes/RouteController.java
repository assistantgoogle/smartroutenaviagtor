package com.ptts.routes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.ptts.device.Device;
import com.ptts.device.DeviceRepository;
import com.ptts.device.DeviceService;
import com.ptts.device.MapDeviceVehicle;
import com.ptts.driver.Driver;
import com.ptts.driver.DriverService;
import com.ptts.stops.StopLocation;
import com.ptts.vehicle.Vehicle;
import com.ptts.vehicle.VehicleService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.Valid;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    private static final Logger LOG = LoggerFactory.getLogger(RouteController.class);

    @Autowired
    private RouteServices routeService;
    @Autowired
    private DriverService driverService;

    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private DeviceService deviceService;
    
 // Fetch a route by ID
    @GetMapping("/{institutionId}/{routeId}")
    public ResponseEntity<Routes> getRouteById(@PathVariable("institutionId") String institutionId,
                                               @PathVariable("routeId") String routeId, @Valid @RequestHeader("x-token-id") String tokenId) {
        LOG.info("Fetching route for institutionId: {} and routeId: {}", institutionId, routeId);

        Routes route = routeService.getRouteById(routeId, institutionId);

        if (route == null) {
            LOG.error("Route not found for routeId: {}", routeId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Fetch stops for this route
        List<StopLocation> stops = routeService.getStopsByRouteId(routeId);
        route.setRouteStopList(stops);

        LOG.info("Route found: {} with {} stops", routeId, stops.size());
        return ResponseEntity.ok(route);
    }
    @GetMapping("/{institutionId}")
    public ResponseEntity<Map<String, Object>> getAllDetails(
        @PathVariable("institutionId") String institutionId,
        @Valid @RequestHeader("x-token-id") String tokenId) {

        LOG.info("Fetching all route details for institutionId: {}", institutionId);

        // Fetch all RouteDriverVehicleMappings for the given institutionId
        List<RouteDriverVehicleMapping> driverVehicleRoutes = routeService.getDriverVehicleRoutesByInstitutionId(institutionId);

        if (driverVehicleRoutes.isEmpty()) {
            LOG.warn("No route-driver-vehicle mappings found for institutionId: {}", institutionId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "No route-driver-vehicle mappings found", "status", HttpStatus.NOT_FOUND.value()));
        }

        // Enhanced response with more structured data
        List<Map<String, Object>> formattedMappings = new ArrayList<>();

        for (RouteDriverVehicleMapping mapping : driverVehicleRoutes) {
            Map<String, Object> mappingDetails = new LinkedHashMap<>();
            
          
            mappingDetails.put("mvrdId", mapping.getMvrdId());
            mappingDetails.put("routeId", mapping.getRouteId());
            mappingDetails.put("driverId", mapping.getDriverId());
            mappingDetails.put("vehicleId", mapping.getVehicleId());
            mappingDetails.put("effectiveDate", mapping.getEffectiveDate());
            mappingDetails.put("expiryDate", mapping.getExpiryDate());
            mappingDetails.put("isActive", mapping.getIsActive());

            // Fetch and add route details with institutionId first
            Routes route = routeService.getRouteById(mapping.getRouteId(), institutionId);
            if (route != null) {
                Map<String, Object> routeDetails = new LinkedHashMap<>();
                routeDetails.put("routeId", route.getRouteId());
                routeDetails.put("routeName", route.getRouteName());
                routeDetails.put("startPoint", route.getStartPoint());
                routeDetails.put("endPoint", route.getEndPoint());
                routeDetails.put("distance", route.getDistance());
                routeDetails.put("estimatedTime", route.getEstimatedTime());
                routeDetails.put("startCoordinates", Map.of("lat", 12.986827124108899, "lng", 77.54024586414629)); // Static lat-lng
                routeDetails.put("endCoordinates", Map.of("lat", 12.990559331136799, "lng", 77.53800758944871));  // Static lat-lng

                
                mappingDetails.put("route", routeDetails);
            }

            // Fetch and add driver details with institutionId first
            if (mapping.getDriverId() != null && !mapping.getDriverId().isEmpty()) {
                Driver driver = driverService.getDriverById(mapping.getDriverId(), institutionId);
                if (driver != null) {
                    Map<String, Object> driverDetails = new LinkedHashMap<>();
                    driverDetails.put("driverId", driver.getDriverId());
                    driverDetails.put("fName", driver.getfName());
                    driverDetails.put("lName", driver.getlName());
                    driverDetails.put("licenseNo", driver.getLicenseNo());
                    driverDetails.put("status", driver.getStatus());
                    mappingDetails.put("driver", driverDetails);
                }
            }
            

            // Fetch and add vehicle details with institutionId first
            if (mapping.getVehicleId() != null && !mapping.getVehicleId().isEmpty()) {
                Vehicle vehicle = vehicleService.getVehicleById(mapping.getVehicleId(), institutionId);
                if (vehicle != null) {
                    Map<String, Object> vehicleDetails = new LinkedHashMap<>();
                    vehicleDetails.put("vehicleId", vehicle.getVehicleId());
                    vehicleDetails.put("vehicleNumber", vehicle.getVehicleNumber());
                    vehicleDetails.put("vehicleType", vehicle.getVehicleType());
                    vehicleDetails.put("vehicleBrand", vehicle.getVehicleBrand());
                    vehicleDetails.put("vehicleModel", vehicle.getVehicleModel());
                    
                    // Fetch and add associated device details
                    Device device = deviceService.getDeviceByVehicleId(vehicle.getVehicleId());
                    if (device != null) {
                        Map<String, Object> deviceDetails = new LinkedHashMap<>();
                        deviceDetails.put("deviceId", device.getDeviceId());
                        deviceDetails.put("deviceName", device.getDeviceName());
                        
                        vehicleDetails.put("device", deviceDetails);
                    }

                    mappingDetails.put("vehicle", vehicleDetails);
                }
            }

            formattedMappings.add(mappingDetails);
        }

        // Build the response with institution_id at the top level
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("institution_id", institutionId);  // Place institution_id here
        response.put("routeDriverVehicleMappings", formattedMappings);

        LOG.info("Successfully fetched {} route-driver-vehicle mappings", driverVehicleRoutes.size());
        return ResponseEntity.ok(response);
        
    }


    // Get all routes
    @GetMapping
    public ResponseEntity<List<Routes>> getAllRoutes() {
        LOG.info("Fetching all routes");
        List<Routes> routes = routeService.getAllRoutes();
        return ResponseEntity.ok(routes);
    }

    // Update a route
    @PutMapping("/update/{institutionId}/{routeId}")
    public ResponseEntity<String> updateRoute(@PathVariable("institutionId") String institutionId,
                                              @PathVariable("routeId") String routeId, 
                                              @Valid @RequestBody Routes updatedRoute, @Valid @RequestHeader("x-token-id") String tokenId) {
        LOG.info("Updating route for institutionId: {} and routeId: {}", institutionId, routeId);
        
        Routes existingRoute = routeService.getRouteById(routeId, institutionId);
        if (existingRoute == null) {
            LOG.error("Route not found for routeId: {}", routeId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Route not found");
        }

        int result = routeService.updateRoute(routeId, updatedRoute);
        if (result > 0) {
            // Delete existing stops by routeId and institutionId
            routeService.deleteStopsByRouteId(routeId, institutionId);

            // Save new stops if any
            List<StopLocation> stopLocations = updatedRoute.getRouteStopList();
            if (stopLocations != null && !stopLocations.isEmpty()) {
                for (StopLocation stop : stopLocations) {
                    routeService.saveStop(stop, routeId); 
                }
            }
            LOG.info("Route and stops updated successfully for routeId: {}", routeId);
            return ResponseEntity.ok("Route and stops updated successfully");
        } else {
            LOG.error("Failed to update route for routeId: {}", routeId);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update route");
        }
    }

    // Create a new route
    @PostMapping("/add")
    @Transactional
    public ResponseEntity<String> createRoute(@RequestBody Routes route, @Valid @RequestHeader("x-token-id") String tokenId) {
        LOG.info("Creating new route");

        int result = routeService.createRoute(route);

        if (result > 0) {
            String routeId = route.getRouteId();

            // Save stops associated with this new route
            List<StopLocation> stopLocations = route.getRouteStopList();
            if (stopLocations != null && !stopLocations.isEmpty()) {
                for (StopLocation stop : stopLocations) {
                    int saveStopResult = routeService.saveStop(stop, routeId);
                    if (saveStopResult <= 0) {
                        LOG.error("Failed to save stop for routeId: {}", routeId);
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save stop for route");
                    }
                }
            }
            LOG.info("Route and stops created successfully for routeId: {}", routeId);
            return ResponseEntity.ok("Route and stops created successfully");
        } else {
            LOG.error("Failed to create route");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create route");
        }
    }

    // Create mapping for route, driver, and vehicle
    @PostMapping("/add/driver-vehicle")
    @Transactional
    public ResponseEntity<String> createRouteDriverVehicleMapping(@RequestBody RouteDriverVehicleMapping routeMapping, @Valid @RequestHeader("x-token-id") String tokenId) {
        LOG.info("Creating route-driver-vehicle mapping");

        // Validate input
        if (routeMapping.getRouteId() == null || routeMapping.getDriverId() == null || routeMapping.getVehicleId() == null || routeMapping.getInstitutionId() == null) {
            LOG.warn("Missing required fields in mapping: Route ID, Driver ID, Vehicle ID, institution_id must be provided.");
            return ResponseEntity.badRequest().body("Route ID, Driver ID, and Vehicle ID, institution_id must be provided.");
        }

        int result = routeService.createRouteDriverVehicleMapping(routeMapping);
        if (result > 0) {
            LOG.info("Route, Driver, and Vehicle mapping created successfully");
            return ResponseEntity.ok("Route, Driver, and Vehicle mapping created successfully");
        } else {
            LOG.error("Failed to create the mapping");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create the mapping");
        }
    }

    // Delete a route by ID
    @Transactional
    @GetMapping("/delete/{institutionId}/{routeId}")
    public ResponseEntity<String> deleteRoute(@PathVariable("institutionId") String institutionId, 
                                              @PathVariable("routeId") String routeId, @Valid @RequestHeader("x-token-id") String tokenId) {
        LOG.info("Deleting route for institutionId: {} and routeId: {}", institutionId, routeId);

        Routes existingRoute = routeService.getRouteById(routeId, institutionId);
        if (existingRoute == null) {
            LOG.error("Route not found for routeId: {}", routeId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Route not found");
        }

        routeService.deleteStopsByRouteId(routeId, institutionId);
        int result = routeService.deleteRoute(routeId, institutionId);

        if (result > 0) {
            LOG.info("Route and associated stops deleted successfully for routeId: {}", routeId);
            return ResponseEntity.ok("Route and associated stops deleted successfully");
        } else {
            LOG.error("Failed to delete route for routeId: {}", routeId);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete route");
        }
    }
}
