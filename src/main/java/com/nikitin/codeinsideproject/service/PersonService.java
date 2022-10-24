package com.nikitin.codeinsideproject.service;

import com.nikitin.codeinsideproject.dto.NotesDto;
import com.nikitin.codeinsideproject.dto.PersonDto;

import java.util.List;

public interface PersonService {
    void savePerson(PersonDto personDto);
    void deletePerson(String username);
    void updatePerson(String username, PersonDto personDto);
    List<PersonDto> getAllPerson();
    void saveNotes(String username, NotesDto notesDto);
    void updateNotes(String username, NotesDto notesDto);
    void deleteNotes(String username, String id);
    List<NotesDto> getAllNotes(String username);
    boolean emailExist(String email);
    boolean usernameExist(String username);

}
