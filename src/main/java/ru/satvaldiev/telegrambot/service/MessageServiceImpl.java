package ru.satvaldiev.telegrambot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.satvaldiev.telegrambot.client.MiddleClient;
import ru.satvaldiev.telegrambot.response.Response;

@Service
public class MessageServiceImpl implements MessageService {
    private final MiddleClient middleClient;

    public MessageServiceImpl(MiddleClient middleClient) {
        this.middleClient = middleClient;

    }

    @Override
    public String stopCommand(String firstName) {
        return String.format("Пока, %s!", firstName);
    }

    @Override
    public String registerCommand(Update update) {
        Response responseBody = middleClient.registerUser(update);
        return responseTextGenerator(responseBody);
    }
    @Override
    public String createAccountCommand(Update update) {
        Response responseBody = middleClient.createAccount(update);
        return responseTextGenerator(responseBody);
    }
    public String responseTextGenerator(Response responseBody) {
        if (responseBody == null || responseBody.message() == null) {
            return "Непредвиденная ошибка, повторите позже";
        }
        else {
            return responseBody.message();
        }
    }
    @Override
    public String startCommand(String firstName) {
        return String.format(
                """
                Здравствуйте, %s!
                
                При открытии счета в нашем банке, Вы получите 5000 рублей в подарок! Поторопитесь! Предложение ограничено!
                
                Чтобы стать клиентом нашего банка, воспользуйтесь командой:
                
                /register
                
                Если Вы являетесь клиентом нашего банка и хотите открыть счет, воспользуйтесь командой:
                
                /createaccount
                
                Если Вы хотите получить текущий баланс, воспользуйтесь командой:
                
                /currentbalance
                
                Если Вы хотите сделать перевод со своего счета текущего пользователя на другой счет по имени пользователя в Telegram, воспользуйтесь командой:
                
                /transfer [toTelegramUser] [сумма]
                
                Пример команды: /transfer Anatoliy 1500.00
                """, firstName);
    }
}
