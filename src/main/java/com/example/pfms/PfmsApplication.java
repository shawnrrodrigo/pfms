package com.example.pfms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class PfmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PfmsApplication.class, args);
    }

}
