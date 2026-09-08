package com.yashwanth.backend.repository;

import com.yashwanth.backend.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

import com.yashwanth.backend.dto.CategoryStats;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long> {

    List<News> findByCategory(String category);

    Optional<News> findByUrl(String url);

    boolean existsByUrl(String url);

    List<News> findByTitleContainingIgnoreCase(String keyword);


    // ==========================================
    // Get News - Newest First
    // ==========================================

    List<News> findAllByOrderByPublishedDateDesc();


    @Query("""
        SELECT new com.yashwanth.backend.dto.CategoryStats(
            n.category,
            COUNT(n)
        )
        FROM News n
        GROUP BY n.category
        ORDER BY COUNT(n) DESC
    """)
    List<CategoryStats> getCategoryStatistics();

}