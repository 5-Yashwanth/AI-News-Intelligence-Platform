package com.yashwanth.backend.controller;

import com.yashwanth.backend.ai.OllamaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AiController {

    private final OllamaService ollamaService;

    @PostMapping("/summarize")
    public String summarize(@RequestBody String article) {

        return ollamaService.summarize(article);

    }
    @PostMapping("/sentiment")
    public String sentiment(@RequestBody String text) {

        return ollamaService.analyzeSentiment(text);

    }
    @PostMapping("/keywords")
public String keywords(@RequestBody String text){

    return ollamaService.extractKeywords(text);

}

}