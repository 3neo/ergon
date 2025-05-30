package com.jewel.services;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Map;

@Service
public class LlmService {

    private final ChatClient chatClient;

    @Autowired
    public LlmService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String ask(String message) {
        String response = chatClient.prompt().user(message).call().content();
     //   return Map.of("generation", response);
        return response;
    }

    public Flux<String> askAndGetStream(String message) {
        return chatClient.prompt().user(message).stream().content();
    }


}
