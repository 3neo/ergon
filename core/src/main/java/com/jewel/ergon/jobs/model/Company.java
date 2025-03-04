package com.jewel.ergon.jobs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.net.URL;
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



    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL )             //, orphanRemoval = true)
    private List<Demand> demands;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Connection> connections;

    @Enumerated(EnumType.STRING)
    @Column(name = "sector", nullable = false)
    private Sector sector;

    @Column(name = "location", nullable = false)
    private String location;

    @Lob
    @Column(name = "presentation")
    private String presentation;

    @Enumerated(EnumType.STRING)
    @Column(name = "company_type", nullable = false)
    private CompanyType companyType;

    @Column(name = "is_start_up", nullable = false)
    private Boolean isStartUp ;

    @Column(name = "is_important", nullable = false)
    private Boolean isImportant;

    @Column(name = "is_in_my_cv", nullable = false)
    private Boolean isInCV ;

    @JsonIgnore
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Experience> experiences ;

    @Email(message = "should use a valid email format")
    @Column(name = "email", unique = true)
    private String email;

    @NotNull(message = "link should not be null")
    @Column(name = "link", nullable = false, unique = true)
    private URL link;

    @Column(name = "phone", unique = true)
    private String phone;

    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_2_id")
    private Address address2;

}

