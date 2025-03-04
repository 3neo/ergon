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
public class AppConfig extends AbstractAuditableEntity{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "key", nullable = false, unique = true)
    private String key;

    @Column(name = "value", nullable = false)
    private String value;
}
