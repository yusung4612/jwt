package com.example.temipj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TemipjApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemipjApplication.class, args);
    }

}
