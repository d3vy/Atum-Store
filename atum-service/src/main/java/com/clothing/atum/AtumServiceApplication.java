package com.clothing.atum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AtumServiceApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AtumServiceApplication.class);
        app.setAdditionalProfiles("cloudconfig");
        app.run(args);
    }
}
