package com.jewel.events.consumers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class JobApplicationDltListener {
//     // logger
     private final Logger logger = LoggerFactory.getLogger(JobApplicationDltListener.class);
    @KafkaListener(topics = "new-skills-events.dlt", groupId = "ergon-dlt-group")
    public void listenDlt(String message) {
        logger.info("📛 Message en erreur envoyé au DLT : " + message);
        // stockage base / alerte / Slack...
    }
}

