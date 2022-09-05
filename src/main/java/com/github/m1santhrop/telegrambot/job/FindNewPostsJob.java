package com.github.m1santhrop.telegrambot.job;

import com.github.m1santhrop.telegrambot.service.FindNewPostService;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FindNewPostsJob {
    
    private final FindNewPostService findNewPostService;

    public FindNewPostsJob(
        FindNewPostService findNewPostService) {
        this.findNewPostService = findNewPostService;
    }
    
    @Scheduled(fixedRateString = "${bot.recountNewPostFixedRate}")
    public void findNewPosts() {
        LocalDateTime start = LocalDateTime.now();
        log.info("Find new posts job started.");
        
        findNewPostService.findNewPosts();

        LocalDateTime end = LocalDateTime.now();
        log.info("Find new posts job finished. Took seconds: {}", end.toEpochSecond(ZoneOffset.UTC) - start.toEpochSecond(ZoneOffset.UTC));
    }
}
