package com.jewel.services;


import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.api.OllamaModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LlmService {

    private final ChatModel chatModel;
    //logger
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(LlmService.class);

    @Autowired
    public LlmService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String ask(String message) {
        org.springframework.ai.chat.model.ChatResponse response = chatModel.call(
                new Prompt(
                        message
                ));
        logger.info("Received response: {}", response.getResult().getOutput().getText());
        return response.getResult().getOutput().getText(); // Assuming ChatResponse has a getContent() method to retrieve the response as a String
    }

//  //  public Flux<String> askAndGetStream(String message) {
//        return chatModel.prompt().user(message).stream().content();
//    }


}
