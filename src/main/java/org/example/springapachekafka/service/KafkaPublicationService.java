package org.example.springapachekafka.service;

import static org.example.springapachekafka.config.KafkaConfig.*;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.example.springapachekafka.model.ExchangeRatesDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class KafkaPublicationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaPublicationService.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaPublicationService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishExchangeRate(ExchangeRatesDTO exchangeRatesDTO) {
        kafkaTemplate.send(EXCHANGE_RATE_TOPIC, UUID.randomUUID().toString(), exchangeRatesDTO).whenComplete((res, ex) -> {
            if (ex == null) {
                final RecordMetadata recordMetadata = res.getRecordMetadata();

                LOGGER.info("Kafka message successfully send to topic {}/ partition {}/ offset {}. Key = {}.",
                        recordMetadata.topic(),
                        recordMetadata.partition(),
                        recordMetadata.offset(),
                        res.getProducerRecord().key());

            } else {
                LOGGER.error("Problem with the publication to kafka.", ex);
            }
        });
    }
}
