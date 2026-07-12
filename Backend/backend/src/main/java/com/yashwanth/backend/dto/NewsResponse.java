package com.yashwanth.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewsResponse {

    private Long id;

    private String title;

    private String source;

    private String category;

    private String url;

    private LocalDateTime publishedDate;

}