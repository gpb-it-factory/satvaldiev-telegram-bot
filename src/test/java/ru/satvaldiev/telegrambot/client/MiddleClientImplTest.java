package ru.satvaldiev.telegrambot.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.satvaldiev.telegrambot.client.impl.MiddleClientImpl;
import ru.satvaldiev.telegrambot.response.Response;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(MiddleClientImpl.class)
class MiddleClientImplTest {
    @Autowired
    MockRestServiceServer server;
    @Autowired
    MiddleClient middleClient;
    @Autowired
    ObjectMapper objectMapper;
    @Value("${restclient.url}")
    String baseUrl;
    private Update update;

    @TestConfiguration
    static class TestConfig {

        @Value("${restclient.url}")
        String baseUrl;

        @Bean
        public RestClient restClient(RestClient.Builder builder) {
            return builder
                    .baseUrl(baseUrl)
                    .build();
        }
    }

    @BeforeEach
    void beforeEach() {
        Chat chat = new Chat(123456L, "private");
        chat.setFirstName("Anatoliy");
        User user = new User(123456L, "Anatoliy", false);
        Message message = new Message();
        message.setFrom(user);
        message.setChat(chat);
        update = new Update();
        update.setMessage(message);
    }

    @Test
    void responseWithSuccessfulRegistration() throws JsonProcessingException {
        Response responseExpected = new Response("Поздравляем! Вы стали клиентом нашего банка");

        this.server
                .expect(requestTo(baseUrl + "/users"))
                .andRespond(withSuccess(objectMapper.writeValueAsString(responseExpected), MediaType.APPLICATION_JSON));

        Response responseActual = middleClient.registerUser(update);
        Assertions.assertEquals(responseExpected.message(), responseActual.message());
    }
    @Test
    void responseWithSuccessfulAccountCreating() throws JsonProcessingException {
        Response responseExpected = new Response("Поздравляем! Вы открыли счет \"Акционный\"");

        this.server
                .expect(requestTo(baseUrl + "/users/" + update.getMessage().getFrom().getId() + "/accounts"))
                .andRespond(withSuccess(objectMapper.writeValueAsString(responseExpected), MediaType.APPLICATION_JSON));

        Response responseActual = middleClient.createAccount(update);
        Assertions.assertEquals(responseExpected.message(), responseActual.message());
    }
}