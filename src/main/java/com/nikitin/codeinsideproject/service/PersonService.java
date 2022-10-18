package com.nikitin.codeinsideproject.service;

import com.nikitin.codeinsideproject.dto.PersonDto;

public interface PersonService {
    void savePerson(PersonDto personDto);
    void deletePerson(PersonDto personDto);
    void updatePerson(PersonDto personDto);
    boolean emailExist(String email);
}
