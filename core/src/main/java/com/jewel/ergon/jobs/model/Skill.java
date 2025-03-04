package com.jewel.ergon.jobs.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Skill  extends AbstractAuditableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "skill_level", nullable = false)
    private SkillLevel skillLevel;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(nullable = false)
    private JobSeeker jobseeker;





    @ManyToOne(cascade = {CascadeType.ALL})
    private Demand demand;

    @Column(name = "is_acquired", nullable = false)
    private Boolean isAcquired = false;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "cv_id", nullable = false)
    private Cv cv;

}
