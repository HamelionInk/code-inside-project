package com.nikitin.codeinsideproject.controller;

import com.nikitin.codeinsideproject.dto.NotesDto;
import com.nikitin.codeinsideproject.entity.Notes;
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

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NotesController {

    private final PersonService personService;

    @Autowired
    public NotesController(PersonService personService) {
        this.personService = personService;
    }

    @Operation(summary = "Получает все заметки пользователя по username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the person",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Notes.class)) }),
            @ApiResponse(responseCode = "404", description = "person not found",
                    content = @Content) })
    @GetMapping(path = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllForPerson(@PathVariable("username") String username) {
        List<NotesDto> responseBody = personService.getAllNotes(username);
        return ResponseEntity.ok(responseBody);
    }

    @Operation(summary = "Сохраняет заметку для определенного пользователя по username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created notes for person",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Notes.class)) }),
            @ApiResponse(responseCode = "404", description = "person not found",
                    content = @Content) })
    @PostMapping(path = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@PathVariable("username") String username, @RequestBody NotesDto notesDto) {
        personService.saveNotes(username, notesDto);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @Operation(summary = "Обновляет выбранную заметку для определенного пользователя, в RequestBody отправляются данные с выбранной заметки и изменениями и обновление происходит по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notes for person updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Notes.class)) }),
            @ApiResponse(responseCode = "404", description = "person not found",
                    content = @Content) })
    @PatchMapping(path = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@PathVariable("username") String username, @RequestBody NotesDto notesDto) {
        personService.updateNotes(username, notesDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Удаляет заметку для определенного пользователя по его username и id заметки")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "notes for person deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Notes.class)) }),
            @ApiResponse(responseCode = "404", description = "person not found or notes not found",
                    content = @Content) })
    @DeleteMapping(path = "/{username}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable("id") String id,
                                    @PathVariable("username") String username) {
        personService.deleteNotes(username, id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
