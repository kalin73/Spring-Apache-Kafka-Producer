package org.example.springapachekafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    NewTopic exRateTopic() {
        return TopicBuilder.name("exchange_rates")
                .partitions(2)
                .compact()
                .build();
    }
}
