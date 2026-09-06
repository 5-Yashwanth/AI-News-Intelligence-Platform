package com.yashwanth.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewsResponse {

    private Long id;

    private String title;

    private String description;
    
    private String source;

    private String category;

    private String url;

    private String imageUrl;

    private String sentiment;

    private String summary;

    private LocalDateTime publishedDate;

    private String keywords;

}