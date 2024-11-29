package com.kenect.kenect_challenge.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    // Should come in a more secure way
    private static final String EXTERNAL_CONTACTS_TOKEN = "J7ybt6jv6pdJ4gyQP9gNonsY";

    @Bean
    @Qualifier("kenect_labs_rest_template")
    RestTemplate kenectRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .additionalInterceptors((ClientHttpRequestInterceptor) (request, body, execution) -> {
                    request.getHeaders().add("Authorization", "Bearer " + EXTERNAL_CONTACTS_TOKEN);
                    return execution.execute(request, body);
                }).build();
    }

    @Bean
    DefaultConversionService conversionService(List<? extends Converter<?,?>> converters){
        var conversionService = new DefaultConversionService();
        converters.forEach(conversionService::addConverter);
        return conversionService;
    }
}
