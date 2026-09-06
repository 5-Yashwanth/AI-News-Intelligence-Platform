package com.yashwanth.backend.ai;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OllamaService {

    private final RestTemplate restTemplate;

    // ==========================================
    // Combined AI Analysis
    // ==========================================

    public String analyzeArticle(String text) {

        String url = "http://localhost:11434/api/generate";

        String prompt = """
                Analyze the following news article.

                Return the result in EXACTLY this format:

                SUMMARY:
                Write a short summary in 3 simple sentences.

                SENTIMENT:
                Return ONLY one of:
                Positive
                Neutral
                Negative

                KEYWORDS:
                Return exactly 5 important keywords separated by commas.
                Do not use numbering.
                Do not add explanations.

                News Article:
                """ + text;

        Map<String, Object> request = Map.of(
                "model", "llama3.2:3b",
                "prompt", prompt,
                "stream", false
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(request, headers);

        Map response = restTemplate.postForObject(
                url,
                entity,
                Map.class
        );

        if (response == null || response.get("response") == null) {
            throw new RuntimeException("Empty response from Ollama");
        }

        return response.get("response").toString();
    }


    // ==========================================
    // Existing Summary Method
    // ==========================================

    public String summarize(String article) {

        String url = "http://localhost:11434/api/generate";

        Map<String, Object> request = Map.of(
                "model", "llama3.2:3b",
                "prompt",
                "Summarize this news article in 3 simple sentences:\n\n" + article,
                "stream", false
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(request, headers);

        Map response = restTemplate.postForObject(
                url,
                entity,
                Map.class
        );

        if (response == null || response.get("response") == null) {
            throw new RuntimeException("Empty response from Ollama");
        }

        return response.get("response").toString();
    }


    // ==========================================
    // Existing Sentiment Method
    // ==========================================

    public String analyzeSentiment(String text) {

        String url = "http://localhost:11434/api/generate";

        Map<String, Object> request = Map.of(
                "model", "llama3.2:3b",
                "prompt",
                """
                Analyze the sentiment of this news.

                Return ONLY one word:
                Positive
                Neutral
                Negative

                News:
                """ + text,
                "stream", false
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(request, headers);

        Map response = restTemplate.postForObject(
                url,
                entity,
                Map.class
        );

        if (response == null || response.get("response") == null) {
            throw new RuntimeException("Empty response from Ollama");
        }

        return response.get("response").toString();
    }


    // ==========================================
    // Existing Keywords Method
    // ==========================================

    public String extractKeywords(String text) {

        String url = "http://localhost:11434/api/generate";

        Map<String, Object> request = Map.of(
                "model", "llama3.2:3b",
                "prompt",
                """
                Extract exactly 5 important keywords from this news article.

                IMPORTANT:
                - Return ONLY the 5 keywords.
                - Separate each keyword using a comma.
                - Do NOT use spaces between words inside a keyword.
                - Do NOT add numbering.
                - Do NOT add explanations.

                Example:
                AI,Technology,Microsoft,Xbox,Gaming

                News:
                """ + text,
                "stream", false
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(request, headers);

        Map response =
                restTemplate.postForObject(url, entity, Map.class);

        if (response == null || response.get("response") == null) {
            throw new RuntimeException("Empty response from Ollama");
        }

        return response.get("response").toString();
    }
}