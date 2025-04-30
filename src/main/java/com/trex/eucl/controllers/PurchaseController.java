package com.trex.eucl.controllers;

import com.trex.eucl.entities.PurchasedToken;
import com.trex.eucl.request.PurchaseToken;
import com.trex.eucl.services.impl.PurchaseTokenService;
import com.trex.eucl.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
