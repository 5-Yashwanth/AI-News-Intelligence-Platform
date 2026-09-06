package com.yashwanth.backend.service;

import com.yashwanth.backend.entity.News;
import com.yashwanth.backend.entity.ReadingHistory;
import com.yashwanth.backend.entity.User;
import com.yashwanth.backend.repository.NewsRepository;
import com.yashwanth.backend.repository.ReadingHistoryRepository;
import com.yashwanth.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
@RequiredArgsConstructor
public class ReadingHistoryService {

    private final ReadingHistoryRepository readingHistoryRepository;

    private final UserRepository userRepository;

    private final NewsRepository newsRepository;

    public void saveHistory(Long newsId) {

        Authentication authentication =
            SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
            .orElseThrow();

        News news = newsRepository.findById(newsId)
            .orElseThrow();

        ReadingHistory history = new ReadingHistory();

        history.setUser(user);
        history.setNews(news);
        history.setViewedAt(LocalDateTime.now());

        readingHistoryRepository.save(history);

    }
    public List<ReadingHistory> getHistory() {

        Authentication authentication =
            SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
            .orElseThrow();

        return readingHistoryRepository.findByUser(user);

    }
}