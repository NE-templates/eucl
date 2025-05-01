package com.trex.eucl.services.impl;

import com.trex.eucl.entities.Notification;
import com.trex.eucl.entities.PurchasedToken;
import com.trex.eucl.repository.NotificationRepository;
import com.trex.eucl.repository.PurchasedTokensRepo;
import com.trex.eucl.services.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService implements INotificationService {

    private final PurchasedTokensRepo purchasedTokensRepo;
    private final NotificationRepository notificationRepository;
    private final EmailServiceImpl emailService;

    @Override
    public void generateExpiryNotifications() {

        List<PurchasedToken> tokens = purchasedTokensRepo.findTokensExpiringInNextFiveHours();

        for (PurchasedToken token : tokens) {
            String ownerName = token.getMeter().getOwner().getNames();
            String meterNumber = token.getMeter().getMeterNumber();
            String message = String.format("Dear %s, REG is pleased to remind you that the token in the %s is going to expire in 5 hours. Please purchase a new token.", ownerName, meterNumber);

            Notification notification = Notification.builder()
                    .meter(token.getMeter())
                    .message(message)
                    .build();

            notificationRepository.save(notification);
            emailService.sendEmail(token.getMeter().getOwner().getEmail(), "Token Expiry Notification", message);
        }

    }
}
