package ru.satvaldiev.telegrambot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import ru.satvaldiev.telegrambot.config.properties.RestClientProperties;

@Configuration
public class RestClientConfig {
    private final RestClientProperties restClientProperties;

    public RestClientConfig(RestClientProperties restClientProperties) {
        this.restClientProperties = restClientProperties;
    }

    @Bean
    public RestClient restClient(RestClient.Builder builder) {
        return builder
                .baseUrl(restClientProperties.url())
                .build();
    }
}
