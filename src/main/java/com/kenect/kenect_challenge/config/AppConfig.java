package com.kenect.kenect_challenge.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class AppConfig {

    @Bean
    DefaultConversionService conversionService(List<? extends Converter<?,?>> converters){
        var conversionService = new DefaultConversionService();
        converters.forEach(conversionService::addConverter);
        return conversionService;
    }
}
