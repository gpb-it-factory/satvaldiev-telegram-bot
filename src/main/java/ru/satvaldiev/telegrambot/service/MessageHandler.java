package ru.satvaldiev.telegrambot.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class MessageHandler {
    private final MessageService messageService;

    public MessageHandler(MessageService messageService) {
        this.messageService = messageService;
    }
    public SendMessage messageReceiver(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            String text = message.getText();
            long chatId = message.getChatId();
            String firstName = message.getChat().getFirstName();
            String responseText;
            switch (text) {
                case "/start" -> responseText = messageService.startCommand(firstName);
                case "/stop" -> responseText = messageService.stopCommand(firstName);
                case "/register" -> responseText = messageService.registerCommand(update);
                case "/createaccount" -> responseText = messageService.createAccountCommand(update);
                default -> responseText = "Неизвестная команда";
            }
            return new SendMessage(String.valueOf(chatId), responseText);
        }
        return null;
    }
}
