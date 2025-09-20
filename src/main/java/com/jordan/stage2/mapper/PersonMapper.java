package com.jordan.stage2.mapper;


import com.jordan.stage2.dto.PersonDto;
import com.jordan.stage2.entity.Person;
import org.springframework.stereotype.Component;

/**
 * This class is responsible for mapping between DTO objects and entities
 */
@Component
public class PersonMapper {

    /**
     * map PersonDto to Person
     *
     * @param dto for conversion
     * @return new Person entity
     */
    public Person toEntity(PersonDto dto) {
        if (dto == null) {
            return null;
        }

        Person Person = new Person();
        Person.setId(dto.getId());
        Person.setFirstName(dto.getFirstName());
        Person.setLastName(dto.getLastName());
        Person.setAge(dto.getAge());
        Person.setEmail(dto.getEmail());

        return Person;
    }

    /**
     * map Person to PersonDto
     * 
     * @param entity entity for conversion
     * @return new PersonDto
     */
    public PersonDto toDto(Person entity) {
        if (entity == null) {
            return null;
        }

        PersonDto dto = new PersonDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setAge(entity.getAge());
        dto.setEmail(entity.getEmail());

        return dto;
    }

    /**
     * update the existing Person entity with the data from the DTO
     *
     * @param entity the entity to update
     * @param dto the DTO with the new data, if null, no update will be performed
     */
    public void updateEntityFromDto(Person entity, PersonDto dto) {
        if (entity == null || dto == null) {
            return;
        }

        // update basic fields
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setAge(dto.getAge());
        entity.setEmail(dto.getEmail());
    }
}