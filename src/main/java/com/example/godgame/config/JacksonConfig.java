//package com.example.godgame.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class JacksonConfig {
//
//    @Bean
//    public ObjectMapper objectMapper() {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.enable(SerializationFeature.INDENT_OUTPUT);
//        mapper.configure(com.fasterxml.jackson.core.JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, false);
//        return mapper;
//    }
//}