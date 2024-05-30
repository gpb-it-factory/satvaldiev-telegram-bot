package ru.satvaldiev.telegrambot.response;

public class Response {
    private String message;

    public Response(String response) {
        this.message = response;
    }
    public Response() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
