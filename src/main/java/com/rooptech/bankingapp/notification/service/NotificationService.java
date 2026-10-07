package com.rooptech.bankingapp.notification.service;

import com.rooptech.bankingapp.account.entity.Customer;
import com.rooptech.bankingapp.account.exception.ResourceNotFoundException;
import com.rooptech.bankingapp.account.repository.CustomerRepository;
import com.rooptech.bankingapp.notification.dto.TransactionEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

    private final CustomerRepository customerRepository;
    private final SmsNotificationService smsNotificationService;

    public void sendTransactionNotification(TransactionEvent event) {

        Customer customer = customerRepository
                .findByAccountAccountId(event.getAccountId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer",
                                "accountId",
                                event.getAccountId().toString()
                        )
                );

        String message = String.format(
                "Transaction successful: %s of ₹%s for account %d",
                event.getTransactionType(),
                event.getAmount(),
                event.getAccountId()
        );

        smsNotificationService.sendSms(
                customer.getMobileNumber(),
                message
        );
    }

//    public void sendTransactionNotification(TransactionEvent event) {
//
//        log.info(
//                "Notification sent: Transaction {}, Account {}, Type {}, Amount {}",
//                event.getTransactionId(),
//                event.getAccountId(),
//                event.getTransactionType(),
//                event.getAmount()
//        );
//    }

}
