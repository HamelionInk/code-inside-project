package com.nikitin.codeinsideproject.service;

import com.nikitin.codeinsideproject.dto.PersonDto;
import com.nikitin.codeinsideproject.mapper.PersonMapper;
import com.nikitin.codeinsideproject.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, PersonMapper personMapper) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    @Override
    public void savePerson(PersonDto personDto) {
        personRepository.save(personMapper.dtoToPerson(personDto));
    }

    @Override
    public void deletePerson(PersonDto personDto) {

    }

    @Override
    public void updatePerson(PersonDto personDto) {

    }

    @Override
    public boolean emailExist(String email) {
        return personRepository.findByEmail(email) == null;
    }
}
