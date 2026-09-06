package com.yashwanth.backend.scheduler;

import com.yashwanth.backend.service.NewsImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewsScheduler {

    private final NewsImportService newsImportService;

    @Scheduled(fixedRate = 7200000)
    public void importNewsAutomatically() {

        System.out.println("Starting scheduled news import...");

        try {

            newsImportService.importNews();

            System.out.println(
                "News import completed successfully."
            );

        } catch (Exception e) {

            System.out.println(
                "Scheduled news import failed: " +
                e.getMessage()
            );
        }
    }
}