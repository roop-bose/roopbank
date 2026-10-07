package com.rooptech.bankingapp.notification.consumer;

import com.rooptech.bankingapp.notification.dto.TransactionEvent;
import com.rooptech.bankingapp.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionNotificationConsumer {
    private final NotificationService notificationService;
    private static final String TOPIC = "transaction-events";
    @KafkaListener(
            topics = TOPIC,
            groupId = "notification-group"
    )
    public void consumeTransactionEvent(TransactionEvent event) {

        log.info(
                "Transaction event received: transactionId={}, accountId={}, type={}, amount={}",
                event.getTransactionId(),
                event.getAccountId(),
                event.getTransactionType(),
                event.getAmount()
        );

        // Next step:
         notificationService.sendTransactionNotification(event);
    }
}
