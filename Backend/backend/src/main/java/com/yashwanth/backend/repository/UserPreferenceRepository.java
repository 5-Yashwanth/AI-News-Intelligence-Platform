package com.yashwanth.backend.repository;

import com.yashwanth.backend.entity.User;
import com.yashwanth.backend.entity.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPreferenceRepository
        extends JpaRepository<UserPreference, Long> {

    Optional<UserPreference> findByUser(User user);

}