package com.jordan.stage2;

import com.jordan.stage2.entity.Person;
import com.jordan.stage2.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final PersonRepository PersonRepository;

    @Autowired
    public DataInitializer(PersonRepository PersonRepository) {
        this.PersonRepository = PersonRepository;
    }

    @Override
    public void run(String... args) {
        // Check if there are already records in the database
        if (PersonRepository.count() == 0) {
            // Create and save initial data
            PersonRepository.save(new Person(null, "John", "Doe", 21.5, "john.doe@example.com"));
            PersonRepository.save(new Person(null, "Jane", "Smith", 22.3, "jane.smith@example.com"));
            PersonRepository.save(new Person(null, "Alice", "Johnson", 20.7, "alice.johnson@example.com"));
            PersonRepository.save(new Person(null, "Bob", "Brown", 23.1, "bob.brown@example.com"));
            PersonRepository.save(new Person(null, "Charlie", "Davis", 22.8, "charlie.davis@example.com"));

            System.out.println("Data initialization completed. Created 5 Person records.");
        } else {
            System.out.println("Database already contains records. Skipping initialization.");
        }
    }
}