package com.jewel.services;

import com.jewel.events.SkillEvent;
import com.jewel.model.SkillEmbedding;
import com.jewel.repositories.SkillEmbeddingRepository;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillEmbeddingService {
    //logger
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SkillEmbeddingService.class);
    private final EmbeddingModel embeddingModel;
    private final SkillEmbeddingRepository repository;

    public SkillEmbeddingService(EmbeddingModel embeddingModel, SkillEmbeddingRepository repository) {
        this.embeddingModel = embeddingModel;
        this.repository = repository;
    }

    public SkillEmbedding store(SkillEvent event) {
        String input = String.join(" ", event.getCandidateName(), event.getSkillName(), event.getSkillLevel(), event.getSkillDescription());
        logger.info("Generating embedding for input: {}", input);
        float[] vector = embeddingModel.embed(input);
        EmbeddingResponse embeddingResponse = embeddingModel.call(
                new EmbeddingRequest(List.of("Hello World", "World is big and salvation is near"), OllamaOptions.builder().build())); // Ensure the builder is built into an OllamaOptions object
        SkillEmbedding entity = new SkillEmbedding(event, vector);
        return repository.save(entity);
    }
}
