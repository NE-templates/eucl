package com.trex.eucl.repository;

import com.trex.eucl.entities.PurchasedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PurchasedTokensRepo extends JpaRepository<PurchasedToken, UUID> {
}
