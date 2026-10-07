package com.rooptech.bankingapp.notification.producer;
import com.rooptech.bankingapp.notification.dto.TransactionEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionEventProducer {
    private static final String TOPIC = "transaction-events";
    private final KafkaTemplate<String , TransactionEvent> kafkaTemplate;
    public void sendTransactionEvent(TransactionEvent event) {

        kafkaTemplate.send(
                TOPIC,
                String.valueOf(event.getAccountId()),
                event
        ).whenComplete((result, ex) -> {

            if (ex != null) {
                log.error("Kafka message failed", ex);
            } else {
                log.info(
                        "Kafka message sent successfully: topic={}, partition={}, offset={}",
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset()
                );
            }
        });
    }
}
