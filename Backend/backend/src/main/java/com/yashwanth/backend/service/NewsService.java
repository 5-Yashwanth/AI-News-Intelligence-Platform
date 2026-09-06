package com.yashwanth.backend.service;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

//import com.yashwanth.backend.ai.AiSummaryService;
import com.yashwanth.backend.dto.NewsRequest;
import com.yashwanth.backend.dto.NewsResponse;
import com.yashwanth.backend.entity.News;
import com.yashwanth.backend.exception.ResourceNotFoundException;
import com.yashwanth.backend.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import com.yashwanth.backend.ai.OllamaService;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final OllamaService ollamaService;

    public List<NewsResponse> getAllNews() {

        List<News> newsList = newsRepository.findAll();

        return newsList.stream()
            .map(this::convertToResponse)
            .toList();

    }    

    public NewsResponse addNews(NewsRequest request) {

    News news = new News();

    news.setTitle(request.getTitle());
    news.setSource(request.getSource());
    news.setCategory(request.getCategory());
    news.setUrl(request.getUrl());
    news.setPublishedDate(request.getPublishedDate());

    // Create text for AI analysis
    String text = request.getTitle();

    // Generate AI Summary
    try {
        String summary = ollamaService.summarize(text);
        news.setSummary(summary);
    } catch (Exception e) {
        System.out.println("AI Summary failed: " + e.getMessage());
    }

    // Generate Sentiment
    try {
        String sentiment = ollamaService.analyzeSentiment(text);
        news.setSentiment(sentiment.trim());
    } catch (Exception e) {
        System.out.println("AI Sentiment failed: " + e.getMessage());
    }

    // Generate Keywords
    try {
        String keywords = ollamaService.extractKeywords(text);
        news.setKeywords(keywords.trim());
    } catch (Exception e) {
        System.out.println("AI Keywords failed: " + e.getMessage());
    }

    News savedNews = newsRepository.save(news);

    return convertToResponse(savedNews);
    }
    public NewsResponse updateNews(Long id, NewsRequest request) {

        News existingNews = newsRepository.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "News not found with id " + id));

        existingNews.setTitle(request.getTitle());
        existingNews.setSource(request.getSource());
        existingNews.setCategory(request.getCategory());
        existingNews.setUrl(request.getUrl());
        existingNews.setPublishedDate(request.getPublishedDate());

        News updatedNews = newsRepository.save(existingNews);

        return convertToResponse(updatedNews);

    }
    public void deleteNews(Long id) {

        newsRepository.deleteById(id);

    }
    public List<NewsResponse> getNewsByCategory(String category) {

        List<News> newsList = newsRepository.findByCategory(category);

        return newsList.stream()
            .map(this::convertToResponse)
            .toList();

    }
    public NewsResponse getNewsById(Long id) {

        News news = newsRepository.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "News not found with id " + id));

        return convertToResponse(news);

    }
    private NewsResponse convertToResponse(News news) {

    NewsResponse response = new NewsResponse();

    response.setId(news.getId());
    response.setTitle(news.getTitle());
    response.setDescription(news.getDescription());
    response.setSource(news.getSource());
    response.setCategory(news.getCategory());
    response.setUrl(news.getUrl());
    response.setImageUrl(news.getImageUrl());
    response.setPublishedDate(news.getPublishedDate());

    response.setSummary(news.getSummary());
    response.setSentiment(news.getSentiment());
    response.setKeywords(news.getKeywords());

    return response;
    }
    public List<News> searchNews(String keyword) {

        return newsRepository.findByTitleContainingIgnoreCase(keyword);

    }
    public Page<News> getNews(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return newsRepository.findAll(pageable);

    }
    public List<News> getLatestNews() {

        return newsRepository.findAll(
            Sort.by(Sort.Direction.DESC, "publishedDate")
        );
    }
    public void regenerateAI(Long id) {

    News news = newsRepository.findById(id)
        .orElseThrow(() ->
            new ResourceNotFoundException(
                "News not found with id " + id
            )
        );

    String text =
        news.getTitle() +
        "\n\n" +
        (news.getDescription() != null
            ? news.getDescription()
            : "");

    try {
        news.setSummary(
            ollamaService.summarize(text)
        );

        news.setSentiment(
            ollamaService.analyzeSentiment(text).trim()
        );

        news.setKeywords(
            ollamaService.extractKeywords(text).trim()
        );

        newsRepository.save(news);

    } catch (Exception e) {

        System.out.println(
            "AI regeneration failed: " + e.getMessage()
        );

        throw new RuntimeException(
            "Failed to regenerate AI data"
        );
    }
}
}