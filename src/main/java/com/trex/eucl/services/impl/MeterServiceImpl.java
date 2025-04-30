package com.trex.eucl.services.impl;

import com.trex.eucl.entities.Meter;
import com.trex.eucl.entities.User;
import com.trex.eucl.exceptions.BadRequestException;
import com.trex.eucl.repository.MeterRepository;
import com.trex.eucl.repository.UserRepository;
import com.trex.eucl.request.MeterRequest;
import com.trex.eucl.services.IMeterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MeterServiceImpl implements IMeterService {

    private final MeterRepository meterRepository;
    private final UserRepository userRepository;

    @Override
    public Meter registerMeter(MeterRequest meterRequest) {

        Optional<Meter> existingMeter = meterRepository.findByMeterNumber(meterRequest.getMeterNumber());

        if(existingMeter.isPresent()) {
            throw new BadRequestException(String.format("This meter number %s is already registered", meterRequest.getMeterNumber()));
        }

        Optional<User> meterOwner = userRepository.findById(meterRequest.getUserId());

        if(meterOwner.isEmpty()) throw new BadRequestException(String.format("The owner of this meter, which is user id: %s, doesn't exist", meterRequest.getUserId()));

        Meter newMeter = Meter.builder()
                .meterNumber(meterRequest.getMeterNumber())
                .owner(meterOwner.get())
                .build();

        return meterRepository.save(newMeter);
    }

    @Override
    public Meter getMeter(UUID meterId) {

        Optional<Meter> meter = meterRepository.findById(meterId);

        if(meter.isEmpty()) throw new BadRequestException(String.format("This meter with Id %s, doesn't exist", meterId));

        return meter.get();
    }

    @Override
    public Meter getMeterByNumber(String meterNumber) {
        Optional<Meter> meter = meterRepository.findByMeterNumber(meterNumber);

        if(meter.isEmpty()) throw new BadRequestException(String.format("This meter with number %s, doesn't exist", meterNumber));

        return meter.get();
    }

    @Override
    public List<Meter> getAllMeters() {
        return meterRepository.findAll();
    }
}
