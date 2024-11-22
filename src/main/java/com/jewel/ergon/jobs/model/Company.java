package com.jewel.ergon.jobs.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Company extends AbstractAuditableEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @ToString.Exclude
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Demand> demands;

    @ToString.Exclude
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Connection> connections;


    @Enumerated(EnumType.STRING)
    @Column(name = "sector", nullable = false)
    private Sector sector;

    @Column(name = "location", nullable = false)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name = "company_type", nullable = false)
    private CompanyType companyType;

    @Column(name = "is_start_up", nullable = false)
    private Boolean isStartUp ;

    @Column(name = "is_important", nullable = false)
    private Boolean isImportant;

    @Column(name = "is_in_my_cv", nullable = false)
    private Boolean isInCV ;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Experience> experiences ;

}

