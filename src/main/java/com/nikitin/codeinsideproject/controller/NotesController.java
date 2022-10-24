package com.nikitin.codeinsideproject.controller;

import com.nikitin.codeinsideproject.dto.NotesDto;
import com.nikitin.codeinsideproject.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NotesController {

    private final PersonService personService;

    @Autowired
    public NotesController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(path = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllForPerson(@PathVariable("username") String username) {
        List<NotesDto> responseBody = personService.getAllNotes(username);
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping(path = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@PathVariable("username") String username, @RequestBody NotesDto notesDto) {
        personService.saveNotes(username, notesDto);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PatchMapping(path = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@PathVariable("username") String username, @RequestBody NotesDto notesDto) {
        personService.updateNotes(username, notesDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{username}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable("id") String id,
                                    @PathVariable("username") String username) {
        personService.deleteNotes(username, id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
