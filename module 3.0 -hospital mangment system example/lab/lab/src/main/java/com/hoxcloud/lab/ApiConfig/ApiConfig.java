package com.codebyarunyadav.HMS.ApiConfig;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {
    @Bean
    ModelMapper modelMapper()
    {
        return new ModelMapper();
    }
}
