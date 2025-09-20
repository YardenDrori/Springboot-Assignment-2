package com.jordan.stage2.controller;

import jakarta.validation.Valid;
import com.jordan.stage2.dto.PersonDto;
import com.jordan.stage2.response.StandardResponse;
import com.jordan.stage2.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * REST controller for Person operations
 * The controller works directly with DTOs and delegates to the service layer
 * for business logic and data conversion
 */
@RestController
@RequestMapping("/Persons")
public class PersonController {

    private final PersonService PersonService;

    public PersonController(PersonService PersonService) {
        this.PersonService = PersonService;
    }

    /**
     * Get all Persons
     * Returns ResponseEntity with StandardResponse and 200 OK status
     */
    @GetMapping()
    public ResponseEntity<StandardResponse> getAllPersons() {
        List<PersonDto> Persons = PersonService.getAllPersons();
        StandardResponse response = new StandardResponse("success", Persons, null);
        return ResponseEntity.ok(response);
    }

    /**
     * Get a Person by ID
     * Returns ResponseEntity with StandardResponse and 200 OK status
     */
    @GetMapping("/{id}")
    public ResponseEntity<StandardResponse> getPerson(@PathVariable Long id) {
        PersonDto Person = PersonService.getPersonById(id);
        StandardResponse response = new StandardResponse("success", Person, null);
        return ResponseEntity.ok(response);
    }

    /**
     * Add a new Person
     * Uses @Valid to validate a Person according to Jakarta Validation constraints
     * Returns ResponseEntity with StandardResponse and 201 Created status with location header
     */
    @PostMapping()
    public ResponseEntity<StandardResponse> addPerson(@Valid @RequestBody PersonDto PersonDto) {
        PersonDto added = PersonService.addPerson(PersonDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(added.getId())
                .toUri();

        StandardResponse response = new StandardResponse("success", added, null);
        return ResponseEntity.created(location).body(response);
    }

    /**
     * Update a Person
     * Uses @Valid to validate a Person according to Jakarta Validation constraints
     * Returns ResponseEntity with StandardResponse and 200 OK status
     * 
     * Note: The path variable ID identifies the resource to update, even though
     * the ID may also be present in the request body
     */
    @PutMapping("/{id}")
    public ResponseEntity<StandardResponse> updatePerson(@Valid @RequestBody PersonDto PersonDto, @PathVariable Long id) {
        PersonDto updated = PersonService.updatePerson(PersonDto, id);
        StandardResponse response = new StandardResponse("success", updated, null);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete a Person
     * Returns 204 No Content status without a response body
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@PathVariable Long id) {
        PersonService.deletePerson(id);
    }
}