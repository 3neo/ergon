package com.jewel.ergon.jobs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.net.URL;
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
public class Demand  extends AbstractAuditableEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private JobSeeker jobseeker;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Company company;

    @Column(nullable = false)
    private String role;


    //it's not mandatory because it can be in OFFER status
    @Column()
    private LocalDate dateApplied;


    @Column(nullable = false)
    private LocalDate dateEnded;


    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "demand_type", nullable = false)
    private DemandType demandType;

    @Column(name = "date_start", nullable = false )
    private LocalDate dateStart;

    @NotNull(message = "jobLink should not be null")
    @Column(name = "job_link", nullable = false)
    private URL jobLink;

    @Lob
    @Column(name = "description", nullable = false )
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "demand", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Skill> demandedSkills = new ArrayList<>();

}
