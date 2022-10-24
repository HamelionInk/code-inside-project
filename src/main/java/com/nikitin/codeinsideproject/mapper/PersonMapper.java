package com.nikitin.codeinsideproject.mapper;

import com.nikitin.codeinsideproject.dto.PersonDto;
import com.nikitin.codeinsideproject.entity.Person;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonDto personToDto(Person person);
    Person dtoToPerson(PersonDto personDto);
    List<PersonDto> allPersonToDto(List<Person> person);
    List<Person> allDtoToPerson(List<PersonDto> personDto);
}
