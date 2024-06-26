package ru.satvaldiev.telegrambot.service;

import org.telegram.telegrambots.meta.api.objects.Update;


public interface MessageService {
    String startCommand(String firstName);
    String stopCommand(String firstName);
    String registerCommand(Update update);
    String createAccountCommand(Update update);
    String getCurrentBalanceCommand(Update update);
    String transferCommand(Update update);
}
