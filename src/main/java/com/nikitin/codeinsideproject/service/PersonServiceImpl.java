package com.nikitin.codeinsideproject.service;

import com.nikitin.codeinsideproject.dto.NotesDto;
import com.nikitin.codeinsideproject.dto.PersonDto;
import com.nikitin.codeinsideproject.entity.Notes;
import com.nikitin.codeinsideproject.entity.Person;
import com.nikitin.codeinsideproject.error.PersonAlreadyExistException;
import com.nikitin.codeinsideproject.error.PersonNotFoundException;
import com.nikitin.codeinsideproject.mapper.NotesMapper;
import com.nikitin.codeinsideproject.mapper.PersonMapper;
import com.nikitin.codeinsideproject.repository.NotesRepository;
import com.nikitin.codeinsideproject.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final NotesRepository notesRepository;
    private final PersonMapper personMapper;
    private final NotesMapper notesMapper;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, NotesRepository notesRepository, PersonMapper personMapper, NotesMapper notesMapper) {
        this.personRepository = personRepository;
        this.notesRepository = notesRepository;
        this.personMapper = personMapper;
        this.notesMapper = notesMapper;
    }

    @Override
    public void savePerson(PersonDto personDto) throws PersonAlreadyExistException {
        if (emailExist(personDto.getEmail()) || usernameExist(personDto.getUsername())) {
            throw new PersonAlreadyExistException("There is an account with that email address or username: "
                    + personDto.getEmail() + ", " + personDto.getUsername());
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
        if(personDtoForUpdate == null) {
            throw new PersonNotFoundException("Profile with username" + username + "not found");
        }
        personDtoForUpdate.setUsername(personDto.getUsername());
        personDtoForUpdate.setPassword(personDto.getPassword());
        personDtoForUpdate.setEmail(personDto.getEmail());
        personDtoForUpdate.setAge(personDto.getAge());
        personRepository.save(personMapper.dtoToPerson(personDtoForUpdate));
    }

    @Override
    public List<PersonDto> getAllPerson() {
        return personMapper.allPersonToDto(personRepository.findAll());
    }

    @Override
    public void saveNotes(String username, NotesDto notesDto) {
        Person person = personRepository.findByUsername(username);
        if(person == null) {
            throw new PersonNotFoundException("Profile with username" + username + "not found");
        }
        notesDto.setVersion(1);
        person.addNotes(notesMapper.dtoToNotes(notesDto));
        personRepository.save(person);
        personRepository.flush();
    }

    @Override
    public void updateNotes(String username, NotesDto notesDto) {
        Person person = personRepository.findByUsername(username);
        if(person == null) {
            throw new PersonNotFoundException("Profile with username" + username + "not found");
        }
        notesDto.setVersion(notesDto.getVersion() + 1);
        notesDto.setDataUpdate(LocalDate.now());
        person.addNotes(notesMapper.dtoToNotes(notesDto));
        personRepository.save(person);
        personRepository.flush();
    }

    @Override
    public void deleteNotes(String username, String id) {
        Person person = personRepository.findByUsername(username);
        if(person == null) {
            throw new PersonNotFoundException("Profile with username" + username + "not found");
        }
        Optional<Notes> notes = notesRepository.findById(UUID.fromString(id));
        person.removeNotes(notes.get());
        personRepository.flush();
    }

    @Override
    public List<NotesDto> getAllNotes(String username) {
        Person person = personRepository.findByUsername(username);
        List<Notes> notesList = person.getNotes();
        return notesMapper.allNotesToDto(notesList);
    }

    @Override
    public boolean emailExist(String email) {
        return personRepository.findByEmail(email) != null;
    }

    @Override
    public boolean usernameExist(String username) {
        return personRepository.findByUsername(username) != null;
    }
}
