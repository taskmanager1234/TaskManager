package com.company.config.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

@Configuration
public class SpringConfig {
    @Bean
    public EntityManager entityManager() {
        EntityManager entityManager = Persistence
                .createEntityManagerFactory("TasksJournal")
                .createEntityManager();
        return entityManager;
    }
}
