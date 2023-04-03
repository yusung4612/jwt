package com.example.temipj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling // 스프링 부트에서 스케줄러가 작동
@EnableJpaAuditing
@SpringBootApplication
public class TemipjApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemipjApplication.class, args);
    }

}
