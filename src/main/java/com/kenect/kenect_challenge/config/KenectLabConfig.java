package com.kenect.kenect_challenge.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Configuration
@ConfigurationProperties(prefix = "kenect-labs")
public class KenectLabConfig {

    private String url;
    private String token;
    private String pageCounterHeaderName;

    @Bean
    @Qualifier("kenect_labs_rest_template")
    RestTemplate kenectRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .additionalInterceptors((ClientHttpRequestInterceptor) (request, body, execution) -> {
                    request.getHeaders().add("Authorization", "Bearer " + token);
                    return execution.execute(request, body);
                }).build();
    }

}
