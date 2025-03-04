package com.jewel.ergon.jobs.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;



@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "currencies", indexes = {
        @Index(name = "idx_currency_name", columnList = "currency_name")
}, uniqueConstraints = {
        @UniqueConstraint(columnNames = {"currency_code"})
})
public class Currency implements Serializable {

    // Primary key with proper generation strategy
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    // Unique currency code (e.g., USD, EUR)   , unique = true may be redundant here
    @Column(name = "currency_code", unique = true, nullable = false, length = 3)
    private String currencyCode;

    // Human-readable currency name (e.g., US Dollar, Euro)
    @Column(name = "currency_name", nullable = false, length = 50)
    private String currencyName;

    // Optional field for symbol (e.g., $, €)
    @Column(name = "currency_symbol", length = 5)
    private String currencySymbol;

    // Status field for enabling or disabling a currency
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;


    // Equality and hash code based on business keys
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return Objects.equals(currencyCode, currency.currencyCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currencyCode);
    }

    // ToString excluding sensitive or redundant fields
    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", currencyCode='" + currencyCode + '\'' +
                ", currencyName='" + currencyName + '\'' +
                '}';
    }
}

