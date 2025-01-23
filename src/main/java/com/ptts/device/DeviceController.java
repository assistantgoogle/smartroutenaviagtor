package com.ptts.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.ptts.appuser.AppUserController;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private AppUserController appUserInterface;

    // Get a device by ID
    @GetMapping("/{deviceId}")
    public ResponseEntity<Device> getDeviceById(@Valid @PathVariable("deviceId") String deviceId,
                                                @RequestHeader("x-token-id") String tokenId) {
        appUserInterface.authorizeSysuser(tokenId);
        try {
            Device device = deviceService.getDeviceById(deviceId);
            if (device != null) {
                return new ResponseEntity<>(device, HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Device not found");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    // Get all devices
    @GetMapping("/all")
    public ResponseEntity<List<Device>> getAllDevices() {
        try {
            List<Device> devices = deviceService.getAllDevices();
            return new ResponseEntity<>(devices, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    // Create or update a device
    @PostMapping("/add")
    public ResponseEntity<Device> createOrUpdateDevice(@Valid @RequestBody Device device,
                                                       @RequestHeader("x-token-id") String tokenId) {
        appUserInterface.authorizeSysuser(tokenId);
        try {
            String result = deviceService.saveOrUpdateDevice(device);
            if (result.contains("success")) {
                return new ResponseEntity<>(device, HttpStatus.CREATED);
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to save/update device");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    // Delete a device
    @DeleteMapping("/delete/{deviceId}")
    public ResponseEntity<String> deleteDevice(@PathVariable("deviceId") String deviceId) {
        try {
            String result = deviceService.deleteDeviceByDeviceId(deviceId);
            if (result.contains("success")) {
                return new ResponseEntity<>("Device deleted successfully", HttpStatus.NO_CONTENT);
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete device");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    // Get a device-vehicle mapping by ID
    @GetMapping("/mappings/{deviceId}/{vehicleId}/{institutionId}")
    public ResponseEntity<MapDeviceVehicle> getMappingById(@PathVariable String deviceId,
                                                           @PathVariable String vehicleId,
                                                           @PathVariable String institutionId) {
        try {
            MapDeviceVehicle mapping = deviceService.findMappingById(deviceId, vehicleId, institutionId);
            if (mapping != null) {
                return new ResponseEntity<>(mapping, HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Mapping not found");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    // Get all device-vehicle mappings
    @GetMapping("/mappings/all")
    public ResponseEntity<List<MapDeviceVehicle>> getAllMappings() {
        try {
            List<MapDeviceVehicle> mappings = deviceService.getAllMappings();
            return new ResponseEntity<>(mappings, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    // Create a new device-vehicle mapping
    @PostMapping("/mappings/add")
    public ResponseEntity<String> createMapping(@Valid @RequestBody MapDeviceVehicle mapping) {
        try {
            String result = deviceService.createMapping(mapping);
            if (result.contains("success")) {
                return new ResponseEntity<>("Mapping created successfully", HttpStatus.CREATED);
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create mapping");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

   
}
