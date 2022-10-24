package com.nikitin.codeinsideproject.mapper;

import com.nikitin.codeinsideproject.dto.NotesDto;
import com.nikitin.codeinsideproject.entity.Notes;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotesMapper {

    NotesDto NotesToDto(Notes notes);
    Notes dtoToNotes(NotesDto notesDto);
    List<NotesDto> allNotesToDto(List<Notes> notes);
    List<Notes> allDtoToNotes(List<NotesDto> notesDto);
}
