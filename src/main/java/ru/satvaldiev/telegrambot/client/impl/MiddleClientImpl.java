package ru.satvaldiev.telegrambot.client.impl;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.satvaldiev.telegrambot.client.MiddleClient;
import ru.satvaldiev.telegrambot.entity.Account;
import ru.satvaldiev.telegrambot.entity.TelegramUser;
import ru.satvaldiev.telegrambot.entity.Transfer;
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
    @Override
    public Response getCurrentBalance(Update update) {
        long telegramUserId = update.getMessage().getFrom().getId();
        return restClient.get()
                .uri("/users/{id}/accounts", telegramUserId)
                .retrieve()
                .body(Response.class);
    }

    @Override
    public Response transfer(Update update) {
        Message message = update.getMessage();
        String command = message.getText();
        String correctSpaces = command.trim().replaceAll("\s", " ");
        String[] splitCommand = correctSpaces.split(" ");

        if (splitCommand.length != 3) {
            return new Response("Команда введена в неправильном формате");
        }
        if (!"/transfer".equals(splitCommand[0])) {
            return new Response("Команда введена в неправильном формате");
        }
        String amount = splitCommand[2];
        if (!amount.matches("[0-9.]+")) {
            System.out.println(amount);
            return new Response("Сумма может состоять только из чисел и знака \".\"");
        }
        if (amount.matches("\\d+\\.?\\d{0,2}")) {
            Transfer transfer = new Transfer(message.getFrom().getId(),
                    message.getFrom().getUserName(), splitCommand[1], amount);
            return restClient.post()
                    .uri("/users/transfers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(transfer)
                    .retrieve()
                    .body(Response.class);
        }
        return new Response("Сумма введена некорректно");
    }
}
