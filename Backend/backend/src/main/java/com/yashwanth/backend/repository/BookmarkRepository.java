package com.yashwanth.backend.repository;

import com.yashwanth.backend.entity.Bookmark;
import com.yashwanth.backend.entity.News;
import com.yashwanth.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findByUser(User user);

    Optional<Bookmark> findByUserAndNews(User user, News news);

    void deleteByUserAndNews(User user, News news);
}