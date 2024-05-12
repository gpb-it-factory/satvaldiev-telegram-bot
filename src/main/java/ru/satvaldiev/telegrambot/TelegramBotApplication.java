package ru.satvaldiev.telegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class TelegramBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(TelegramBotApplication.class, args);
    }
}
