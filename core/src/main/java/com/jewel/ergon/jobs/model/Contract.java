package com.jewel.ergon.jobs.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jewel.ergon.jobs.utilis.IsoTimestampToLocalDateDeserializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Contract   extends AbstractAuditableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "contract_title", nullable = false)
    private String contractTitle;

    @Column(name = "contract_number", nullable = false, unique = true)
    private String contractNumber;

    @JsonDeserialize(using = IsoTimestampToLocalDateDeserializer.class)
    @PastOrPresent(message = "Date should be in past")
    @Column(name = "start_date", nullable = false, unique = true)
    private LocalDate startDate;

    @JsonDeserialize(using = IsoTimestampToLocalDateDeserializer.class)
    @PastOrPresent(message = "Date should be in past")
    @Column(name = "end_date", unique = true)
    private LocalDate endDate;

    @Column(name = "is_cdi", nullable = false)
    private Boolean isCdi = false;

    @Column(name = "salary", nullable = false, precision = 10, scale = 2)
    private BigDecimal salary;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "contract_type", nullable = false)
    private ContractType contractType;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(nullable = false)
    private Experience experience;



    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private Currency currency;

}
