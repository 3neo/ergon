package com.jewel.config;

//TODO explain why we use Kafka for async processing and how it works with DLT (Dead Letter Topic) + add documentation
//@Configuration
public class KafkaConsumerConfig {
//
//    private final KafkaTemplate<String, String> kafkaTemplate;
//    private final KafkaProperties kafkaProperties;
//    private final SslBundles sslBundles;
//
//    public KafkaConsumerConfig(KafkaTemplate<String, String> kafkaTemplate, KafkaProperties kafkaProperties, SslBundles sslBundles) {
//        this.kafkaTemplate = kafkaTemplate;
//        this.kafkaProperties = kafkaProperties;
//        this.sslBundles = sslBundles;
//    }

//    @Bean
//    public ConsumerFactory<String, String> consumerFactory() {
//        Map<String, Object> config = new HashMap<>(kafkaProperties.getConsumer().buildProperties(sslBundles));
//        System.out.println("🛠️ Kafka Consumer Config: " + config);
//        return new DefaultKafkaConsumerFactory<>(config);
//    }

//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, String> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());

        // Redirection automatique en cas d’exception vers <original-topic>-dlt
//        factory.setCommonErrorHandler(new DefaultErrorHandler(
//                new DeadLetterPublishingRecoverer(kafkaTemplate,
//                        (record, ex) -> new TopicPartition(record.topic() + ".dlt", record.partition())),
//                new FixedBackOff(0L, 2) // retry 2 times before DLT
//        ));
//
//        return factory;
//    }
}
