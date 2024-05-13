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
                case "/start" -> responseText = startCommand(firstName);
                case "/stop" -> responseText = stopCommand(firstName);
                case "/ping" -> responseText = pingCommand();
                default -> responseText = "Неизвестная команда";
            }
            return new SendMessage(String.valueOf(chatId), responseText);
        }
        return null;
    }

    @Override
    public String startCommand(String firstName) {
        return String.format("Привет, %s!", firstName);
    }

    @Override
    public String stopCommand(String firstName) {
        return String.format("Пока, %s!", firstName);
    }

    @Override
    public String pingCommand() {
        return "pong";
    }
}
