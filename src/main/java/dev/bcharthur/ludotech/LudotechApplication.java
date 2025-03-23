// LudotechApplication.java
package dev.bcharthur.ludotech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class LudotechApplication {


    public static void main(String[] args) {
        SpringApplication.run(LudotechApplication.class, args);
    }
}