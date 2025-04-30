package com.trex.eucl.services;

import com.trex.eucl.entities.PurchasedToken;
import com.trex.eucl.request.PurchaseToken;

import java.util.List;
import java.util.UUID;

public interface IPurchaseService {

    PurchasedToken purchaseToken(PurchaseToken purchaseToken);

    PurchaseToken getPurchasedToken(UUID tokenId);
    PurchaseToken getPurchasedTokenByToken(String token);
    List<PurchaseToken> getPurchasedTokensByMeter(String meterNumber);
    List<PurchaseToken> getAllPurchasedTokens();

}
