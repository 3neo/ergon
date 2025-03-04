package com.jewel;

import com.tngtech.archunit.core.domain.JavaClass;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.TimeZone;
import java.util.logging.Logger;



@SpringBootApplication
@EnableAsync
public class ErgonApplication {

    private final  static Logger logger = Logger.getLogger(ErgonApplication.class.getName());

    public static void main(String[] args) {
        logger.info("before setting time zone is: " + TimeZone.getDefault().getID());
        ApplicationContext applicationContext = SpringApplication.run(ErgonApplication.class, args);
        // Initialize ApplicationModules to check module boundaries (optional)
        ApplicationModules modules = ApplicationModules.of(ErgonApplication.class, JavaClass.Predicates.resideInAPackage("com.jewel.ergon.domain"));
        // Print all modules (for debugging)
        modules.forEach(System.out::println);
        // Verify module dependencies
        modules.verify();

    }
    @PostConstruct
    public void init() {
        // Setting Spring Boot SetTimeZone
        TimeZone.setDefault(TimeZone.getTimeZone("Africa/Tunis"));
        logger.info("Global springboot time zone set to: " + TimeZone.getDefault().getID());

    }
}
