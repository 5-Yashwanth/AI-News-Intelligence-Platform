package com.yashwanth.backend.scheduler;

import com.yashwanth.backend.service.NewsImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewsScheduler {

    @Scheduled(fixedRate = 7200000)
    public void importNewsAutomatically() {

        System.out.println("Starting scheduled news import...");

        newsImportService.importNews();

        System.out.println("News import completed.");

    }
    private final NewsImportService newsImportService;

}