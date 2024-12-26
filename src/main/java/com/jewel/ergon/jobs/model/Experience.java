package com.jewel.ergon.jobs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "start_date", nullable = false, unique = true)
    private LocalDate startDate;

    @PastOrPresent
    @Column(name = "end_date")
    private LocalDate endDate;


    // ALTER TABLE experience DROP COLUMN jobseeker_id;   to fix an insert problem
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "job_seeker_id",nullable = false)
    private JobSeeker jobseeker;

    @Column(name = "description", nullable = false)
    private String description;


    //TODO use set ?
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "experiences", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Cv> cvs ;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "experience", cascade = CascadeType.ALL)
    private List<Contract> contracts = new ArrayList<>();

}
