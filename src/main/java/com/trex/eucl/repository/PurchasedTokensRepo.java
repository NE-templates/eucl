package com.trex.eucl.repository;

import com.trex.eucl.entities.PurchasedToken;
import com.trex.eucl.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PurchasedTokensRepo extends JpaRepository<PurchasedToken, UUID> {

    Optional<List<PurchasedToken>> findAllByMeter_MeterNumber(String meterNumber);
    Optional<PurchasedToken> findByToken(String token);

    @Query(value = """
    SELECT * FROM purchased_tokens pt
    WHERE pt.token_status <> 'EXPIRED'
    AND NOW() >= (pt.purchase_date + (pt.token_value_days || ' days')::INTERVAL - INTERVAL '5 hours')
    """, nativeQuery = true)
    List<PurchasedToken> findTokensExpiringInNextFiveHours();

}
