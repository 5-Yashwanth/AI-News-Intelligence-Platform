package com.yashwanth.backend.controller;

import com.yashwanth.backend.entity.ReadingHistory;
import com.yashwanth.backend.service.ReadingHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
public class ReadingHistoryController {

    private final ReadingHistoryService readingHistoryService;

    @PostMapping("/{newsId}")
    public void saveHistory(@PathVariable Long newsId) {

        readingHistoryService.saveHistory(newsId);

    }

    @GetMapping
    public List<ReadingHistory> getHistory() {

        return readingHistoryService.getHistory();

    }
}