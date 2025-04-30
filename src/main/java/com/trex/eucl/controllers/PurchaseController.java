package com.trex.eucl.controllers;

import com.trex.eucl.entities.PurchasedToken;
import com.trex.eucl.request.PurchaseToken;
import com.trex.eucl.services.impl.PurchaseTokenService;
import com.trex.eucl.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/purchase")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseTokenService purchaseTokenService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse<PurchasedToken>> purchaseToken(@Valid @RequestBody PurchaseToken purchaseToken) {
        PurchasedToken purchasedToken = purchaseTokenService.purchaseToken(purchaseToken);
        return new ApiResponse<>("Token purchased successfully", HttpStatus.CREATED, purchasedToken).toResponseEntity();
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<PurchasedToken>>> purchaseToken() {
        List<PurchasedToken> purchasedTokens = purchaseTokenService.getAllPurchasedTokens();
        return new ApiResponse<>("All purchases retrieved successfully", HttpStatus.OK, purchasedTokens).toResponseEntity();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PurchasedToken>> getPurchasedToken(@PathVariable UUID id) {
        PurchasedToken purchasedToken = purchaseTokenService.getPurchasedToken(id);
        return new ApiResponse<>("Purchase retrieved successfully", HttpStatus.OK, purchasedToken).toResponseEntity();
    }

    @GetMapping("/get-by-token/{token}")
    public ResponseEntity<ApiResponse<PurchasedToken>> getPurchasedTokenByToken(@PathVariable String token) {
        PurchasedToken purchasedToken = purchaseTokenService.getPurchasedTokenByToken(token);
        return new ApiResponse<>("Purchase retrieved successfully", HttpStatus.OK, purchasedToken).toResponseEntity();
    }

    @GetMapping("/all/{meterNumber}")
    public ResponseEntity<ApiResponse<List<PurchasedToken>>> getPurchasedTokensByMeter(@PathVariable String meterNumber) {
        List<PurchasedToken> purchasedTokens = purchaseTokenService.getPurchasedTokensByMeter(meterNumber);
        return new ApiResponse<>("Meter purchases retrieved successfully", HttpStatus.OK, purchasedTokens).toResponseEntity();
    }

    @PatchMapping("/use/{token}")
    public ResponseEntity<ApiResponse<String>> useTokenPerDay(@PathVariable String token) {
        String usageMessage = purchaseTokenService.useTokenPerDay(token);
        return new ApiResponse<>("Token used", HttpStatus.OK, usageMessage).toResponseEntity();
    }
}
