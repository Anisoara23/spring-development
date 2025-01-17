package com.example;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Explore California API",
                description = "API Definitions of the Explore California Microservice",
                version = "1.0.0"
        )
)
public class MicroserviceEnhancedApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicroserviceEnhancedApplication.class, args);
    }
}