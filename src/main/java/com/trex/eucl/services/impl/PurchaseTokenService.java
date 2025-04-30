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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public PurchasedToken getPurchasedToken(UUID purchaseId) {
        Optional<PurchasedToken> purchasedToken = purchasedTokensRepo.findById(purchaseId);

        if(purchasedToken.isEmpty()) throw new BadRequestException(String.format("The purchase with id %s doesn't exist", purchaseId));

        return purchasedToken.get();
    }

    @Override
    public PurchasedToken getPurchasedTokenByToken(String token) {
        Optional<PurchasedToken> purchasedToken = purchasedTokensRepo.findByToken(token);

        if(purchasedToken.isEmpty()) throw new BadRequestException(String.format("This token %s doesn't exist", token));

        return purchasedToken.get();
    }

    @Override
    public List<PurchasedToken> getPurchasedTokensByMeter(String meterNumber) {

        meterRepository.findByMeterNumber(meterNumber)
                .orElseThrow(() -> new BadRequestException(
                        String.format("Meter with %s number doesn't exist", meterNumber)));

        Optional<List<PurchasedToken>> purchasedToken = purchasedTokensRepo.findAllByMeter_MeterNumber(meterNumber);

        return purchasedToken.orElseGet(ArrayList::new);

    }

    @Override
    public List<PurchasedToken> getAllPurchasedTokens() {
        return purchasedTokensRepo.findAll();
    }

    @Override
    public String useTokenPerDay(String token) {
        PurchasedToken purchasedToken = purchasedTokensRepo.findByToken(token)
                .orElseThrow(() -> new BadRequestException(
                        String.format("The purchase corresponding to this token %s doesn't exist", token)));

        if (purchasedToken.getTokenStatus() == TokenStatus.EXPIRED) {
            throw new BadRequestException(String.format("Token %s is expired! Buy another one.", token));
        }

        int remainingDays = purchasedToken.getTokenValueDays();

        if (remainingDays <= 0) {
            purchasedToken = PurchasedToken.builder()
                    .id(purchasedToken.getId())
                    .meter(purchasedToken.getMeter())
                    .token(purchasedToken.getToken())
                    .tokenStatus(TokenStatus.EXPIRED)
                    .tokenValueDays(0)
                    .purchaseDate(purchasedToken.getPurchaseDate())
                    .amount(purchasedToken.getAmount())
                    .build();
            purchasedTokensRepo.save(purchasedToken);
            throw new BadRequestException(String.format("Token %s has no remaining days and is now expired", token));
        }

        int newRemainingDays = remainingDays - 1;
        TokenStatus newStatus = (newRemainingDays == 0) ? TokenStatus.EXPIRED : TokenStatus.USED;

        purchasedToken = PurchasedToken.builder()
                .id(purchasedToken.getId())
                .meter(purchasedToken.getMeter())
                .token(purchasedToken.getToken())
                .tokenStatus(newStatus)
                .tokenValueDays(newRemainingDays)
                .purchaseDate(purchasedToken.getPurchaseDate())
                .amount(purchasedToken.getAmount())
                .build();

        purchasedTokensRepo.save(purchasedToken);

        return String.format("Token %s used for one day. Remaining days: %d", token, newRemainingDays);
    }

}
