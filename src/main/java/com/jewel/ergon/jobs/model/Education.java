package com.jewel.ergon.jobs.model;


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
public class Education extends AbstractAuditableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "institution", nullable = false)
    private String institution;

    @Column(name = "degree", nullable = false)
    private String degree;

    @Column(name = "field_of_study", nullable = false)
    private String fieldOfStudy;

    @Column(name = "graduation_date", nullable = false)
    private LocalDate graduationDate;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "is_in_progress", nullable = false)
    private Boolean isInProgress = false;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(nullable = false)
    private JobSeeker jobseeker;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "cv_id", nullable = false)
    private Cv cv;

}
