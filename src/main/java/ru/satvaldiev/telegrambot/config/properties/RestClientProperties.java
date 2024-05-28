package ru.satvaldiev.telegrambot.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "restclient")
public record RestClientProperties(String url) {
}
