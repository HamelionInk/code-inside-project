package com.nikitin.codeinsideproject.service;

import com.nikitin.codeinsideproject.dto.PersonDto;
import com.nikitin.codeinsideproject.error.PersonAlreadyExistException;
import com.nikitin.codeinsideproject.mapper.PersonMapper;
import com.nikitin.codeinsideproject.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void savePerson(PersonDto personDto) throws PersonAlreadyExistException {
        if (emailExist(personDto.getEmail())) {
            throw new PersonAlreadyExistException("There is an account with that email address: "
                    + personDto.getEmail());
        } else {
            personRepository.save(personMapper.dtoToPerson(personDto));
        }
    }

    @Override
    public void deletePerson(String username) {
        personRepository.delete(personRepository.findByUsername(username));
    }

    @Override
    public void updatePerson(String username, PersonDto personDto) {
        PersonDto personDtoForUpdate = personMapper.personToDto(personRepository.findByUsername(username));
        personDtoForUpdate.setUsername(personDto.getUsername());
        personDtoForUpdate.setPassword(personDto.getPassword());
        personDtoForUpdate.setEmail(personDto.getEmail());
        personDtoForUpdate.setAge(personDto.getAge());
        personRepository.save(personMapper.dtoToPerson(personDtoForUpdate));
    }

    @Override
    public boolean emailExist(String email) {
        return personRepository.findByEmail(email) != null;
    }
}
