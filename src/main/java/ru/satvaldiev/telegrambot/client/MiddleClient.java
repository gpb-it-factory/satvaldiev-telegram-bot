package ru.satvaldiev.telegrambot.client;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.satvaldiev.telegrambot.response.Response;

public interface MiddleClient {
    Response registerUser(Update update);

    Response createAccount(Update update);
}
