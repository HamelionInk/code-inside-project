package com.nikitin.codeinsideproject.repository;

import com.nikitin.codeinsideproject.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {
    Person findByUsername(String username);
    Person findByEmail(String email);
    void deleteByUsername(String username);
}
