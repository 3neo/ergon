package com.jewel.ergon.jobs.model;

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
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "job_title", nullable = false)
    private String jobTitle;



    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @PastOrPresent
    @Column(name = "start_date", nullable = false, unique = true)
    private LocalDate startDate;

    @PastOrPresent
    @Column(name = "end_date")
    private LocalDate endDate;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL, optional = false , fetch = FetchType.LAZY)
    @JoinColumn(name = "job_seeker_id", nullable = false)
    private JobSeeker jobseeker;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToMany(mappedBy = "experiences", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @ToString.Exclude
    private List<Cv> cvs ;


    @OneToMany(mappedBy = "experience", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contract> contracts = new ArrayList<>();

}
