package com.github.m1santhrop.telegrambot.command;

import static com.github.m1santhrop.telegrambot.command.CommandUtils.*;
import static java.util.stream.Collectors.*;
import com.github.m1santhrop.telegrambot.exception.ExceptionSender;
import com.github.m1santhrop.telegrambot.repository.entity.TelegramUser;
import com.github.m1santhrop.telegrambot.service.SendBotMessageService;
import com.github.m1santhrop.telegrambot.service.TelegramUserService;
import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

@AllArgsConstructor
public class ListGroupSubCommand implements Command {

    private TelegramUserService telegramUserService;
    private SendBotMessageService sendBotMessageService;
    
    public static final String MESSAGE =  "Я нашел все подписки на группы: \n\n";
    
    @Override
    public void execute(Update update) {
        Long chatId = getChatId(update);
        TelegramUser telegramUser = telegramUserService.findByChatId(chatId)
            .orElseThrow(() -> ExceptionSender.throwNotFoundUserException(chatId));

        String message;
        if (telegramUser.getGroupSubs().isEmpty()) {
            message = "Пока нет подписок на группы. Чтобы добавить подписку напиши /addGroupSub";
        } else {
            String collectedGroups = telegramUser.getGroupSubs().stream()
                .map(groupSub -> String.format("ID : %s, Группа : %s %n", groupSub.getId(),
                    groupSub.getTitle()))
                .collect(joining()); 
            message = MESSAGE + collectedGroups;
        }
        
        sendBotMessageService.sendMessage(chatId, message);
    }
}
