package com.yashwanth.backend.controller;

import com.yashwanth.backend.entity.News;
import com.yashwanth.backend.service.RecommendationService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;


    // ==========================================
    // Get Personalized Recommendations
    // ==========================================

    @GetMapping
    public List<News> getRecommendations(
            Authentication authentication
    ) {

        String email = authentication.getName();

        return recommendationService.getRecommendedNews(email);
    }
}