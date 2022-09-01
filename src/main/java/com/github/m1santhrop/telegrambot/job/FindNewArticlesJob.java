package com.github.m1santhrop.telegrambot.job;

import com.github.m1santhrop.telegrambot.service.FindNewArticleService;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FindNewArticlesJob {
    
    private final FindNewArticleService findNewArticleService;

    public FindNewArticlesJob(
        FindNewArticleService findNewArticleService) {
        this.findNewArticleService = findNewArticleService;
    }
    
    @Scheduled(fixedRateString = "${bot.recountNewArticleFixedRate}")
    public void findNewArticles() {
        LocalDateTime start = LocalDateTime.now();
        log.info("Find new article job started.");
        
        findNewArticleService.findNewArticles();

        LocalDateTime end = LocalDateTime.now();
        log.info("Find new articles job finished. Took seconds: {}", end.toEpochSecond(ZoneOffset.UTC) - start.toEpochSecond(ZoneOffset.UTC));
    }
}
