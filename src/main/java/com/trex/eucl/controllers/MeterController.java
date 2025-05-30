package com.trex.eucl.controllers;

import com.trex.eucl.entities.Meter;
import com.trex.eucl.request.MeterRequest;
import com.trex.eucl.services.impl.MeterServiceImpl;
import com.trex.eucl.response.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Meters", description = "Endpoints for managing electricity meters")
@RestController
@RequestMapping("/api/v1/meter")
@RequiredArgsConstructor
public class MeterController {

    private final MeterServiceImpl meterService;

    @Operation(summary = "Register meter", description = "Registers a new meter")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/register")
    public ResponseEntity<APIResponse<Meter>> registerMeter(@Valid @RequestBody MeterRequest meterRequest) {
        Meter registerMeter = meterService.registerMeter(meterRequest);
        return new APIResponse<>("Meter registered successfully", HttpStatus.CREATED, registerMeter).toResponseEntity();
    }

    @Operation(summary = "Get meter by ID", description = "Fetches a meter using UUID")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{id}")
    public  ResponseEntity<APIResponse<Meter>> getMeter(@PathVariable UUID id) {
        Meter meter = meterService.getMeter(id);
        return new APIResponse<>("Meter retrieved successfully", HttpStatus.OK, meter).toResponseEntity();
    }

    @Operation(summary = "Get meter by number", description = "Fetches a meter using its number")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/number/{meterNumber}")
    public ResponseEntity<APIResponse<Meter>> getMeterByNumber(@PathVariable String meterNumber) {
        Meter meter = meterService.getMeterByNumber(meterNumber);
        return new APIResponse<>("Meter retrieved successfully", HttpStatus.OK, meter).toResponseEntity();
    }

    @Operation(summary = "Get all meters", description = "Fetches all registered meters")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/all")
    public ResponseEntity<APIResponse<List<Meter>>> getMeterByNumber() {
        List<Meter> meters = meterService.getAllMeters();
        return new APIResponse<>("All meters retrieved successfully", HttpStatus.OK, meters).toResponseEntity();
    }

    @Operation(summary = "Get user meters", description = "Fetches all user registered meters")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/user/{id}")
    public ResponseEntity<APIResponse<List<Meter>>> getMetersByUser(@PathVariable UUID id) {
        List<Meter> userMeters = meterService.getUserMeters(id);
        return new APIResponse<>("User meters retrieved successfully", HttpStatus.OK, userMeters).toResponseEntity();
    }

}
