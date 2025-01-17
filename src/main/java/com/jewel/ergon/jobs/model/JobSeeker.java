package com.jewel.ergon.jobs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "Job_seeker")
public class JobSeeker extends AbstractAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;


    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = 500)
    private String bio; // New field for a summary or personal description

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "jobseeker", cascade = CascadeType.ALL)
    private List<Demand> demands;

    @JsonIgnore
    @OneToMany(mappedBy = "jobseeker", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Cv> cvs;

    @JsonIgnore
    @OneToMany(mappedBy = "jobseeker", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Experience> experiences;

    @JsonIgnore
    @OneToMany(mappedBy = "jobseeker", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Skill> skills;

    @JsonIgnore
    @OneToMany(mappedBy = "jobseeker", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Education> educations = new ArrayList<>();

    @Column(name = "phone")
    private String phone;

    @Lob
    @JsonIgnore // Prevent serialization/deserialization of the image field
    @Column(name = "image")
    private byte[] image;

    @Column(name = "address", nullable = false)
    private String address;

}
