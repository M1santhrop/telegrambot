package com.github.m1santhrop.telegrambot.repository;

import com.github.m1santhrop.telegrambot.repository.entity.TelegramUser;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelegramUserRepository extends JpaRepository<TelegramUser, String> {
    List<TelegramUser> findByActive(Boolean active);
}
