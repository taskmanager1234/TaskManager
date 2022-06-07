package com.company.config.spring;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

@ComponentScan(basePackages = "com.company")
@Configuration
public class SpringConfig {
    @Bean
    public EntityManager entityManager() {
        EntityManager entityManager = Persistence
                .createEntityManagerFactory("TasksJournal")
                .createEntityManager();
        return entityManager;
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }



}
