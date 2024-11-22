package com.jewel.ergon.jobs.model;

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
public class JobSeeker extends AbstractAuditableEntity{

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

    @ToString.Exclude
    @OneToMany(mappedBy = "jobseeker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Demand> demands;

    @OneToMany(mappedBy = "jobseeker", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Cv> cvs;

    @OneToMany(mappedBy = "jobseeker", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Experience> experiences;

    @OneToMany(mappedBy = "jobseeker", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Skill> skills;

    @OneToMany(mappedBy = "jobseeker", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Education> educations = new ArrayList<>();

    @Column(name = "phone")
    private String phone;

    @Lob
    @Column(name = "image")
    private Byte[] image;

    @Column(name = "address", nullable = false)
    private String address;

}
