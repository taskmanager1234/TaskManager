package com.company.repository;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class RoleRepository {
    @Autowired
    EntityManager entityManager;
}