package ru.satvaldiev.telegrambot.service.impl;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.satvaldiev.telegrambot.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {
    @Override
    public SendMessage messageReceiver(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String firstName = update.getMessage().getChat().getFirstName();
            String responseText;
            switch (text) {
                case "/start" -> responseText = String.format("Привет, %s!", firstName);
                case "/stop" -> responseText = String.format("Пока, %s!", firstName);
                case "/ping" -> responseText = "pong";
                default -> responseText = "Неизвестная команда";
            }
            return new SendMessage(String.valueOf(chatId), responseText);
        }
        return null;
    }
}
