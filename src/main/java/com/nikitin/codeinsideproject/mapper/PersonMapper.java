package com.nikitin.codeinsideproject.mapper;

import com.nikitin.codeinsideproject.dto.PersonDto;
import com.nikitin.codeinsideproject.entity.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonDto personToDto(Person person);
    Person dtoToPerson(PersonDto personDto);
}
