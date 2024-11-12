package com.jewel.ergon.jobs.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Demand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_seeker_id", nullable = false)
    private JobSeeker jobseeker;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private LocalDate dateApplied;


    @Column(nullable = false)
    private LocalDate dateEnded;


    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, unique = true)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "demand_type", nullable = false)
    private DemandType demandType;

}
