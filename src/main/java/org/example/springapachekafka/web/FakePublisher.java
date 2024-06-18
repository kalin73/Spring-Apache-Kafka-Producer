package org.example.springapachekafka.web;

import org.example.springapachekafka.model.ExchangeRatesDTO;
import org.example.springapachekafka.service.KafkaPublicationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
public class FakePublisher {
    private final KafkaPublicationService kafkaPublicationService;

    public FakePublisher(KafkaPublicationService kafkaPublicationService) {
        this.kafkaPublicationService = kafkaPublicationService;
    }

    @GetMapping("/publish")
    public String publish() {
        var toPublish = new ExchangeRatesDTO("USD", System.currentTimeMillis(), Map.of("BGN", BigDecimal.valueOf(1.840515)
                , "EUR", BigDecimal.valueOf(0.937668)));

        kafkaPublicationService.publishExchangeRate(toPublish);

        return "OK";
    }
}
