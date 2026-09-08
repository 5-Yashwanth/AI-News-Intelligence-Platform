package com.yashwanth.backend.dto;

import lombok.Data;

@Data
public class UserPreferenceResponse {

    private boolean aiMl;

    private boolean software;

    private boolean indiaNews;

    private boolean bigTech;

    private boolean worldBusiness;

    private boolean sportsMovies;
}