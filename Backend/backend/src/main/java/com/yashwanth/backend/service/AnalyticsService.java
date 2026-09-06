package com.yashwanth.backend.service;

import com.yashwanth.backend.dto.AnalyticsResponse;
import com.yashwanth.backend.repository.BookmarkRepository;
import com.yashwanth.backend.repository.NewsRepository;
import com.yashwanth.backend.repository.ReadingHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final NewsRepository newsRepository;

    private final BookmarkRepository bookmarkRepository;

    private final ReadingHistoryRepository readingHistoryRepository;

    public AnalyticsResponse getAnalytics() {

        AnalyticsResponse response = new AnalyticsResponse();

        response.setTotalArticles(newsRepository.count());

        response.setTotalBookmarks(bookmarkRepository.count());

        response.setArticlesRead(readingHistoryRepository.count());

        response.setFavoriteCategory("Technology");

        response.setCategoryStats(
            newsRepository.getCategoryStatistics()
        );

        return response;

    }

}