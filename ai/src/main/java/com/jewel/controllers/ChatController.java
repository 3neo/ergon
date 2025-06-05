package com.jewel.controllers;

import com.jewel.services.LlmService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/ai", produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Company Controller", description = "API for managing companys")
public class ChatController {

    private final LlmService llmService;

    public ChatController(LlmService llmService) {
        this.llmService = llmService;
    }

    @GetMapping("/generate")
    public Map generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        String response = llmService.ask(message);
        return Map.of("generation", response);
    }

//    @GetMapping("/generateStream")
//    public Flux<String> generateStream(@RequestParam(value = "message",
//            defaultValue = "Tell me a joke") String message) {
//        return llmService.askAndGetStream(message)
//                .map(generation -> generation.replace("\n", " ").replace("\r", " "))
//                .map(generation -> generation.replaceAll("\\s+", " "));
//    }
}
