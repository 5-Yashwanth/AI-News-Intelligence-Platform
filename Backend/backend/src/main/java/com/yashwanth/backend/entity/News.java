package com.yashwanth.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "news")
@Data
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title cannot be empty")
    @Column(nullable = false)
    private String title;

    @Column(length = 5000)
    private String description;

    @NotBlank(message = "Source cannot be empty")
    private String source;

    @NotBlank(message = "Category cannot be empty")
    private String category;

    @NotBlank(message = "URL cannot be empty")
    private String url;

    @Column(length = 1000)
    private String imageUrl;

    private LocalDateTime publishedDate;

    @Column(length = 3000)
    private String summary;

    private String sentiment;
    @Column(length = 1000)
    private String keywords;
}
