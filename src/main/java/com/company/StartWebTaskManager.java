package com.company;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StartWebTaskManager {

    public static void main(String[] args)  {
        SpringApplication.run(StartWebTaskManager.class, args);
        InitPostgresSql.init();
    }
}