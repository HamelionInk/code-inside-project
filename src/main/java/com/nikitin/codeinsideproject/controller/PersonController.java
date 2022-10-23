package com.nikitin.codeinsideproject.controller;

import com.nikitin.codeinsideproject.dto.PersonDto;
import com.nikitin.codeinsideproject.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody @Valid PersonDto personDto) {
        personService.savePerson(personDto);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    // Admin and User
    @PatchMapping(path = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@PathVariable("username") String username, @RequestBody @Valid PersonDto personDto) {
        personService.updatePerson(username, personDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    // Только админ
    @DeleteMapping(path = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable("username") String username) {
        personService.deletePerson(username);
        return ResponseEntity.ok(HttpStatus.OK);
    }


}
