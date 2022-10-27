package com.nikitin.codeinsideproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nikitin.codeinsideproject.dto.NotesDto;
import com.nikitin.codeinsideproject.entity.Person;
import com.nikitin.codeinsideproject.error.PersonNotFoundException;
import com.nikitin.codeinsideproject.error.ResponseExceptionHandler;
import com.nikitin.codeinsideproject.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
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
public class NotesControllerTest {

    @Mock
    private PersonService personService;

    @InjectMocks
    private NotesController notesController;

    private MockMvc mockMvc;
    private NotesDto notesDto;
    private NotesDto notesDto1;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(notesController).setControllerAdvice(new ResponseExceptionHandler()).build();
        Person person = new Person();
        notesDto = new NotesDto(UUID.randomUUID(),
                "header One",
                1,
                LocalDate.now(),
                null,
                "Text Notes",
                person);
        notesDto1 = new NotesDto(UUID.randomUUID(),
                "header One1",
                1,
                LocalDate.now(),
                null,
                "Text Notes1",
                person);
    }

    @Test
    public void getMappingGetAllForPersonStatusIsOk() throws Exception {
        List<NotesDto> notesDtoList = new ArrayList<>();
        notesDtoList.add(notesDto);
        notesDtoList.add(notesDto1);
        when(personService.getAllNotes(any())).thenReturn(notesDtoList);
        mockMvc.perform(
                get("/notes/TestUsername")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].header", is("header One")))
                .andExpect(jsonPath("$[1].header", is("header One1")));
        verify(personService, times(1)).getAllNotes(any());
    }

    @Test
    public void getMappingGetAllForPersonStatusNotFound() throws Exception {
        try {
            doThrow(PersonNotFoundException.class).when(personService).getAllNotes(any());
        } catch (Exception ex) {
            //Здесь должен быть какой то логер =Ъ
        }
        mockMvc.perform(
                        get("/notes/TestUsername")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(personService, times(1)).getAllNotes(any());
    }

    @Test
    public void postMappingSaveStatusIsOk() throws Exception{
        mockMvc.perform(
                        post("/notes/TestUsername")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(notesDto)))
                .andExpect(status().isOk());
        verify(personService, times(1)).saveNotes(any(), any());
    }

    @Test
    public void postMappingSaveStatusNotFound() throws Exception {
        try {
            doThrow(PersonNotFoundException.class).when(personService).saveNotes(any(), any());
        } catch (Exception ex) {
            //Здесь должен быть какой то логер =Ъ
        }
        mockMvc.perform(
                        post("/notes/TestUsername")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(notesDto)))
                .andExpect(status().isNotFound());
        verify(personService, times(1)).saveNotes(any(), any());
    }

    @Test
    public void patchMappingUpdateStatusIsOk() throws Exception {
        mockMvc.perform(
                        patch("/notes/TestUsername")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(notesDto)))
                .andExpect(status().isOk());
        verify(personService, times(1)).updateNotes(any(), any());
    }

    @Test
    public void patchMappingUpdateStatusNotFound() throws Exception{
        try {
            doThrow(PersonNotFoundException.class).when(personService).updateNotes(any(), any());
        } catch (Exception ex) {
            //Здесь должен быть какой то логер =Ъ
        }
        mockMvc.perform(
                        patch("/notes/TestUsername")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(notesDto)))
                .andExpect(status().isNotFound());
        verify(personService, times(1)).updateNotes(any(), any());
    }

    @Test
    public void deleteMappingDeleteStatusIsOk() throws Exception {
        mockMvc.perform(
                        delete("/notes/TestUsernameDto/5435341312543")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(personService, times(1)).deleteNotes(any(), any());
    }

    @Test
    public void deleteMappingDeleteStatusNotFound() throws Exception {
        try {
            doThrow(PersonNotFoundException.class).when(personService).deleteNotes(any(), any());
        } catch (Exception ex) {
            //Здесь должен быть какой то логер =Ъ
        }
        mockMvc.perform(
                        delete("/notes/TestUsernameDto/5435341312543")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(personService, times(1)).deleteNotes(any(), any());
    }

    public static String asJsonString(final Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
