package com.nikitin.codeinsideproject.repository;

import com.nikitin.codeinsideproject.entity.Notes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NotesRepository extends JpaRepository<Notes, UUID> {
}
