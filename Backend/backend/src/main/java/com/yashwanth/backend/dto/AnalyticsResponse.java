package com.yashwanth.backend.dto;

import lombok.Data;
import java.util.List;


@Data
public class AnalyticsResponse {

    private long totalArticles;

    private long totalBookmarks;

    private long articlesRead;

    private String favoriteCategory;
    
    private List<CategoryStats> categoryStats;

}