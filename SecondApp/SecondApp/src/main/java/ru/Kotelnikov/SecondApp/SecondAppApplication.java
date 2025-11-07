package ru.Kotelnikov.SecondApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "ru.Kotelnikov.SecondApp")
public class SecondAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecondAppApplication.class, args);
    }
}
