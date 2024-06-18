package ru.satvaldiev.telegrambot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.satvaldiev.telegrambot.config.properties.BotProperties;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    private static final Logger LOG = LoggerFactory.getLogger(TelegramBot.class);
    private final BotProperties botProperties;
    private final MessageHandler messageHandler;

    @Autowired
    public TelegramBot(BotProperties botProperties, MessageHandler messageHandler) {
        super(botProperties.token());
        this.botProperties = botProperties;
        this.messageHandler = messageHandler;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            SendMessage sendMessage = messageHandler.messageReceiver(update);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            LOG.error("Ошибка отправки сообщения пользователю", e);
        }
    }

    @Override
    public String getBotUsername() {
        return botProperties.name();
    }
}
