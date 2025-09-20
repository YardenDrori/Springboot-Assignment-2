package com.jordan.stage2.service;


import com.jordan.stage2.dto.PersonDto;
import java.util.List;

/**
 * Service interface for Person operations
 * Works directly with DTOs to handle both data conversion and business logic
 */
public interface PersonService {
    /**
     * Get all Persons from the system as DTOs
     * @return List of all Persons as DTOs
     */
    List<PersonDto> getAllPersons();
    
    /**
     * Get Person by ID as DTO
     * @param id The Person ID to retrieve
     * @return The found Person as DTO
     * @throws org.example.stage2.exception.NotExists If a Person doesn't exist
     */
    PersonDto getPersonById(Long id);
    
    /**
     * Add a new Person
     * @param PersonDto Person data to add (as DTO)
     * @return The added Person as DTO with generated ID
     * @throws org.example.stage2.exception.AlreadyExists If Person with the same email already exists
     */
    PersonDto addPerson(PersonDto PersonDto);
    
    /**
     * Update an existing Person
     * @param PersonDto Updated Person data (as DTO)
     * @param id The ID from the path parameter
     * @return The updated Person as DTO
     * @throws org.example.stage2.exception.NotExists If a Person doesn't exist
     * @throws org.example.stage2.exception.PersonIdAndIdMismatch If ID in a path doesn't match Person ID
     * @throws org.example.stage2.exception.AlreadyExists If email is already in use by another Person
     */
    PersonDto updatePerson(PersonDto PersonDto, Long id);
    
    /**
     * Delete a Person by ID
     * @param id Person ID to delete
     * @throws org.example.stage2.exception.NotExists If Person doesn't exist
     */
    void deletePerson(Long id);
}