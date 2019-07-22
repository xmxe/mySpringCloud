package com.cas;

import net.unicon.cas.client.configuration.EnableCasClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableCasClient
@SpringBootApplication
public class CasClientTAppication {
    public static void main(String[] args) {
        SpringApplication.run(CasClientTAppication.class,args);
    }
}
