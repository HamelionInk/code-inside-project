package com.nikitin.codeinsideproject.service;

import com.nikitin.codeinsideproject.dto.NotesDto;
import com.nikitin.codeinsideproject.entity.Notes;
import com.nikitin.codeinsideproject.entity.Person;
import com.nikitin.codeinsideproject.error.PersonAlreadyExistException;
import com.nikitin.codeinsideproject.error.PersonNotFoundException;
import com.nikitin.codeinsideproject.mapper.NotesMapper;
import com.nikitin.codeinsideproject.mapper.NotesMapperImpl;
import com.nikitin.codeinsideproject.mapper.PersonMapper;
import com.nikitin.codeinsideproject.mapper.PersonMapperImpl;
import com.nikitin.codeinsideproject.repository.NotesRepository;
import com.nikitin.codeinsideproject.repository.PersonRepository;
import com.nikitin.codeinsideproject.util.RoleEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;
    @Mock
    private NotesRepository notesRepository;
    @Mock
    private PersonMapper personMapperMock;
    @Mock
    private NotesMapper notesMapperMock;

    @InjectMocks
    private PersonMapperImpl personMapper;
    @InjectMocks
    private NotesMapperImpl notesMapper;

    @InjectMocks
    private PersonServiceImpl personService;
    private Person personOne;
    private Person personTwo;
    private Notes notesOne;
    private List<Person> personList;

    @BeforeEach
    public void setUp() {
        personList = new ArrayList<>();
        List<Notes> notesList = new ArrayList<>();

        personOne = new Person(UUID.randomUUID(),
                "TestUsername",
                "TestPassword",
                "TestEmail@gmail.com",
                20,
                RoleEnum.USER,
                notesList);
        personTwo = new Person(UUID.randomUUID(),
                "TestUsername2",
                "TestPassword2",
                "TestEmail2@gmail.com",
                21,
                RoleEnum.USER,
                notesList);
        notesOne = new Notes(UUID.randomUUID(),
                "header One",
                1,
                LocalDate.now(),
                null,
                "Text Notes",
                personOne);
        personList.add(personOne);
        personList.add(personTwo);
        personOne.addNotes(notesOne);
    }

    @AfterEach
    public void tearDown() {
        personOne = personTwo = null;
        personList = null;
    }

    @Test
    public void savePerson() throws PersonAlreadyExistException, PersonNotFoundException {
        personService.savePerson(personMapper.personToDto(personOne));
        verify(personRepository, times(1)).save(any());
    }

    @Test
    public void emailExist() {
        when(personRepository.findByEmail(any())).thenReturn(personOne);
        boolean response = personService.emailExist(personOne.getEmail());
        verify(personRepository, times(1)).findByEmail(any());
        assertTrue(response);
    }

    @Test
    public void usernameExist() {
        when(personRepository.findByUsername(any())).thenReturn(personOne);
        boolean response = personService.usernameExist(personOne.getUsername());
        verify(personRepository, times(1)).findByUsername(any());
        assertTrue(response);
    }

    @Test
    public void deletePerson() throws PersonNotFoundException {
        when(personRepository.findByUsername(any())).thenReturn(personOne);
        personService.deletePerson(personOne.getUsername());
        personList.remove(personOne);
        verify(personRepository, times(1)).delete(any());
        assertEquals(1, personList.size());
    }

    @Test
    public void updatePerson() {
        when(personRepository.findByUsername(any())).thenReturn(personOne);
        personService.updatePerson(personOne.getUsername(), personMapper.personToDto(personTwo));
        verify(personRepository, times(1)).save(any());
        assertEquals(personOne.getUsername(), personTwo.getUsername());
        assertEquals(personOne.getPassword(), personTwo.getPassword());
        assertEquals(personOne.getEmail(), personTwo.getEmail());
        assertEquals(personOne.getAge(), personTwo.getAge());
    }

    @Test
    public void getAllPerson() {
        when(personRepository.findAll()).thenReturn(personList);
        personService.getAllPerson();
        List<Person> list = personList;
        assertEquals(list, personList);
        verify(personRepository, times(1)).findAll();
    }

    @Test
    public void saveNotes() {
        when(personRepository.findByUsername(any())).thenReturn(personTwo);
        when(notesMapperMock.dtoToNotes(any())).thenReturn(notesOne);
        personService.saveNotes(any(), notesMapper.notesToDto(notesOne));
        verify(personRepository, times(1)).save(any());
        verify(personRepository, times(1)).flush();
    }

    @Test
    public void updateNotes() {
        when(personRepository.findByUsername(any())).thenReturn(personTwo);
        when(notesMapperMock.dtoToNotes(any())).thenReturn(notesOne);
        personService.updateNotes(any(), notesMapper.notesToDto(notesOne));
        verify(personRepository, times(1)).save(any());
        verify(personRepository, times(1)).flush();

    }

    @Test
    public void deleteNotes() {
        when(personRepository.findByUsername(any())).thenReturn(personTwo);
        when(notesRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(notesOne));
        personService.deleteNotes(any(), notesOne.getId().toString());
        assertEquals(0, personTwo.getNotesList().size());
        verify(personRepository, times(1)).flush();
    }

    @Test
    public void getAllNotes() {
        when(personRepository.findByUsername(any())).thenReturn(personOne);
        List<NotesDto> notesDtoList = notesMapper.allNotesToDto(personOne.getNotesList());
        when(notesMapperMock.allNotesToDto(any())).thenReturn(notesDtoList);
        assertEquals(1, personService.getAllNotes(any()).size());
    }

}
