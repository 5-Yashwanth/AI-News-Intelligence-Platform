package com.yashwanth.backend.controller;

import org.springframework.data.domain.Page;
import com.yashwanth.backend.external.NewsApiResponse;
import com.yashwanth.backend.dto.NewsRequest;
import com.yashwanth.backend.dto.NewsResponse;
import com.yashwanth.backend.entity.News;
import com.yashwanth.backend.service.NewsApiService;
import com.yashwanth.backend.service.NewsImportService;
//import com.yashwanth.backend.entity.News;
import com.yashwanth.backend.service.NewsService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;
    private final NewsApiService newsApiService;
    private final NewsImportService newsImportService;


    @GetMapping
    public List<NewsResponse> getNews() {

        return newsService.getAllNews();

    }

    @PostMapping
    public NewsResponse createNews(@RequestBody NewsRequest request) {

        return newsService.addNews(request);

    }
    @PutMapping("/{id}")
    public NewsResponse updateNews(
            @PathVariable Long id,
            @Valid @RequestBody NewsRequest request) {

        return newsService.updateNews(id, request);

    }
    @DeleteMapping("/{id}")
    public void deleteNews(@PathVariable Long id) {

        newsService.deleteNews(id);

    }
    @GetMapping("/category/{category}")
    public List<NewsResponse> getNewsByCategory(
           @PathVariable String category) {

        return newsService.getNewsByCategory(category);

    }
    @GetMapping("/{id}")
    public NewsResponse getNewsById(@PathVariable Long id) {

        return newsService.getNewsById(id);

    }
    @GetMapping("/fetch")
    public NewsApiResponse fetchNews() {

        return newsApiService.fetchNews();

    }
    @PostMapping("/import")
    public String importNews() {

        newsImportService.importNews();

        return "News imported successfully.";

    }
    @GetMapping("/search")
    public List<News> searchNews(
            @RequestParam String keyword) {

        return newsService.searchNews(keyword);

    }
    @GetMapping("/page")
    public Page<News> getNewsPage(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size) {

        return newsService.getNews(page, size);

    }
    @GetMapping("/latest")
    public List<News> latestNews() {

        return newsService.getLatestNews();

    }
}