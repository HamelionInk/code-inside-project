package com.nikitin.codeinsideproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "notes")
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    @Column(name = "id")
    private UUID id;

    @Column(name = "header")
    private String header;

    @Column(name = "version")
    private int version;

    @Column(name = "data_create")
    @CreationTimestamp
    private LocalDate dataCreate;

    @Column(name = "data_update")
    private LocalDate dataUpdate;

    @Column(name = "text_notes")
    private String textNotes;

    @ManyToOne
    @NotNull
    @JsonIgnore
    private Person person;

    public Notes() {
    }

    public Notes(UUID id, String header, int version, LocalDate dataCreate, LocalDate dataUpdate, String textNotes, Person person) {
        this.id = id;
        this.header = header;
        this.version = version;
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
        this.version = version;
    }
}
