package com.nikitin.codeinsideproject.repository;

import com.nikitin.codeinsideproject.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {
    Person findByUsername(String username);
    Person findByEmail(String email);
    void deleteByUsername(String username);
}
