package com.jordan.stage2.service;

import com.jordan.stage2.dto.PersonDto;
import com.jordan.stage2.entity.Person;
import com.jordan.stage2.exception.AlreadyExists;
import com.jordan.stage2.exception.NotExists;
import com.jordan.stage2.exception.PersonIdAndIdMismatch;
import com.jordan.stage2.mapper.PersonMapper;
import com.jordan.stage2.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/*
 * @Transactional annotations are used in this service layer for several important reasons:
 *
 * 1. Data integrity - Ensures all database operations within a method either complete
 *    successfully or roll back entirely, maintaining data consistency
 *
 * 2. Performance optimization - Using readOnly=true for query methods helps Hibernate
 *    optimize performance by disabling dirty checking and potentially using read replicas
 *
 * 3. Declarative transaction management - Allows Spring to handle transaction boundaries
 *    automatically, reducing error-prone manual transaction handling
 */
@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository PersonRepository;
    private final PersonMapper PersonMapper;

    @Autowired
    public PersonServiceImpl(PersonRepository PersonRepository, PersonMapper PersonMapper) {
        this.PersonRepository = PersonRepository;
        this.PersonMapper = PersonMapper;
    }

    /**
     * Get all Persons from the system as DTOs
     * @return List of all Persons as DTOs
     */
    @Override
    @Transactional(readOnly = true)
    public List<PersonDto> getAllPersons() {
        return PersonRepository.findAll().stream()
                .map(PersonMapper::toDto)
                .collect(Collectors.toList());
    }


    /**
     * Get Person by ID as DTO
     * @param id The Person ID to retrieve
     * @return The found Person as DTO
     * @throws NotExists If a Person doesn't exist
     */
    @Override
    @Transactional(readOnly = true)
    public PersonDto getPersonById(Long id) {
        Person Person = PersonRepository.findById(id)
                .orElseThrow(() -> new NotExists("Person with id " + id + " does not exist"));
                
        return PersonMapper.toDto(Person);
    }


    /**
     * Add a new Person
     * @param PersonDto Person data to add (as DTO)
     * @return The added Person as DTO
     * @throws AlreadyExists If a Person with the same ID already exists
     */
    @Override
    @Transactional
    public PersonDto addPerson(PersonDto PersonDto) {
        // Check if a Person with the same email already exists
        if (PersonRepository.findByEmail(PersonDto.getEmail()).isPresent()) {
            throw new AlreadyExists("Person with email " + PersonDto.getEmail() + " already exists");
        }

        // Convert DTO to an entity using the mapper
        Person Person = PersonMapper.toEntity(PersonDto);
        
        // Save entity and convert back to DTO
        Person added = PersonRepository.save(Person);
        return PersonMapper.toDto(added);
    }


    /**
     * Update an existing Person
     * @param PersonDto Updated Person data (as DTO)
     * @param id The ID from the path parameter
     * @return The updated Person as DTO
     * @throws NotExists If a Person doesn't exist
     * @throws PersonIdAndIdMismatch If ID in a path doesn't match Person ID
     */
    @Override
    @Transactional
    public PersonDto updatePerson(PersonDto PersonDto, Long id) {
        // Check if the ID parameter matches the Person's ID (if DTO has ID)
        if (PersonDto.getId() != null && !PersonDto.getId().equals(id)) {
            throw new PersonIdAndIdMismatch("Path ID " + id + " does not match body ID " + PersonDto.getId());
        }

        // Check if a Person exists
        Person existingPerson = PersonRepository.findById(id)
                .orElseThrow(() -> new NotExists("Person with id " + id + " does not exist"));

        // Check if another Person already uses the updated email
        PersonRepository.findByEmail(PersonDto.getEmail())
                .ifPresent(Person -> {
                    if (!Person.getId().equals(id)) {
                        throw new AlreadyExists("Email " + PersonDto.getEmail() + " is already in use");
                    }
                });

        // Update the existing entity from the DTO
        PersonMapper.updateEntityFromDto(existingPerson, PersonDto);

        // Save the updated entity and convert back to DTO
        Person updated = PersonRepository.save(existingPerson);
        return PersonMapper.toDto(updated);
    }


    /**
     * Delete a Person by ID
     * @param id Person ID to delete
     * @throws NotExists If a Person doesn't exist
     */
    @Override
    @Transactional
    public void deletePerson(Long id) {
        // Check if a Person exists
        if (!PersonRepository.existsById(id)) {
            throw new NotExists("Person with id " + id + " does not exist");
        }

        PersonRepository.deleteById(id);
    }
}