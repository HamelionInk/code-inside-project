package com.nikitin.codeinsideproject.controller;

import com.nikitin.codeinsideproject.dto.PersonDto;
import com.nikitin.codeinsideproject.entity.Person;
import com.nikitin.codeinsideproject.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Получает всех пользователей и их заметки")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the persons",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Person.class)) }),
            @ApiResponse(responseCode = "404", description = "persons not found",
                    content = @Content) })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        List<PersonDto> responseBody = personService.getAllPerson();
        return ResponseEntity.ok(responseBody);
    }

    @Operation(summary = "Сохраняет или регистрирует пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created person",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Person.class)) }),
            @ApiResponse(responseCode = "409", description = "Person already exist with email or username",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Not Valid data")})
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody @Valid PersonDto personDto) {
        personService.savePerson(personDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Обновляет информацию о пользователе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated person",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Person.class)) }),
            @ApiResponse(responseCode = "409", description = "Person already exist with email or username",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Person not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Not Valid data")})
    @PatchMapping(path = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@PathVariable("username") String username, @RequestBody @Valid PersonDto personDto) {
        personService.updatePerson(username, personDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Удаляет пользователя по его username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted person",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Person.class)) }),
            @ApiResponse(responseCode = "404", description = "Person not found",
                    content = @Content)})
    @DeleteMapping(path = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable("username") String username) {
        personService.deletePerson(username);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
