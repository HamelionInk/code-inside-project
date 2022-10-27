package com.nikitin.codeinsideproject.controller;

import com.nikitin.codeinsideproject.dto.PersonDto;
import com.nikitin.codeinsideproject.entity.Notes;
import com.nikitin.codeinsideproject.error.PersonAlreadyExistException;
import com.nikitin.codeinsideproject.error.PersonNotFoundException;
import com.nikitin.codeinsideproject.error.ResponseExceptionHandler;
import com.nikitin.codeinsideproject.service.PersonService;
import com.nikitin.codeinsideproject.util.RoleEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    private MockMvc mockMvc;
    private PersonDto personDto;
    private PersonDto personDto1;
    private PersonDto personDtoNotValidPassword;

    @BeforeEach
    public void setUp() {
        List<Notes> notesList = new ArrayList<>();
        personDto = new PersonDto(UUID.randomUUID(),
                "TestUsernameDto",
                "TestPasswordDto",
                "TestPasswordDto",
                "TestEmail@gmail.com",
                RoleEnum.USER,
                43,
                notesList);
        personDto1 = new PersonDto(UUID.randomUUID(),
                "TestUsernameDto1",
                "TestPasswordDto1",
                "TestPasswordDto1",
                "TestEmail1@gmail.com",
                RoleEnum.USER,
                43,
                notesList);
        personDtoNotValidPassword = new PersonDto(UUID.randomUUID(),
                "TestUsernameDtoNotValid",
                "TestPasswordDto",
                "TestPasswordDtoNotValid",
                "TestEmail@gmail.com",
                RoleEnum.USER,
                43,
                notesList);
        mockMvc = MockMvcBuilders.standaloneSetup(personController).setControllerAdvice(new ResponseExceptionHandler()).build();
    }

    @AfterEach
    public void tearDown() {
        personDto = personDto1 = personDtoNotValidPassword = null;
    }

    @Test
    public void postMappingSaveStatusIsOk() throws Exception {
        mockMvc.perform(
                        post("/person")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(personDto)))
                .andExpect(status().isOk());
        verify(personService, times(1)).savePerson(any());
    }

    @Test
    public void postMappingSaveStatusBadRequest() throws Exception {
        mockMvc.perform(
                        post("/person")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(personDtoNotValidPassword)))
                .andExpect(status().isBadRequest());
        verify(personService, times(0)).savePerson(any());
    }

    @Test
    public void postMappingSaveStatusConflict() throws Exception {
        try {
            doThrow(PersonAlreadyExistException.class).when(personService).savePerson(any());
        } catch (Exception ex) {
            //Здесь должен быть какой то логер =Ъ
        }
        mockMvc.perform(
                        post("/person")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(personDto)))
                .andExpect(status().isConflict());
        verify(personService, times(1)).savePerson(any());
    }

    @Test
    public void patchMappingUpdateStatusIsOk() throws Exception {
        mockMvc.perform(
                        patch("/person/TestUsernameDto")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(personDto)))
                .andExpect(status().isOk());
        verify(personService, times(1)).updatePerson(any(), any());
    }

    @Test
    public void patchMappingUpdateStatusBadRequest() throws Exception {
        mockMvc.perform(
                        patch("/person/TestUsernameDto")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(personDtoNotValidPassword)))
                .andExpect(status().isBadRequest());
        verify(personService, times(0)).updatePerson(any(), any());
    }

    @Test
    public void patchMappingUpdateStatusConflict() throws Exception {
        try {
            doThrow(PersonAlreadyExistException.class).when(personService).updatePerson(any(), any());
        } catch (Exception ex) {
            //Здесь должен быть какой то логер =Ъ
        }
        mockMvc.perform(
                        patch("/person/TestUsernameDto")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(personDto)))
                .andExpect(status().isConflict());
        verify(personService, times(1)).updatePerson(any(), any());
    }

    @Test
    public void patchMappingUpdateStatusNotFound() throws Exception {
        try {
            doThrow(PersonNotFoundException.class).when(personService).updatePerson(any(), any());
        } catch (Exception ex) {
            //Здесь должен быть какой то логер =Ъ
        }
        mockMvc.perform(
                        patch("/person/TestUsernameDto")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(personDto)))
                .andExpect(status().isNotFound());
        verify(personService, times(1)).updatePerson(any(), any());
    }

    @Test
    public void deleteMappingDeleteStatusIsOk() throws Exception {
        mockMvc.perform(
                        delete("/person/TestUsernameDto")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(personService, times(1)).deletePerson(any());
    }

    @Test
    public void deleteMappingDeleteStatusNotFound() throws Exception {
        try {
            doThrow(PersonNotFoundException.class).when(personService).deletePerson(any());
        } catch (Exception ex) {
            //Здесь должен быть какой то логер =Ъ
        }
        mockMvc.perform(
                        delete("/person/TestUsernameDto")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(personService, times(1)).deletePerson(any());
    }

    @Test
    public void getMappingGetAllStatusIsOk() throws Exception {
        List<PersonDto> personDtoList = new ArrayList<>();
        personDtoList.add(personDto);
        personDtoList.add(personDto1);
        when(personService.getAllPerson()).thenReturn(personDtoList);
        mockMvc.perform(
                        get("/person")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username", is("TestUsernameDto")))
                .andExpect(jsonPath("$[1].username", is("TestUsernameDto1")));
        verify(personService, times(1)).getAllPerson();
    }

    @Test
    public void getMappingGetAllStatusNotFound() throws Exception {
        try {
            doThrow(PersonNotFoundException.class).when(personService).getAllPerson();
        } catch (Exception ex) {
            //Здесь должен быть какой то логер =Ъ
        }
        mockMvc.perform(
                        get("/person")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(personService, times(1)).getAllPerson();
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
