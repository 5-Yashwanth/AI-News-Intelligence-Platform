package com.yashwanth.backend.service;

import com.yashwanth.backend.ai.AiSummaryService;
import com.yashwanth.backend.entity.News;
import com.yashwanth.backend.external.Article;

//import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import com.yashwanth.backend.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsImportService {

    private final NewsApiService newsApiService;

    private final NewsRepository newsRepository;

    private final AiSummaryService aiSummaryService;

    public void importNews() {

        var response = newsApiService.fetchNews();

        var articles = response.getArticles();

        for (Article article : articles) {

            News news = new News();

            news.setTitle(article.getTitle());

            news.setSource(article.getSource().getName());

            news.setCategory("technology");

            news.setUrl(article.getUrl());

            news.setPublishedDate(
                OffsetDateTime.parse(article.getPublishedAt())
                    .toLocalDateTime()
            );

            news.setSummary(aiSummaryService.summarize(news.getTitle()));
            newsRepository.save(news);

        }
    } 
}