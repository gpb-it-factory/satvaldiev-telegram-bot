package ru.satvaldiev.telegrambot.entity;

public record Transfer(long id, String from, String to, String amount) {
}
