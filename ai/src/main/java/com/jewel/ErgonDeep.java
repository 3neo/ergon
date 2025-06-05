package com.jewel;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.TimeZone;
import java.util.logging.Logger;


@SpringBootApplication
@EnableAsync
public class ErgonDeep {

    private final static Logger logger = Logger.getLogger(ErgonDeep.class.getName());

    public static void main(String[] args) {
        logger.info("before setting time zone is: " + TimeZone.getDefault().getID());
        System.out.println("BOOTSTRAP SERVERS = " + System.getProperty("spring.kafka.bootstrap-servers"));
        ApplicationContext applicationContext = SpringApplication.run(ErgonDeep.class, args);

        System.out.println("BOOTSTRAP SERVERS = " + System.getProperty("spring.kafka.bootstrap-servers"));

    }

    @PostConstruct
    public void init() {
        // Setting Spring Boot SetTimeZone
        TimeZone.setDefault(TimeZone.getTimeZone("Africa/Tunis"));
        logger.info("Global springboot time zone set to: " + TimeZone.getDefault().getID());

    }
}
