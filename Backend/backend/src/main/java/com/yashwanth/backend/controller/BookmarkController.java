package com.yashwanth.backend.controller;

import com.yashwanth.backend.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.yashwanth.backend.dto.NewsResponse;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping("/{newsId}")
    public void saveBookmark(@PathVariable Long newsId) {

        bookmarkService.saveBookmark(newsId);

    }

    @GetMapping
    public List<NewsResponse> getBookmarks() {

        return bookmarkService.getBookmarks();

    }

}