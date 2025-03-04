package com.jewel.ergon.jobs.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "addresses")
public class Address {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "Street is required")
    @Size(max = 100, message = "Street name cannot exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String street;

    @NotBlank(message = "City is required")
    @Size(max = 50, message = "City name cannot exceed 50 characters")
    @Column(nullable = false, length = 50)
    private String city;

    @NotBlank(message = "State is required")
    @Size(max = 50, message = "State name cannot exceed 50 characters")
    @Column(nullable = false, length = 50)
    private String state;

    @NotBlank(message = "Country is required")
    @Size(max = 50, message = "Country name cannot exceed 50 characters")
    @Column(nullable = false, length = 50)
    private String country;

    @NotBlank(message = "Postal code is required")
    @Size(max = 20, message = "Postal code cannot exceed 20 characters")
    @Column(nullable = false, length = 20)
    private String postalCode;

    @Size(max = 20, message = "Optional field for a unit or apartment number")
    @Column(length = 20)
    private String unit;

    @Size(max = 100, message = "Additional instructions cannot exceed 100 characters")
    private String additionalInstructions;
}
