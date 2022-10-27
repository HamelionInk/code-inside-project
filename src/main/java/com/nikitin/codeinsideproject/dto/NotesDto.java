package com.nikitin.codeinsideproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nikitin.codeinsideproject.entity.Person;

import java.time.LocalDate;
import java.util.UUID;

public class NotesDto {

    private UUID id;
    private String header;
    @JsonIgnore
    private static int version;
    private LocalDate dataCreate;
    private LocalDate dataUpdate;
    private String textNotes;
    @JsonIgnore
    private Person person;

    public NotesDto() {
    }

    public NotesDto(UUID id, String header, int version, LocalDate dataCreate, LocalDate dataUpdate, String textNotes, Person person) {
        this.id = id;
        this.header = header;
        NotesDto.version = version;
        this.dataCreate = dataCreate;
        this.dataUpdate = dataUpdate;
        this.textNotes = textNotes;
        this.person = person;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public LocalDate getDataCreate() {
        return dataCreate;
    }

    public void setDataCreate(LocalDate dataCreate) {
        this.dataCreate = dataCreate;
    }

    public LocalDate getDataUpdate() {
        return dataUpdate;
    }

    public void setDataUpdate(LocalDate dataUpdate) {
        this.dataUpdate = dataUpdate;
    }

    public String getTextNotes() {
        return textNotes;
    }

    public void setTextNotes(String textNotes) {
        this.textNotes = textNotes;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        NotesDto.version = version;
    }
}
