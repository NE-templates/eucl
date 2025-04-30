package com.trex.eucl.services.impl;

import com.trex.eucl.entities.Meter;
import com.trex.eucl.entities.PurchasedToken;
import com.trex.eucl.enums.TokenStatus;
import com.trex.eucl.exceptions.BadRequestException;
import com.trex.eucl.repository.MeterRepository;
import com.trex.eucl.repository.PurchasedTokensRepo;
import com.trex.eucl.request.PurchaseToken;
import com.trex.eucl.services.IPurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PurchaseTokenService implements IPurchaseService {

    private final PurchasedTokensRepo purchasedTokensRepo;
    private final MeterRepository meterRepository;

    @Override
    public PurchasedToken purchaseToken(PurchaseToken purchaseToken) {

        Meter meter = meterRepository.findByMeterNumber(purchaseToken.getMeterNumber())
                .orElseThrow(() -> new BadRequestException(
                        String.format("Meter with %s number doesn't exist", purchaseToken.getMeterNumber())));

        int amount = purchaseToken.getAmount();

        if (amount < 100 || amount % 100 != 0) {
            throw new BadRequestException("Amount must be at least 100 RWF and a multiple of 100.");
        }

        int days = amount / 100;
        if (days > 1825) {
            throw new BadRequestException("Cannot buy more than 1825 days (5 years) of electricity.");
        }

        String input = meter.getMeterNumber() + amount + System.currentTimeMillis();
        String token = String.valueOf(Math.abs(input.hashCode()));
        token = String.format("%016d", Long.parseLong(token)).substring(0, 16);

        PurchasedToken purchasedToken = PurchasedToken.builder()
                .meter(meter)
                .amount(amount)
                .token(token)
                .tokenValueDays(days)
                .tokenStatus(TokenStatus.NEW)
                .build();

        return purchasedTokensRepo.save(purchasedToken);
    }


    @Override
    public PurchaseToken getPurchasedToken(UUID tokenId) {
        return null;
    }

    @Override
    public PurchaseToken getPurchasedTokenByToken(String token) {
        return null;
    }

    @Override
    public List<PurchaseToken> getPurchasedTokensByMeter(String meterNumber) {
        return null;
    }

    @Override
    public List<PurchaseToken> getAllPurchasedTokens() {
        return null;
    }
}
