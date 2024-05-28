package ru.satvaldiev.telegrambot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

public interface MessageService {
    SendMessage messageReceiver(Update update);
    String startCommand(String firstName);
    String stopCommand(String firstName);
    String pingCommand();
    String registerCommand(User user);
}
