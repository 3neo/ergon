package com.jewel.ergon.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {


//    private final KafkaProperties kafkaProperties;
//    private final SslBundles sslBundles;
//
//    public KafkaProducerConfig(KafkaProperties kafkaProperties, SslBundles sslBundles) {
//        this.kafkaProperties = kafkaProperties;
//        this.sslBundles = sslBundles;
//    }
//
//    @Bean
//    public ProducerFactory<String, String> producerFactory() {
//        Map<String, Object> config = new HashMap<>(
//                kafkaProperties.getProducer().buildProperties(sslBundles)
//        );
//        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
//        return new DefaultKafkaProducerFactory<>(config);
//    }
//
//    @Bean
//    public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> pf) {
//        return new KafkaTemplate<>(pf);
//    }
}
