package com.amala.weatherapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Component;

@Component
public class ApiConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

