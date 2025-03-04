package com.jewel.ergon.jobs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jewel.ergon.jobs.model.demandStatus.DemandStatus;
import com.jewel.ergon.jobs.utilis.IsoTimestampToLocalDateDeserializer;
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
   //fix  time zone-related issues , cause front send date in ISO 8601 format
    @JsonDeserialize(using = IsoTimestampToLocalDateDeserializer.class)
    @Column(name = "date_applied")
    private LocalDate dateApplied;

    @JsonDeserialize(using = IsoTimestampToLocalDateDeserializer.class)
    @Column(name = "date_ended", nullable = false)
    private LocalDate dateEnded;


    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;


    @Transient
    private DemandStatus demandStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "demand_type", nullable = false)
    private DemandType demandType;

    @JsonDeserialize(using = IsoTimestampToLocalDateDeserializer.class)
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
