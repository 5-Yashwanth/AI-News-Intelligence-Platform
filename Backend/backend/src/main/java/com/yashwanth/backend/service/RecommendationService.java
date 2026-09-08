package com.yashwanth.backend.service;

import com.yashwanth.backend.entity.News;
import com.yashwanth.backend.entity.ReadingHistory;
import com.yashwanth.backend.entity.User;
import com.yashwanth.backend.entity.UserPreference;

import com.yashwanth.backend.repository.NewsRepository;
import com.yashwanth.backend.repository.ReadingHistoryRepository;
import com.yashwanth.backend.repository.UserPreferenceRepository;
import com.yashwanth.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final NewsRepository newsRepository;

    private final UserRepository userRepository;

    private final UserPreferenceRepository userPreferenceRepository;

    private final ReadingHistoryRepository readingHistoryRepository;


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
        // Get Reading History
        // ==========================================

        List<ReadingHistory> readingHistory =
                readingHistoryRepository
                        .findByUserOrderByViewedAtDesc(user);


        // ==========================================
        // Get Recently Read Categories
        // ==========================================

        Set<String> recentlyReadCategories =
                new HashSet<>();

        for (ReadingHistory history : readingHistory) {

            if (history.getNews() != null
                    && history.getNews().getCategory() != null) {

                recentlyReadCategories.add(
                        history.getNews()
                                .getCategory()
                                .trim()
                                .toLowerCase()
                );
            }
        }


        // ==========================================
        // Get All News - Newest First
        // ==========================================

        List<News> allNews =
                newsRepository
                        .findAllByOrderByPublishedDateDesc();


        // ==========================================
        // Filter + Personalize News
        // ==========================================

        return allNews.stream()

                // Keep only preferred categories
                .filter(news ->
                        isCategoryEnabled(
                                news.getCategory(),
                                preference
                        )
                )

                // Put recently-read categories first
                .sorted(
                        (news1, news2) -> {

                            boolean firstCategoryRead =
                                    isRecentlyReadCategory(
                                            news1.getCategory(),
                                            recentlyReadCategories
                                    );

                            boolean secondCategoryRead =
                                    isRecentlyReadCategory(
                                            news2.getCategory(),
                                            recentlyReadCategories
                                    );

                            if (firstCategoryRead
                                    && !secondCategoryRead) {

                                return -1;
                            }

                            if (!firstCategoryRead
                                    && secondCategoryRead) {

                                return 1;
                            }

                            return 0;
                        }
                )

                .toList();
    }


    // ==========================================
    // Check Recently Read Category
    // ==========================================

    private boolean isRecentlyReadCategory(
            String category,
            Set<String> recentlyReadCategories
    ) {

        if (category == null) {
            return false;
        }

        return recentlyReadCategories.contains(
                category.trim().toLowerCase()
        );
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