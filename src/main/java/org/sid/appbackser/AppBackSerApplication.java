package org.sid.appbackser;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class AppBackSerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppBackSerApplication.class, args);
    }
    @Bean
    CommandLineRunner start(){
        return args -> {
            System.out.println(new Date());
        };
    }
}
