package com.yashwanth.backend.service;

import com.yashwanth.backend.external.NewsApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
//import org.springframework.stereotype.Service;
//import org.springframework.beans.factory.annotation.Value;

@Service
public class NewsApiService {

    @Value("${news.api.key}")
    private String apiKey;

    @Value("${news.api.url}")
    private String apiUrl;

    private final RestClient restClient = RestClient.create();

    public NewsApiResponse fetchNews() {

        return restClient
            .get()
            .uri(apiUrl +
                    "?country=us" +
                    "&category=technology" +
                    "&pageSize=10" +
                    "&apiKey=" + apiKey)
            .retrieve()
            .body(NewsApiResponse.class);

    }
    public void printConfiguration() {

        System.out.println("API KEY : " + apiKey);

        System.out.println("URL : " + apiUrl);

    }
}