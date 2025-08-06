package com.simsoft.shapes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ShapeMovementApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShapeMovementApplication.class, args);
    }
}
