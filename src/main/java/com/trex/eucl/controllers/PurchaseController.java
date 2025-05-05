package com.trex.eucl.controllers;

import com.trex.eucl.entities.PurchasedToken;
import com.trex.eucl.request.PurchaseToken;
import com.trex.eucl.services.impl.PurchaseTokenService;
import com.trex.eucl.response.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Token Purchases", description = "Endpoints for purchasing and managing tokens")
@RestController
@RequestMapping("/api/v1/purchase")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseTokenService purchaseTokenService;

    @Operation(summary = "Purchase token", description = "Create a new token purchase")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/")
    public ResponseEntity<APIResponse<PurchasedToken>> purchaseToken(@Valid @RequestBody PurchaseToken purchaseToken) {
        PurchasedToken purchasedToken = purchaseTokenService.purchaseToken(purchaseToken);
        return new APIResponse<>("Token purchased successfully", HttpStatus.CREATED, purchasedToken).toResponseEntity();
    }

    @Operation(summary = "Get all purchases", description = "Retrieve all purchased tokens")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/all")
    public ResponseEntity<APIResponse<List<PurchasedToken>>> purchaseToken() {
        List<PurchasedToken> purchasedTokens = purchaseTokenService.getAllPurchasedTokens();
        return new APIResponse<>("All purchases retrieved successfully", HttpStatus.OK, purchasedTokens).toResponseEntity();
    }

    @Operation(summary = "Get purchase by ID", description = "Retrieve purchased token by its UUID")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<PurchasedToken>> getPurchasedToken(@PathVariable UUID id) {
        PurchasedToken purchasedToken = purchaseTokenService.getPurchasedToken(id);
        return new APIResponse<>("Purchase retrieved successfully", HttpStatus.OK, purchasedToken).toResponseEntity();
    }

    @Operation(summary = "Get purchase by token string", description = "Retrieve a token purchase using its string value")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/get-by-token/{token}")
    public ResponseEntity<APIResponse<PurchasedToken>> getPurchasedTokenByToken(@PathVariable String token) {
        PurchasedToken purchasedToken = purchaseTokenService.getPurchasedTokenByToken(token);
        return new APIResponse<>("Purchase retrieved successfully", HttpStatus.OK, purchasedToken).toResponseEntity();
    }

    @Operation(summary = "Get purchases by meter number", description = "Retrieve all token purchases for a given meter number")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/all/{meterNumber}")
    public ResponseEntity<APIResponse<List<PurchasedToken>>> getPurchasedTokensByMeter(@PathVariable String meterNumber) {
        List<PurchasedToken> purchasedTokens = purchaseTokenService.getPurchasedTokensByMeter(meterNumber);
        return new APIResponse<>("Meter purchases retrieved successfully", HttpStatus.OK, purchasedTokens).toResponseEntity();
    }

    @Operation(summary = "Use token", description = "Consume one day of a token's validity")
    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/use/{token}")
    public ResponseEntity<APIResponse<String>> useTokenPerDay(@PathVariable String token) {
        String usageMessage = purchaseTokenService.useTokenPerDay(token);
        return new APIResponse<>("Token used", HttpStatus.OK, usageMessage).toResponseEntity();
    }
}
