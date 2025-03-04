package com.jewel.ergon.jobs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jewel.ergon.jobs.utilis.IsoTimestampToLocalDateDeserializer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "cv")
public class Cv  extends AbstractAuditableEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Lob
    @Column(name = "resume_text", nullable = false)
    private String resumeText;


    @Column(name = "is_canadian_format", nullable = false)
    private Boolean isCanadianFormat;

    @Enumerated(EnumType.STRING)
    @Column(name = "language", nullable = false)
    private Language language;

    @JsonDeserialize(using = IsoTimestampToLocalDateDeserializer.class)
    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @Lob
    @Column(name = "file")
    @JsonIgnore // Prevent serialization/deserialization of the image field
    private byte[] file;


    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private JobSeeker jobseeker;

    @Column(name = "profile", nullable = false)
    private String profile;



//    @JsonIgnore
//    @ToString.Exclude
//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
//    @JoinTable(name = "Cv_experiences",
//            joinColumns = @JoinColumn(name = "cv_id"),
//            inverseJoinColumns = @JoinColumn(name = "experiences_id"))
//    private Set<Experience> experiences = new LinkedHashSet<>();
//
//    @JsonIgnore
//    @ToString.Exclude
//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
//    @JoinTable(joinColumns = @JoinColumn(name = "cv_id"))
//    private Set<Skill> skills = new LinkedHashSet<>();
//
//
//    @JsonIgnore
//    @ToString.Exclude
//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
//    @JoinTable(name = "Cv_educations",
//            joinColumns = @JoinColumn(name = "cv_id"),
//            inverseJoinColumns = @JoinColumn(name = "educations_id"))
//    private Set<Education> educations = new LinkedHashSet<>();

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "version_cv", nullable = false)
    private Integer versionCV;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "cv", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Experience> experiences = new ArrayList<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "cv", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Skill> skills = new ArrayList<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "cv", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Education> educations = new ArrayList<>();

}
