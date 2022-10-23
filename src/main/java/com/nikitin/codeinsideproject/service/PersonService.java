package com.nikitin.codeinsideproject.service;

import com.nikitin.codeinsideproject.dto.PersonDto;

public interface PersonService {
    void savePerson(PersonDto personDto);
    void deletePerson(String username);
    boolean emailExist(String email);
    void updatePerson(String username, PersonDto personDto);
}
