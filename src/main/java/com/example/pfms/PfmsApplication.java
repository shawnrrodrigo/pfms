package com.example.pfms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.example.pfms.model")
public class PfmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PfmsApplication.class, args);
    }

}
