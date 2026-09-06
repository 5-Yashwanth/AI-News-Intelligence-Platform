package com.yashwanth.backend.ai;

import org.springframework.stereotype.Service;

@Service
public class AiSentimentService {

    public String analyze(String text) {

        text = text.toLowerCase();

        if(text.contains("growth")
                || text.contains("success")
                || text.contains("record")
                || text.contains("wins")
                || text.contains("launch")){

            return "Positive";
        }

        if(text.contains("crash")
                || text.contains("layoff")
                || text.contains("hack")
                || text.contains("loss")
                || text.contains("bankrupt")){

            return "Negative";
        }

        return "Neutral";
    }

}