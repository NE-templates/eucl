package com.trex.eucl.controllers;

import com.trex.eucl.entities.Meter;
import com.trex.eucl.request.MeterRequest;
import com.trex.eucl.services.impl.MeterServiceImpl;
import com.trex.eucl.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/meter")
@RequiredArgsConstructor
public class MeterController {

    private final MeterServiceImpl meterService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Meter>> registerMeter(@Valid @RequestBody MeterRequest meterRequest) {
        Meter registerMeter = meterService.registerMeter(meterRequest);
        return new ApiResponse<>("Meter registered successfully", HttpStatus.CREATED, registerMeter).toResponseEntity();
    }

    @GetMapping("/{id}")
    public  ResponseEntity<ApiResponse<Meter>> getMeter(@PathVariable UUID id) {
        Meter meter = meterService.getMeter(id);
        return new ApiResponse<>("Meter retrieved successfully", HttpStatus.OK, meter).toResponseEntity();
    }

    @GetMapping("/number/{meterNumber}")
    public ResponseEntity<ApiResponse<Meter>> getMeterByNumber(@PathVariable String meterNumber) {
        Meter meter = meterService.getMeterByNumber(meterNumber);
        return new ApiResponse<>("Meter retrieved successfully", HttpStatus.OK, meter).toResponseEntity();
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Meter>>> getMeterByNumber() {
        List<Meter> meters = meterService.getAllMeters();
        return new ApiResponse<>("All meters retrieved successfully", HttpStatus.OK, meters).toResponseEntity();
    }

}
