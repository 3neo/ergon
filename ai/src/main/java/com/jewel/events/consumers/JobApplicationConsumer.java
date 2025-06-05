package com.jewel.events.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jewel.events.SkillEvent;
import com.jewel.services.SkillEmbeddingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class JobApplicationConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();
    //logger
    private final Logger logger = LoggerFactory.getLogger(JobApplicationConsumer.class);
    private final SkillEmbeddingService skillEmbeddingService;

    public JobApplicationConsumer(SkillEmbeddingService skillEmbeddingService) {
        this.skillEmbeddingService = skillEmbeddingService;
    }


    @KafkaListener(topics = "new-skills-events", groupId = "ergon-group")
    public void consume(String message) {
        try {
            SkillEvent event = objectMapper.readValue(message, SkillEvent.class);
            handleEvent(event);
        } catch (Exception e) {
            // log + dead letter logic
        }
    }

    private void handleEvent(SkillEvent event) {
        // traitement métier
        logger.info("Reçu : " + event.getCandidateName() + " a postulé pour " + event.getSkillId()
                + " avec compétence " + event.getSkillName() + " et niveau " + event.getSkillLevel()
                + ". Description : " + event.getSkillDescription());
        // Call the service to store the embedding
        skillEmbeddingService.store(event);
    }
}
