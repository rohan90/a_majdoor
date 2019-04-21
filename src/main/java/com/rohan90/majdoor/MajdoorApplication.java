package com.rohan90.majdoor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class MajdoorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MajdoorApplication.class, args);
    }

}
