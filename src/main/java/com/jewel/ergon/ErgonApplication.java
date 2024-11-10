package com.jewel.ergon;

import com.tngtech.archunit.core.domain.JavaClass;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class ErgonApplication {

    public static void main(String[] args) {

        ApplicationContext applicationContext = SpringApplication.run(ErgonApplication.class, args);
        // Initialize ApplicationModules to check module boundaries (optional)
        ApplicationModules modules = ApplicationModules.of(ErgonApplication.class, JavaClass.Predicates.resideInAPackage("com.jewel.ergon.domain"));
        // Print all modules (for debugging)
        modules.forEach(System.out::println);
        // Verify module dependencies
        modules.verify();

    }

}
