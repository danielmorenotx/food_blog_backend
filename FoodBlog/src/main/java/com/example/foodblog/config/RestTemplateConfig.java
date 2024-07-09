package com.example.foodblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig { // can make rest call from one service to another service
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
