package com.jewel.ergon.jobs.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Cv {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2000)
    private String resumeText;


    @Column(name = "is_canadian_format", nullable = false)
    private Boolean isCanadianFormat;

    @Enumerated(EnumType.STRING)
    @Column(name = "language", nullable = false)
    private Language language;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @Lob
    @Column(name = "file", nullable = false)
    private Byte[] file;


    @ManyToOne
    @JoinColumn(name = "Job_seeker_id", nullable = false)
    private JobSeeker jobseeker;

    @Column(name = "profile", nullable = false)
    private String profile;


    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "Cv_experiences",
            joinColumns = @JoinColumn(name = "cv_id"),
            inverseJoinColumns = @JoinColumn(name = "experiences_id"))
    private Set<Experience> experiences = new LinkedHashSet<>();

    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(joinColumns = @JoinColumn(name = "cv_id"))
    private Set<Skill> skills = new LinkedHashSet<>();

    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "Cv_educations",
            joinColumns = @JoinColumn(name = "cv_id"),
            inverseJoinColumns = @JoinColumn(name = "educations_id"))
    private Set<Education> educations = new LinkedHashSet<>();


}
