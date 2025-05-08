package com.trex.eucl.repository;

import com.trex.eucl.entities.Meter;
import com.trex.eucl.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MeterRepository extends JpaRepository<Meter, UUID> {

    Optional<Meter> findByMeterNumber(String meterNumber);

    List<Meter> findAllByOwner_Id(UUID id);

}
