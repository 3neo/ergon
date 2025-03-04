package com.jewel.ergon.jobs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jewel.ergon.jobs.utilis.IsoTimestampToLocalDateDeserializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
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
public class Experience  extends AbstractAuditableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "job_title", nullable = false)
    private String jobTitle;



    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(nullable = false)
    private Company company;

    @PastOrPresent
    @JsonDeserialize(using = IsoTimestampToLocalDateDeserializer.class)
    @Column(name = "start_date", nullable = false, unique = true)
    private LocalDate startDate;

    @PastOrPresent
    @JsonDeserialize(using = IsoTimestampToLocalDateDeserializer.class)
    @Column(name = "end_date")
    private LocalDate endDate;


    // ALTER TABLE experience DROP COLUMN jobseeker_id;   to fix an insert problem
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "job_seeker_id",nullable = false)
    private JobSeeker jobseeker;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;



    //TODO use set ?


    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "experience", cascade = CascadeType.ALL)
    private List<Contract> contracts = new ArrayList<>();

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cv_id")
    private Cv cv;

}
