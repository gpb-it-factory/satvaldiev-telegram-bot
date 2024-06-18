package ru.satvaldiev.telegrambot.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.satvaldiev.telegrambot.client.impl.MiddleClientImpl;
import ru.satvaldiev.telegrambot.response.Response;


import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageServiceImplTest {
    private Message message;
    private Update update;


    @Mock
    private MiddleClientImpl middleClient;
    @InjectMocks
    private MessageServiceImpl messageService;

    @BeforeEach
    void beforeEach() {
        Chat chat = new Chat(123456L, "private");
        chat.setFirstName("Anatoliy");
        User user = new User(123456L, "Anatoliy", false);
        message = new Message();
        message.setFrom(user);
        message.setChat(chat);
        update = new Update();
        update.setMessage(message);
    }

    @Test
    void responseTextToStopCommand() {
        message.setText("/stop");

        String responseText = messageService.stopCommand(message.getChat().getFirstName());

        Assertions.assertEquals("Пока, Anatoliy!", responseText);
    }

    @Test
    void responseTextToRegisterCommand() {
        message.setText("/register");
        when(middleClient.registerUser(update))
                .thenReturn(new Response("Поздравляем! Вы стали клиентом нашего банка"));

        String responseText = messageService.registerCommand(update);

        Assertions.assertEquals("Поздравляем! Вы стали клиентом нашего банка", responseText);
    }

    @Test
    void responseTextToCreateAccountCommand() {
        message.setText("/createaccount");
        when(middleClient.createAccount(update))
                .thenReturn(new Response("Поздравляем! Вы открыли счет \"Акционный\""));

        String responseText = messageService.createAccountCommand(update);

        Assertions.assertEquals("Поздравляем! Вы открыли счет \"Акционный\"", responseText);
    }

    @Test
    void responseTextWithNullResponseBodyFromMiddleService() {
        String responseText = messageService.responseTextGenerator(null);

        Assertions.assertEquals("Непредвиденная ошибка, повторите позже", responseText);
    }
    @Test
    void responseTextWithNullMessageInResponseBodyFromMiddleService() {
        message.setText(null);
        String responseText = messageService.responseTextGenerator(new Response(message.getText()));

        Assertions.assertEquals("Непредвиденная ошибка, повторите позже", responseText);
    }

    @Test
    void responseTextToStartCommand() {
        message.setText("/start");

        String responseText = messageService.startCommand(message.getChat().getFirstName());

        Assertions.assertEquals("""
                Здравствуйте, Anatoliy!
                
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
                """, responseText);
    }
}