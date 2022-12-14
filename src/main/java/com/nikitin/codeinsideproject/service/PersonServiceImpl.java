package com.nikitin.codeinsideproject.service;

import com.nikitin.codeinsideproject.dto.NotesDto;
import com.nikitin.codeinsideproject.dto.PersonDto;
import com.nikitin.codeinsideproject.entity.Notes;
import com.nikitin.codeinsideproject.entity.Person;
import com.nikitin.codeinsideproject.error.NotesNotFoundException;
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
        if (emailExist(personDto.getEmail()) && usernameExist(personDto.getUsername())) {
            throw new PersonAlreadyExistException("There is an account with that email address or username: "
                    + personDto.getEmail() + ", " + personDto.getUsername());
        } else {
            personRepository.save(personMapper.dtoToPerson(personDto));
        }
    }

    @Override
    public void deletePerson(String username) {
        Person person = personRepository.findByUsername(username);
        if(person == null) {
            throw new PersonNotFoundException();
        }
        personRepository.delete(person);
    }

    @Override
    public void updatePerson(String username, PersonDto personDto) {
        Person personForUpdate = personRepository.findByUsername(username);
        if(personForUpdate == null) {
            throw new PersonNotFoundException("Profile with username" + username + "not found");
        }
        personForUpdate.setUsername(personDto.getUsername());
        personForUpdate.setPassword(personDto.getPassword());
        personForUpdate.setEmail(personDto.getEmail());
        personForUpdate.setAge(personDto.getAge());
        personRepository.save(personForUpdate);
    }

    @Override
    public List<PersonDto> getAllPerson() {
        List<PersonDto> personDtoList = personMapper.allPersonToDto(personRepository.findAll());
        if(personDtoList == null) {
            throw new PersonNotFoundException();
        }
        return personDtoList;
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
        if(!notes.isPresent()) {
            throw new NotesNotFoundException("Notes not found in this profile");
        }
        person.removeNotes(notes.get());
        personRepository.flush();
    }

    @Override
    public List<NotesDto> getAllNotes(String username) {
        Person person = personRepository.findByUsername(username);
        if(person == null) {
            throw new PersonNotFoundException("Profile with username" + username + "not found");
        }
        List<Notes> notesList = person.getNotesList();
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
