package com.yashwanth.backend.service;

import com.yashwanth.backend.dto.UserPreferenceRequest;
import com.yashwanth.backend.dto.UserPreferenceResponse;
import com.yashwanth.backend.entity.User;
import com.yashwanth.backend.entity.UserPreference;
import com.yashwanth.backend.repository.UserPreferenceRepository;
import com.yashwanth.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPreferenceService {

    private final UserRepository userRepository;
    private final UserPreferenceRepository userPreferenceRepository;


    public UserPreferenceResponse getPreferences(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));


        UserPreference preference =
                userPreferenceRepository.findByUser(user)
                        .orElseGet(() -> createDefaultPreferences(user));


        return convertToResponse(preference);
    }


    public UserPreferenceResponse updatePreferences(
            String email,
            UserPreferenceRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));


        UserPreference preference =
                userPreferenceRepository.findByUser(user)
                        .orElseGet(() -> {

                            UserPreference newPreference =
                                    new UserPreference();

                            newPreference.setUser(user);

                            return newPreference;
                        });


        preference.setAiMl(request.isAiMl());
        preference.setSoftware(request.isSoftware());
        preference.setIndiaNews(request.isIndiaNews());
        preference.setBigTech(request.isBigTech());
        preference.setWorldBusiness(request.isWorldBusiness());
        preference.setSportsMovies(request.isSportsMovies());


        UserPreference saved =
                userPreferenceRepository.save(preference);


        return convertToResponse(saved);
    }


    private UserPreference createDefaultPreferences(User user) {

        UserPreference preference =
                new UserPreference();

        preference.setUser(user);

        preference.setAiMl(true);
        preference.setSoftware(true);
        preference.setIndiaNews(true);
        preference.setBigTech(true);
        preference.setWorldBusiness(true);
        preference.setSportsMovies(true);

        return userPreferenceRepository.save(preference);
    }


    private UserPreferenceResponse convertToResponse(
            UserPreference preference) {

        UserPreferenceResponse response =
                new UserPreferenceResponse();

        response.setAiMl(preference.isAiMl());
        response.setSoftware(preference.isSoftware());
        response.setIndiaNews(preference.isIndiaNews());
        response.setBigTech(preference.isBigTech());
        response.setWorldBusiness(
                preference.isWorldBusiness()
        );
        response.setSportsMovies(
                preference.isSportsMovies()
        );

        return response;
    }
}
