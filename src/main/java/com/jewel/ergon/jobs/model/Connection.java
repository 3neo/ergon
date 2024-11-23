package com.jewel.ergon.jobs.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.validator.constraints.URL;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "connection")
public class Connection  extends AbstractAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    private Company company;

    @Email
    @Column(name = "mail")
    private String mail;

    @Column(name = "phone")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "connection_role", nullable = false)
    private ConnectionRole connectionRole;

    @Column(name = "is_known", nullable = false)
    private Boolean isKnown;

    @URL
    @Column(name = "linked_in_url")
    private String linkedInUrl;

    @Column(name = "is_connected_on_linked_in", nullable = false)
    private Boolean isConnectedOnLinkedIn;

}