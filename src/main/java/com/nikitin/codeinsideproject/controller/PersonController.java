package com.nikitin.codeinsideproject.controller;

import com.nikitin.codeinsideproject.dto.PersonDto;
import com.nikitin.codeinsideproject.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        List<PersonDto> responseBody = personService.getAllPerson();
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody @Valid PersonDto personDto) {
        personService.savePerson(personDto);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PatchMapping(path = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@PathVariable("username") String username, @RequestBody @Valid PersonDto personDto) {
        personService.updatePerson(username, personDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable("username") String username) {
        personService.deletePerson(username);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
