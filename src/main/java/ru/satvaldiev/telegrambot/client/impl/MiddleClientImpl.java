package ru.satvaldiev.telegrambot.client.impl;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.satvaldiev.telegrambot.client.MiddleClient;
import ru.satvaldiev.telegrambot.entity.Account;
import ru.satvaldiev.telegrambot.entity.TelegramUser;
import ru.satvaldiev.telegrambot.response.Response;

@Component
public class MiddleClientImpl implements MiddleClient {
    private final RestClient restClient;

    public MiddleClientImpl(RestClient restClient) {
        this.restClient = restClient;
    }


    @Override
    public Response registerUser(Update update) {
        User user = update.getMessage().getFrom();
        TelegramUser telegramUser = new TelegramUser(user.getId(), user.getUserName());
        return restClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(telegramUser)
                .retrieve()
                .body(Response.class);
    }

    @Override
    public Response createAccount(Update update) {
        long telegramUserId = update.getMessage().getFrom().getId();
        Account account = new Account("Акционный");
        return restClient.post()
                .uri("/users/{id}/accounts", telegramUserId)
                .contentType(MediaType.APPLICATION_JSON)
                .body(account)
                .retrieve()
                .body(Response.class);
    }
}
