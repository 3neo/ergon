package com.jewel;

import com.tngtech.archunit.core.domain.JavaClass;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.TimeZone;
import java.util.logging.Logger;



@SpringBootApplication(exclude = {
        OAuth2ResourceServerAutoConfiguration.class
})
@EnableScheduling
@EnableAsync
public class ErgonApplication {

    private final  static Logger logger = Logger.getLogger(ErgonApplication.class.getName());

    public static void main(String[] args) {
        logger.info("before setting time zone is: " + TimeZone.getDefault().getID());
        System.out.println("BOOTSTRAP SERVERS = " + System.getProperty("spring.kafka.bootstrap-servers"));

        ApplicationContext applicationContext = SpringApplication.run(ErgonApplication.class, args);
        // Initialize ApplicationModules to check module boundaries (optional)
        ApplicationModules modules = ApplicationModules.of(ErgonApplication.class, JavaClass.Predicates.resideInAPackage("com.jewel.ergon.domain"));
        // Print all modules (for debugging)
        modules.forEach(System.out::println);
        // Verify module dependencies
        modules.verify();
        System.out.println("BOOTSTRAP SERVERS = " + System.getProperty("spring.kafka.bootstrap-servers"));


    }
    @PostConstruct
    public void init() {
        // Setting Spring Boot SetTimeZone
        TimeZone.setDefault(TimeZone.getTimeZone("Africa/Tunis"));
        logger.info("Global springboot time zone set to: " + TimeZone.getDefault().getID());

    }
}
