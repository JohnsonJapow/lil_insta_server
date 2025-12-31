// package com.lil.insta.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
// import org.springframework.data.redis.core.RedisTemplate;
// import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
// import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
// import org.springframework.data.redis.serializer.RedisSerializer;
// import org.springframework.data.redis.serializer.StringRedisSerializer;

// import com.fasterxml.jackson.databind.cfg.MapperBuilder;

// import tools.jackson.databind.ObjectMapper;

// @Configuration
// @EnableRedisRepositories
// public class RedisConfig {

//     @Bean
//     public LettuceConnectionFactory redisConnectionFactory() {
//         LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory("localhost", 6379);
//         connectionFactory.afterPropertiesSet();

//         return connectionFactory;
//     }

//     @Bean
//     public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory connectionFactory) {
//         RedisTemplate<String, Object> template = new RedisTemplate<>();
//         template.setConnectionFactory(connectionFactory);

//         // Key serializer
//         template.setKeySerializer(new StringRedisSerializer());

//         // ObjectMapper objectMapper = new ObjectMapper();
//         // objectMapper.registerModule(new JavaTimeModule());
//         // objectMapper.activateDefaultTyping(
//         //         objectMapper.getPolymorphicTypeValidator(),
//         //         ObjectMapper.DefaultTyping.NON_FINAL);

//         RedisSerializer<Object> serializer = new JacksonJsonRedisSerializer<>(Object.class);

//         template.setValueSerializer(serializer);
//         template.afterPropertiesSet();
//         return template;
//     }
// }
