package ru.satvaldiev.telegrambot.entity;

public record Transfer(long from, String to, String amount) {}