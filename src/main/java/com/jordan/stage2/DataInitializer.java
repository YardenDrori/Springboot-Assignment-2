package com.jordan.stage2;


import com.jordan.stage2.entity.Person;
import com.jordan.stage2.repository.PersonRepository;
import com.jordan.stage2.entity.Role;
import com.jordan.stage2.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.HashSet;

@Component
public class DataInitializer implements CommandLineRunner {

    private final PersonRepository PersonRepository;
    private final RoleRepository RoleRepository;

    @Autowired
    public DataInitializer(PersonRepository PersonRepository, RoleRepository RoleRepository) {
        this.PersonRepository = PersonRepository;
        this.RoleRepository = RoleRepository;
    }

    

    @Override
    public void run(String... args) {

        if (RoleRepository.count() == 0) {
            Role adminRole = new Role(null, "ADMIN", "Administrator role with full permissions", new HashSet<>());
            Role userRole = new Role(null, "USER", "Standard user role with limited permissions", new HashSet<>());
            Role managerRole = new Role(null, "MANAGER", "Manager role with elevated permissions", new HashSet<>());

            RoleRepository.save(adminRole);
            RoleRepository.save(userRole);
            RoleRepository.save(managerRole);

            System.out.println("Data initialization completed. Created 3 Role records.");
        } else {
            System.out.println("Database already contains Role records. Skipping initialization.");
        }

        // Check if there are already records in the database
        if (PersonRepository.count() == 0) {
            // Create and save initial data
            PersonRepository.save(new Person(null, "John", "Doe", 21.5, "john.doe@example.com", new HashSet<>()));
            PersonRepository.save(new Person(null, "Jane", "Smith", 22.3, "jane.smith@example.com", new HashSet<>()));
            PersonRepository.save(new Person(null, "Alice", "Johnson", 20.7, "alice.johnson@example.com", new HashSet<>()));
            PersonRepository.save(new Person(null, "Bob", "Brown", 23.1, "bob.brown@example.com", new HashSet<>()));
            PersonRepository.save(new Person(null, "Charlie", "Davis", 22.8, "charlie.davis@example.com", new HashSet<>()));

            System.out.println("Data initialization completed. Created 5 Person records.");
        } else {
            System.out.println("Database already contains records. Skipping initialization.");
        }

    }
}