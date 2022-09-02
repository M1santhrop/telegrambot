package com.github.m1santhrop.telegrambot.service;

import com.github.m1santhrop.telegrambot.repository.entity.TelegramUser;
import java.util.List;
import java.util.Optional;

public interface TelegramUserService {
    void save(TelegramUser telegramUser);
    List<TelegramUser> findByActive(Boolean active);
    Optional<TelegramUser> findByChatId(String chatId);
}
