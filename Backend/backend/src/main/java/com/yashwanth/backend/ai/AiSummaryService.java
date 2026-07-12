package com.yashwanth.backend.ai;

import org.springframework.stereotype.Service;

@Service
public class AiSummaryService {

    public String summarize(String title) {

        return "Summary of: " + title;

    }

}