package com.yashwanth.backend.controller;

import com.yashwanth.backend.dto.UserPreferenceRequest;
import com.yashwanth.backend.dto.UserPreferenceResponse;
import com.yashwanth.backend.service.UserPreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/preferences")
@RequiredArgsConstructor
public class UserPreferenceController {

    private final UserPreferenceService userPreferenceService;


    @GetMapping
    public UserPreferenceResponse getPreferences(
            Authentication authentication) {

        String email = authentication.getName();

        return userPreferenceService.getPreferences(email);
    }


    @PutMapping
    public UserPreferenceResponse updatePreferences(
            Authentication authentication,
            @RequestBody UserPreferenceRequest request) {

        String email = authentication.getName();

        return userPreferenceService.updatePreferences(
                email,
                request
        );
    }
}