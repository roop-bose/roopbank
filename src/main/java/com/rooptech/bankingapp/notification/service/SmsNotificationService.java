package com.rooptech.bankingapp.notification.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SmsNotificationService {
    public void sendSms(String mobileNumber, String message) {
        log.info(
                "Sending SMS to mobile number: {}, message: {}",
                mobileNumber,
                message
        );
        // SMS provider API will come here
    }
}
