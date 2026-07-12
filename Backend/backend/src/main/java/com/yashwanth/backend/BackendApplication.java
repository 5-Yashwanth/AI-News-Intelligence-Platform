package com.yashwanth.backend;

import com.yashwanth.backend.service.NewsApiService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
@RequiredArgsConstructor
public class BackendApplication implements CommandLineRunner {

    private final NewsApiService newsApiService;

    public static void main(String[] args) {

        SpringApplication.run(BackendApplication.class, args);

    }

    @Override
    public void run(String... args) {

        newsApiService.printConfiguration();

    }
}