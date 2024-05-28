package ru.satvaldiev.telegrambot.service.impl;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.satvaldiev.telegrambot.entity.TelegramUser;
import ru.satvaldiev.telegrambot.response.Response;
import ru.satvaldiev.telegrambot.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {
    private final RestClient restClient;

    public MessageServiceImpl(RestClient restClient) {
        this.restClient = restClient;

    }

    @Override
    public SendMessage messageReceiver(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            String text = message.getText();
            long chatId = message.getChatId();
            String firstName = message.getChat().getFirstName();
            User user = message.getFrom();
            String responseText;
            switch (text) {
                case "/start" -> responseText = startCommand(firstName);
                case "/stop" -> responseText = stopCommand(firstName);
                case "/ping" -> responseText = pingCommand();
                case "/register" -> responseText = registerCommand(user);
                default -> responseText = "Неизвестная команда";
            }
            return new SendMessage(String.valueOf(chatId), responseText);
        }
        return null;
    }

    @Override
    public String startCommand(String firstName) {
        return String.format(
                """
                Здравствуйте, %s!
                
                Чтобы стать клиентом нашего банка воспользуйтесь командой:
                /register
                """, firstName);
    }

    @Override
    public String stopCommand(String firstName) {
        return String.format("Пока, %s!", firstName);
    }

    @Override
    public String pingCommand() {
        return "pong";
    }
    @Override
    public String registerCommand(User user) {
        TelegramUser telegramUser = new TelegramUser(user.getId());
        Response responseBody = restClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(telegramUser)
                .retrieve()
                .toEntity(Response.class)
                .getBody();

        if (responseBody == null || responseBody.getMessage() == null) {
            return "Непредвиденная ошибка, повторите позже";
        }
        else {
            return responseBody.getMessage();
        }
    }
}
