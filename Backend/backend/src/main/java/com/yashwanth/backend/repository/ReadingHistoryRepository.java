package com.yashwanth.backend.repository;

import com.yashwanth.backend.entity.ReadingHistory;
import com.yashwanth.backend.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReadingHistoryRepository
        extends JpaRepository<ReadingHistory, Long> {

    List<ReadingHistory> findByUser(User user);

    // ==========================================
    // Get User Reading History - Newest First
    // ==========================================

    List<ReadingHistory> findByUserOrderByViewedAtDesc(User user);

}