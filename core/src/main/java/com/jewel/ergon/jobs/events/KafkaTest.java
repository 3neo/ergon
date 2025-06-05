package com.jewel.ergon.jobs.events;

import com.jewel.ergon.jobs.events.producers.SkillPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class KafkaTest implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(KafkaTest.class);
    @Autowired
    SkillPublisher skillPublisher;


    @Override
    public void run(String... args) {
        logger.info("Spring Boot Runner executing  kafka test...");
        skillPublisher.publishJobApplication(
                SkillEvent.builder().skillId("skill-123")
                        .candidateName("Ahmed")
                        .skillName("Java Programming")
                        .skillDescription("Proficiency in Java programming language")
                        .skillLevel("Advanced")
                        .build()
        );
    }
}
