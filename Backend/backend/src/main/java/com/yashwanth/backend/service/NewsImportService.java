package com.yashwanth.backend.service;

import com.yashwanth.backend.ai.OllamaService;
import com.yashwanth.backend.entity.News;
import com.yashwanth.backend.external.Article;
import com.yashwanth.backend.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class NewsImportService {

    private final NewsApiService newsApiService;

    private final NewsRepository newsRepository;

    private final OllamaService ollamaService;


    public void importNews() {

        var response = newsApiService.fetchNews();

        var articles = (java.util.List<Article>) response.getArticles();

        for (Article article : articles) {

            // ==========================================
            // Prevent Duplicate Articles
            // ==========================================

            if (newsRepository.existsByUrl(article.getUrl())) {

                System.out.println(
                    "Skipping duplicate article: " + article.getTitle()
                );

                continue;
            }


            News news = new News();

            news.setTitle(article.getTitle());

            news.setDescription(article.getDescription());

            news.setSource(article.getSource().getName());

            news.setUrl(article.getUrl());

            news.setImageUrl(article.getUrlToImage());

            news.setPublishedDate(
                OffsetDateTime.parse(article.getPublishedAt())
                    .toLocalDateTime()
            );


            // ==========================================
            // Text Sent to Ollama
            // ==========================================

            String text =
                article.getTitle() + "\n\n" +
                (article.getDescription() != null
                    ? article.getDescription()
                    : "");


            // ==========================================
            // ONE AI REQUEST
            // Summary + Sentiment + Keywords + Category
            // ==========================================

            try {

                String aiResponse =
                    ollamaService.analyzeArticle(text);


                System.out.println(
                    "AI Response for: " + article.getTitle()
                );

                System.out.println(aiResponse);


                // ==========================================
                // Extract Summary
                // ==========================================

                String summary =
                    extractSection(
                        aiResponse,
                        "SUMMARY:",
                        "SENTIMENT:"
                    );

                news.setSummary(summary);


                // ==========================================
                // Extract Sentiment
                // ==========================================

                String sentiment =
                    extractSection(
                        aiResponse,
                        "SENTIMENT:",
                        "KEYWORDS:"
                    );

                news.setSentiment(
                    sentiment.trim()
                );


                // ==========================================
                // Extract Keywords
                // ==========================================

                String keywords =
                    extractSection(
                        aiResponse,
                        "KEYWORDS:",
                        "CATEGORY:"
                    );

                news.setKeywords(
                    keywords.trim()
                );


                // ==========================================
                // Extract Category
                // ==========================================

                String category =
                    extractSection(
                        aiResponse,
                        "CATEGORY:",
                        null
                    );


                news.setCategory(
                    normalizeCategory(category)
                );


            } catch (Exception e) {

                System.out.println(
                    "AI Analysis failed: " +
                    e.getMessage()
                );

            }


            // ==========================================
            // Save News
            // ==========================================

            newsRepository.save(news);
        }
    }


    // ==========================================
    // Extract Section From AI Response
    // ==========================================

    private String extractSection(
        String response,
        String startMarker,
        String endMarker
    ) {

        if (response == null) {
            return "";
        }


        int start =
            response.indexOf(startMarker);


        if (start == -1) {
            return "";
        }


        start += startMarker.length();


        int end;


        if (endMarker != null) {

            end =
                response.indexOf(
                    endMarker,
                    start
                );

        } else {

            end = response.length();
        }


        if (end == -1) {
            end = response.length();
        }


        return response
            .substring(start, end)
            .trim();
    }


    // ==========================================
    // Normalize AI Category
    // ==========================================

    private String normalizeCategory(String category) {

        if (category == null) {
            return "Software";
        }


        String value =
            category
                .trim()
                .replace(".", "")
                .replace("\"", "");


        // ==========================================
        // AI & ML
        // ==========================================

        if (value.equalsIgnoreCase("AI & ML")
                || value.equalsIgnoreCase("AI and ML")
                || value.equalsIgnoreCase("Artificial Intelligence")) {

            return "AI & ML";
        }


        // ==========================================
        // Software
        // ==========================================

        if (value.equalsIgnoreCase("Software")
                || value.equalsIgnoreCase("Software Technology")) {

            return "Software";
        }


        // ==========================================
        // India News
        // ==========================================

        if (value.equalsIgnoreCase("India News")
                || value.equalsIgnoreCase("Indian News")) {

            return "India News";
        }


        // ==========================================
        // Big Tech & Startups
        // ==========================================

        if (value.equalsIgnoreCase("Big Tech & Startups")
                || value.equalsIgnoreCase("Big Tech and Startups")) {

            return "Big Tech & Startups";
        }


        // ==========================================
        // World & Business
        // ==========================================

        if (value.equalsIgnoreCase("World & Business")
                || value.equalsIgnoreCase("World and Business")) {

            return "World & Business";
        }


        // ==========================================
        // Sports & Movies
        // ==========================================

        if (value.equalsIgnoreCase("Sports & Movies")
                || value.equalsIgnoreCase("Sports and Movies")) {

            return "Sports & Movies";
        }


        // ==========================================
        // Unknown Category
        // ==========================================

        System.out.println(
            "Unknown AI category: " + category
        );


        return "Software";
    }
}