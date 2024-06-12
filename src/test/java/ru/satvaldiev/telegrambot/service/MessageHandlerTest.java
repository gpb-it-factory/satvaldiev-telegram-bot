package ru.satvaldiev.telegrambot.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageHandlerTest {
    private Message message;
    private Update update;

    @Mock
    private MessageService messageService;
    @InjectMocks
    private MessageHandler messageHandler;


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
    void sendMessageWithNullReceivingMessageIsNull() {
        message.setText(null);

        SendMessage sendMessage = messageHandler.messageReceiver(update);

        Assertions.assertNull(sendMessage);
    }
    @Test
    void sendMessageWithNoTextInReceivingMessageIsNull() {
        update.setMessage(null);

        SendMessage sendMessage = messageHandler.messageReceiver(update);

        Assertions.assertNull(sendMessage);
    }

    @Test
    void responseTextWithStartCommand() {
        message.setText("/start");
        when(messageService.startCommand(update.getMessage().getChat().getFirstName()))
                .thenReturn("Здравствуйте, Anatoliy!");

        String responseText = messageHandler.messageReceiver(update).getText();

        Assertions.assertEquals("Здравствуйте, Anatoliy!", responseText);
    }
    @Test
    void responseTextWithStopCommand() {
        message.setText("/stop");
        when(messageService.stopCommand(update.getMessage().getChat().getFirstName()))
                .thenReturn("Пока, Anatoliy!");

        String responseText = messageHandler.messageReceiver(update).getText();

        Assertions.assertEquals("Пока, Anatoliy!", responseText);
    }
    @Test
    void responseTextWithRegisterCommand() {
        message.setText("/register");
        when(messageService.registerCommand(update))
                .thenReturn("Поздравляем! Вы стали клиентом нашего банка");

        String responseText = messageHandler.messageReceiver(update).getText();

        Assertions.assertEquals("Поздравляем! Вы стали клиентом нашего банка", responseText);
    }
    @Test
    void responseTextWithCreateAccountCommand() {
        message.setText("/createaccount");
        when(messageService.createAccountCommand(update))
                .thenReturn("Поздравляем! Вы открыли счет \"Акционный\"");

        String responseText = messageHandler.messageReceiver(update).getText();

        Assertions.assertEquals("Поздравляем! Вы открыли счет \"Акционный\"", responseText);
    }
    @Test
    void responseTextWithUnknownCommand() {

        message.setText("/dropTelegram");

        String responseText = messageHandler.messageReceiver(update).getText();

        Assertions.assertEquals("Неизвестная команда", responseText);
    }
}