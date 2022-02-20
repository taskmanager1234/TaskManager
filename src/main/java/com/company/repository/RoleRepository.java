package com.company.repository;

import com.company.model.Role;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.UUID;

public class RoleRepository {
    @Autowired
    EntityManager entityManager;
}