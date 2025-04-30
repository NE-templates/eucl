package com.trex.eucl.services;

import com.trex.eucl.entities.PurchasedToken;
import com.trex.eucl.request.PurchaseToken;

import java.util.List;
import java.util.UUID;

public interface IPurchaseService {

    PurchasedToken purchaseToken(PurchaseToken purchaseToken);

    PurchasedToken getPurchasedToken(UUID tokenId);
    PurchasedToken getPurchasedTokenByToken(String token);
    List<PurchasedToken> getPurchasedTokensByMeter(String meterNumber);
    List<PurchasedToken> getAllPurchasedTokens();

    String useTokenPerDay(String token);

}
