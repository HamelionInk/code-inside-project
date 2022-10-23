package com.nikitin.codeinsideproject.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notes")
public class NotesController {

    // Сохраняем заметку
    @PostMapping(path = "/")
    public ResponseEntity<?> save() {
        return null;
    }

    // Обновляем заметку по id
    @PatchMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@PathVariable("id") String id) {
        return null;
    }

    // Удаляем заметку по id
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        return null;
    }

}
