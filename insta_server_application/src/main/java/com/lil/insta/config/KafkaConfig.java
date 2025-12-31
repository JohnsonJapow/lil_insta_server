// package com.lil.insta.config;

// import java.util.HashMap;
// import java.util.Map;

// import org.apache.kafka.clients.admin.NewTopic;
// import org.apache.kafka.clients.consumer.ConsumerConfig;
// import org.apache.kafka.clients.producer.ProducerConfig;
// import org.apache.kafka.common.serialization.StringDeserializer;
// import org.apache.kafka.common.serialization.StringSerializer;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.Profile;
// import org.springframework.kafka.annotation.EnableKafka;
// import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
// import org.springframework.kafka.config.TopicBuilder;
// import org.springframework.kafka.core.ConsumerFactory;
// import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
// import org.springframework.kafka.core.DefaultKafkaProducerFactory;
// import org.springframework.kafka.core.KafkaTemplate;
// import org.springframework.kafka.core.ProducerFactory;
// import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;

// @Configuration
// @EnableKafka
// @Profile("kafka")
// public class KafkaConfig {
//     public static final String DEFAULT_SERVER = "localhost:9092";
//     public static final String COMMENT_TOPIC = "comment-to-moderate";
//     public static final String CONSUMER_GROUP_1 = "consumer_group_1";

//     @Bean
//     public ProducerFactory<String, String> producerFactory() {
//         Map<String, Object> config = new HashMap<>();
//         config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, DEFAULT_SERVER);
//         config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//         config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//         return new DefaultKafkaProducerFactory<>(config);
//     }

//     @Bean
//     public KafkaTemplate<String, String> kafkaTemplate() {
//         return new KafkaTemplate<>(producerFactory());
//     }

//     @Bean
//     public ConsumerFactory<String, String> consumerFactory() {
//         Map<String, Object> config = new HashMap<>();
//         config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, DEFAULT_SERVER);
//         config.put(ConsumerConfig.GROUP_ID_CONFIG, CONSUMER_GROUP_1);
//         config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//         config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//         config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//         config.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
//         config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, StringDeserializer.class);
//         return new DefaultKafkaConsumerFactory<>(config);
//     }

//     @Bean
//     public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
//         ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
//         factory.setConsumerFactory(consumerFactory());
//         return factory;
//     }

//     @Bean
//     public NewTopic defaulTopic() {
//         return TopicBuilder.name(COMMENT_TOPIC)
//             .partitions(3)
//             .replicas(1)
//             .build();
// }
// }
// package com.lil.insta.config;

// import org.apache.kafka.clients.admin.NewTopic;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.Profile;
// import org.springframework.kafka.config.TopicBuilder;

// @Configuration
// @Profile("kafka")
// public class KafkaConfig {

//     public static final String COMMENT_TOPIC = "comment-to-moderate";

//     @Bean
//     public NewTopic commentTopic() {
//         return TopicBuilder.name(COMMENT_TOPIC)
//                 .partitions(3)
//                 .replicas(1)
//                 .build();
//     }
// }
