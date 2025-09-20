package com.jordan.stage2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Role")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Role name is required")
    @Size(min = 1, max = 50, message = "Role name must be between 1 and 50 characters")
    @Column(name = "role_name", nullable = false, length = 50)
    private String roleName;

    @Size(max = 200, message = "Description must be up to 200 characters")
    @Column(name = "description", length = 200)
    private String description;
}
