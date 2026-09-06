package com.yashwanth.backend.service;

import com.yashwanth.backend.entity.Bookmark;
import com.yashwanth.backend.repository.BookmarkRepository;
import com.yashwanth.backend.repository.NewsRepository;
import com.yashwanth.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.yashwanth.backend.entity.News;
import com.yashwanth.backend.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import com.yashwanth.backend.dto.NewsResponse;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final NewsRepository newsRepository;

    public void saveBookmark(Long newsId) {

        Authentication authentication =
            SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
            .orElseThrow();

        News news = newsRepository.findById(newsId)
            .orElseThrow();

        if (bookmarkRepository.findByUserAndNews(user, news).isPresent()) {
                return;
        }

        Bookmark bookmark = new Bookmark();

        bookmark.setUser(user);
        bookmark.setNews(news);
        bookmark.setSavedAt(LocalDateTime.now());

        bookmarkRepository.save(bookmark);
    }
    public List<NewsResponse> getBookmarks() {

        Authentication authentication =
            SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
            .orElseThrow();

        return bookmarkRepository.findByUser(user)
            .stream()
            .map(bookmark -> {

                News news = bookmark.getNews();

                NewsResponse response = new NewsResponse();

                response.setId(news.getId());
                response.setTitle(news.getTitle());
                response.setSource(news.getSource());
                response.setCategory(news.getCategory());
                response.setUrl(news.getUrl());
                response.setPublishedDate(news.getPublishedDate());
                response.setImageUrl(news.getImageUrl());

                return response;

            })
            .toList();
 }
}