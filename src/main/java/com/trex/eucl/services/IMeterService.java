package com.trex.eucl.services;

import com.trex.eucl.entities.Meter;
import com.trex.eucl.request.MeterRequest;

import java.util.List;
import java.util.UUID;

public interface IMeterService {

    Meter registerMeter(MeterRequest meterRequest);
    Meter getMeter(UUID meterId);
    Meter getMeterByNumber(String meterNumber);
    List<Meter> getAllMeters();

    List<Meter> getUserMeters(UUID id);

}
