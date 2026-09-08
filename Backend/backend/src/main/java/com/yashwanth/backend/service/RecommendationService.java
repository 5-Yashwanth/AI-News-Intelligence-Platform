package com.yashwanth.backend.service;

import com.yashwanth.backend.entity.News;
import com.yashwanth.backend.entity.User;
import com.yashwanth.backend.entity.UserPreference;
import com.yashwanth.backend.repository.NewsRepository;
import com.yashwanth.backend.repository.UserPreferenceRepository;
import com.yashwanth.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final NewsRepository newsRepository;

    private final UserRepository userRepository;

    private final UserPreferenceRepository userPreferenceRepository;


    // ==========================================
    // Get Personalized News
    // ==========================================

    public List<News> getRecommendedNews(String email) {

        // ==========================================
        // Find User
        // ==========================================

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "User not found"
                                )
                        );


        // ==========================================
        // Find User Preferences
        // ==========================================

        UserPreference preference =
                userPreferenceRepository
                        .findByUser(user)
                        .orElseGet(
                                () -> createDefaultPreferences(user)
                        );


        // ==========================================
        // Get All News - Newest First
        // ==========================================

        List<News> allNews =
                newsRepository.findAllByOrderByPublishedDateDesc();


        // ==========================================
        // Filter According To Preferences
        // ==========================================

        return allNews.stream()

                .filter(news ->
                        isCategoryEnabled(
                                news.getCategory(),
                                preference
                        )
                )

                .toList();
    }


    // ==========================================
    // Check Category
    // ==========================================

    private boolean isCategoryEnabled(
            String category,
            UserPreference preference
    ) {

        if (category == null) {
            return false;
        }


        String value =
                category
                        .trim()
                        .toLowerCase();


        // ==========================================
        // AI & ML
        // ==========================================

        if (value.equals("ai & ml")) {

            return preference.isAiMl();

        }


        // ==========================================
        // Software
        // ==========================================

        if (value.equals("software")) {

            return preference.isSoftware();

        }


        // ==========================================
        // India News
        // ==========================================

        if (value.equals("india news")) {

            return preference.isIndiaNews();

        }


        // ==========================================
        // Big Tech & Startups
        // ==========================================

        if (value.equals("big tech & startups")) {

            return preference.isBigTech();

        }


        // ==========================================
        // World & Business
        // ==========================================

        if (value.equals("world & business")) {

            return preference.isWorldBusiness();

        }


        // ==========================================
        // Sports & Movies
        // ==========================================

        if (value.equals("sports & movies")) {

            return preference.isSportsMovies();

        }


        // ==========================================
        // Existing Old Categories
        // ==========================================
        // Keep old database records visible.
        // ==========================================

        if (
                value.equals("technology") ||
                value.equals("business") ||
                value.equals("sports") ||
                value.equals("health") ||
                value.equals("science") ||
                value.equals("entertainment")
        ) {

            return true;

        }


        // ==========================================
        // Unknown Category
        // ==========================================

        return true;
    }


    // ==========================================
    // Create Default Preferences
    // ==========================================

    private UserPreference createDefaultPreferences(
            User user
    ) {

        UserPreference preference =
                new UserPreference();


        preference.setUser(user);

        preference.setAiMl(true);

        preference.setSoftware(true);

        preference.setIndiaNews(true);

        preference.setBigTech(true);

        preference.setWorldBusiness(true);

        preference.setSportsMovies(true);


        return userPreferenceRepository.save(
                preference
        );
    }
}