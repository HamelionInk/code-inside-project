package com.nikitin.codeinsideproject.controller;

import com.nikitin.codeinsideproject.entity.Notes;
import com.nikitin.codeinsideproject.entity.Person;
import com.nikitin.codeinsideproject.service.PersonService;
import com.nikitin.codeinsideproject.util.RoleEnum;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

    @Mock
    private PersonService personService;
    private Person person;

    @InjectMocks
    private PersonController personController;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        List<Notes> notesList = new ArrayList<>();
        person = new Person(UUID.randomUUID(),
                "TestUsername",
                "TestPassword",
                "TestEmail@gmail.com",
                20,
                RoleEnum.USER,
                notesList);
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
    }

    @AfterEach
    public void tearDown() {
        person = null;
    }

    @Test
    public void PostMappingSave() throws Exception {
        //when(personService.savePerson(any())).thenReturn()
        mockMvc.perform(
                post("/person")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        verify(personService, times(1)).savePerson(any());
    }

}
